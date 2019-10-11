package com.crazy.portal.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Data
@Component
public class FTPClientUtil {

   @Value("${ftp.host}")
   private String host;
   @Value("${ftp.port}")
   private Integer port;
   @Value("${ftp.username}")
   private String username;
   @Value("${ftp.password}")
   private String password;
   
   private Boolean        binaryTransfer = true;
   private Boolean        passiveMode    = true;
   private String         encoding       = "UTF-8";
   private Integer        clientTimeout  = 3000;
   private Boolean        flag           = true;
   
   public FTPClientUtil(){
	   
   }
   
   public FTPClientUtil(String host, int port, String username, String password){
	   this.host = host;
	   this.port = port;
	   this.username = username;
	   this.password = password;
   }

    /**   
     * 返回一个FTPClient实例   
     *
     */
    private FTPClient getFTPClient()  throws IOException {
        //构造一个FtpClient实例
        FTPClient ftpClient = new FTPClient();
        //设置字符集
        ftpClient.setControlEncoding(encoding);
        try {
            //连接到ftp服务器
            connect(ftpClient);
            //设置为passive模式
            if (passiveMode) {
                ftpClient.enterLocalPassiveMode();
            }
            //设置文件传输类型
            setFileType(ftpClient);
            ftpClient.setSoTimeout(clientTimeout);
		} catch (IOException e) {
			 throw e;
		}
        return ftpClient;     
    }     
    
    /**   
     * 设置文件传输类型   
     *    
     * @throws IOException
     */    
    private void setFileType(FTPClient ftpClient) throws IOException {     
        try {     
            if (binaryTransfer) {     
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);     
            } else {     
                ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);     
            }
        } catch (IOException e) {     
            throw new IOException("setFileType error", e);     
        }
    }
    
    /**   
     * 连接到ftp服务器   
     *    
     * @param ftpClient   
     * @return 连接成功返回true，否则返回false   
     */
    public void connect(FTPClient ftpClient) throws IOException {
        try {
            ftpClient.connect(host, port);

            // 连接后检测返回码来校验连接是否成功
            int reply = ftpClient.getReplyCode();
            log.info(">>>Ftp reply code : {}", reply);
            if (FTPReply.isPositiveCompletion(reply)) {
                //登陆到ftp服务器
                log.info("==============登陆："+username+password);
                if (ftpClient.login(username, password)) {
                    log.info(">>>Ftp reply str : {}", ftpClient.getReplyString());
                    setFileType(ftpClient);
                }else{
                    throw new IOException("FTP username or password is wrong.");
                }
            } else {
                ftpClient.disconnect();
                throw new IOException("FTP server refused connection.");
            }
        } catch (IOException e) {
            if (ftpClient.isConnected()) {
                try {
                    //断开连接
                    ftpClient.disconnect();
                } catch (IOException e1) {
                    throw new IOException("Could not disconnect from server.", e);
                }

            }
            throw new IOException("Could not connect to server.", e);
        }
    }
    
    /**   
     * 断开ftp连接   
     *    
     * @throws IOException
     */    
    private void disconnect(FTPClient ftpClient) throws IOException {     
        try {     
            ftpClient.logout();     
            if (ftpClient.isConnected()) {     
                ftpClient.disconnect();     
            }     
        } catch (IOException e) {     
            throw new IOException("Could not disconnect from server.", e);     
        }     
    }     
    
    //---------------------------------------------------------------------      
    // public method      
    //---------------------------------------------------------------------      
    /**   
     * 上传一个本地文件到远程指定文件   
     *    
     * @param serverFile 服务器端文件名(包括完整路径)   
     * @param localFile 本地文件名(包括完整路径)   
     * @return 成功时，返回true，失败返回false   
     */
    public boolean put(String serverFile, String localFile) throws IOException {     
        return put(serverFile, localFile, false);     
    }     
    
    /**   
     * 上传一个本地文件到远程指定文件   
     *    
     * @param serverFile 服务器端文件名(包括完整路径)   
     * @param localFile 本地文件名(包括完整路径)   
     * @param delFile 成功后是否删除文件   
     * @return 成功时，返回true，失败返回false   
     */
    public boolean put(String serverFile, String localFile, boolean delFile) throws IOException {
        FTPClient ftpClient = null;     
        InputStream input = null;     
        try {     
            ftpClient = getFTPClient();     
            // 处理传输      
            input = new FileInputStream(localFile);
            log.info(">>>Ftp directory : {}", ftpClient.printWorkingDirectory());
            int lastIndex = serverFile.lastIndexOf("/");
            String subStr = serverFile.substring(0, lastIndex);
            Boolean bo = ftpClient.changeWorkingDirectory(subStr);
            log.info(">>>Ftp directory : {}", ftpClient.printWorkingDirectory());
            if(!bo){
            	boolean result = ftpClient.makeDirectory(serverFile.substring(0, serverFile.lastIndexOf("/")));
            	log.info("Make dir result : {}", result);
            	log.info("Change dir result : {}", ftpClient.changeWorkingDirectory(serverFile.substring(0, serverFile.lastIndexOf("/"))));

            }
            log.info("serverFile is {}",serverFile);
            ftpClient.storeFile(serverFile, input);     
            input.close();
            if (delFile) {     
                (new File(localFile)).delete();     
            }     
            return true;
        } catch (FileNotFoundException e) {     
            throw new IOException("local file not found.", e);     
        } catch (IOException e) {
            throw e;
        } finally {     
            try {     
                if (input != null) {     
                    input.close();     
                }     
            } catch (Exception e) {     
                throw new IOException("Couldn't close FileInputStream.", e);     
            }     
            if (ftpClient != null) {
                disconnect(ftpClient);
            }     
        }     
    }
    
    /**   
     * 下载一个远程文件到本地的指定文件   
     *    
     * @param serverFile 服务器端文件名(包括完整路径)   
     * @param localFile 本地文件名(包括完整路径)   
     * @return 成功时，返回true，失败返回false   
     */
    public boolean get(String serverFile, String localFile) throws IOException {     
        return get(serverFile, localFile, false);     
    }     
    
    /**   
     * 下载一个远程文件到本地的指定文件   
     *    
     * @param serverFile 服务器端文件名(包括完整路径)   
     * @param localFile 本地文件名(包括完整路径)   
     * @return 成功时，返回true，失败返回false   
     */
    public boolean get(String serverFile, String localFile, boolean delFile) throws IOException {     
        OutputStream output = null;     
        try {     
            output = new FileOutputStream(localFile);     
            return get(serverFile, output, delFile);     
        } catch (FileNotFoundException e) {     
            throw new IOException("local file not found.", e);     
        } finally {     
            try {     
                if (output != null) {     
                    output.close();     
                }     
            } catch (IOException e) {     
                throw new IOException("Couldn't close FileOutputStream.", e);     
            }     
        }     
    }     
         
    /**   
     * 下载一个远程文件到指定的流   
     * 处理完后记得关闭流   
     *    
     * @param serverFile   
     * @param output   
     * @return   
     */
    public boolean get(String serverFile, OutputStream output) throws IOException {     
        return get(serverFile, output, false);     
    }     
         
    /**   
     * 下载一个远程文件到指定的流   
     * 处理完后记得关闭流   
     *    
     * @param serverFile   
     * @param output   
     * @param delFile   
     * @return   
     */
    public boolean get(String serverFile, OutputStream output, boolean delFile) throws IOException {     
        FTPClient ftpClient = null;     
        try {     
            ftpClient = getFTPClient();
            if(log.isDebugEnabled()){
                for(FTPFile ftpFile : ftpClient.listFiles()){
                    log.debug("FTP文件下载 >>> 请求文件: {}, FTP文件: {}, 匹配结果: {}", serverFile, ftpFile.getName(), serverFile.equals(ftpFile.getName()));
                }
            }
            // 处理传输
            boolean result = ftpClient.retrieveFile(serverFile, output);
            log.info("获取FTP文件结果：{} , serverFile : {}", result, serverFile);
            if (delFile) {
                // 删除远程文件
                ftpClient.deleteFile(serverFile);     
            }     
            return true;     
        } catch (IOException e) {     
            throw new IOException("Couldn't get file from server.", e);     
        } finally {     
            if (ftpClient != null) {     
                disconnect(ftpClient); //断开连接      
            }     
        }     
    }     
         
    /**   
     * 从ftp服务器上删除一个文件   
     *    
     * @param delFile   
     * @return   
     */
    public boolean delete(String delFile) throws IOException {     
        FTPClient ftpClient = null;     
        try {     
            ftpClient = getFTPClient();     
            ftpClient.deleteFile(delFile);     
            return true;     
        } catch (IOException e) {     
            throw new IOException("Couldn't delete file from server.", e);     
        } finally {     
            if (ftpClient != null) {     
                disconnect(ftpClient); //断开连接      
            }     
        }     
    }     
         
    /**   
     * 批量删除   
     *    
     * @param delFiles   
     * @return   
     */
    public boolean delete(String[] delFiles) throws IOException {     
        FTPClient ftpClient = null;     
        try {     
            ftpClient = getFTPClient();     
            for (String s : delFiles) {     
                ftpClient.deleteFile(s);     
            }     
            return true;     
        } catch (IOException e) {     
            throw new IOException("Couldn't delete file from server.", e);     
        } finally {     
            if (ftpClient != null) {     
                disconnect(ftpClient); //断开连接      
            }     
        }     
    }     
    
    /**   
     * 列出远程默认目录下所有的文件   
     *    
     * @return 远程默认目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组   
     */
    public String[] listNames() throws IOException {     
        return listNames(null);     
    }     
    
    /**   
     * 列出远程目录下所有的文件   
     *    
     * @param remotePath 远程目录名   
     * @return 远程目录下所有文件名的列表，目录不存在或者目录下没有文件时返回0长度的数组   
     */
    public String[] listNames(String remotePath) throws IOException {     
        FTPClient ftpClient = null;     
        try {     
            ftpClient = getFTPClient();     
            String[] listNames = ftpClient.listNames(remotePath);     
            return listNames;     
        } catch (IOException e) {     
            throw new IOException("列出远程目录下所有的文件时出现异常", e);     
        } finally {     
            if (ftpClient != null) {     
                disconnect(ftpClient); //断开连接      
            }     
        }     
    }     
    public boolean isExist(String remoteFilePath)throws IOException{  
         FTPClient ftpClient = null;
        try{  
            ftpClient = getFTPClient();  
            File file=new File(remoteFilePath);  
               
            String remotePath=remoteFilePath.substring(0,(remoteFilePath.indexOf(file.getName())-1));  
            String[] listNames = ftpClient.listNames(remotePath);     
            System.out.println(remoteFilePath);  
            for(int i=0;i<listNames.length;i++){  
  
                if(remoteFilePath.equals(listNames[i])){  
                    flag=true;  
                    System.out.println("文件:"+file.getName()+"已经存在了");  
                    break;  
                      
                }else {  
                    flag=false;  
                }  
            }  
              
        } catch (IOException e) {     
            throw new IOException("查询文件是否存在文件时出现异常", e);     
        } finally {     
            if (ftpClient != null) {     
                disconnect(ftpClient); //断开连接      
            }     
        }     
        return flag;  
    }

    public static void main(String[] args) {
        try {
            FTPClientUtil ftp = new FTPClientUtil("10.45.80.12", 21, "ftp_test", "!QAZ2wsx");
            ftp.put("/sql0826.txt", "C:\\Users\\xsh12148\\Desktop\\sql0826.txt");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
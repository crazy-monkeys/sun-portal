package com.crazy.portal.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * Created by weiying on 2019/8/2.
 */
public class JaxbXmlUtil {

    public static <T> T convertSoapXmlToJavaBean(String xml, Class<T> t) throws Exception {
        SOAPMessage message = MessageFactory.newInstance(SOAPConstants.DEFAULT_SOAP_PROTOCOL).createMessage(null, new ByteArrayInputStream(xml.getBytes()));
        JAXBContext jc = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
    }

    public static <T> T convertSoapXmlToJavaBean2(String xml, Class<T> t) throws Exception {
        SOAPMessage message = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL).createMessage(null, new ByteArrayInputStream(xml.getBytes()));
        JAXBContext jc = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
    }

    /**
     * pojo转换成xml
     *
     * @param obj 待转化的对象
     * @return xml格式字符串
     * @throws Exception JAXBException
     */
    public static String convertToXml(Object obj) throws Exception {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        // 指定是否使用换行和缩排对已编组 XML 数据进行格式化的属性名称。
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, writer);

        return writer.toString();
    }
}

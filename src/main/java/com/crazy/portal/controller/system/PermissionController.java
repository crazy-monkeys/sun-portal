package com.crazy.portal.controller.system;

import com.crazy.portal.annotation.OperationLog;
import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.bean.system.PermissionBean;
import com.crazy.portal.config.exception.BusinessException;
import com.crazy.portal.controller.BaseController;
import com.crazy.portal.entity.system.Resource;
import com.crazy.portal.entity.system.Role;
import com.crazy.portal.entity.system.User;
import com.crazy.portal.service.system.PermissionService;
import com.crazy.portal.service.system.RoleService;
import com.crazy.portal.service.system.UserService;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.Enums;
import com.crazy.portal.util.ErrorCodes.SystemManagerEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 23:37 2019/6/10
 * @Modified by:
 */
//@RestController
@Slf4j
@RequestMapping("/permission")
public class PermissionController extends BaseController{

    @javax.annotation.Resource
    private PermissionService permissionService;
    @javax.annotation.Resource
    private UserService userService;
    @javax.annotation.Resource
    private RoleService roleService;


    /**
     * 获取当前登录用户下拥有的所有资源
     * @return
     */
    @GetMapping(value="/getAll")
    public BaseResponse getAll(){

        Map<String,Object> root = new HashMap<>();
        root.put("id",0);
        root.put("resourceName","菜单");
        root.put("children",Collections.EMPTY_LIST);
        Role currentRole = super.getCurrentRole();
        Integer roleID;
        if(currentRole.getRoleCode().equals("ADMIN")){
            roleID = null;
        }else{
            roleID = currentRole.getId();
        }
        List<Resource> list = permissionService.queryResourceList(roleID);
        if(!list.isEmpty()){
            //获取树形结构
            List<Resource> resources = permissionService.resourceTree(list);
            root.put("children",resources);
        }
        return super.successResult(root);
    }

    /**
     * 获取角色对应的资源id
     * @param roleCode
     * @return
     */
    @GetMapping(value = "/findPermission/{roleCode}")
    public BaseResponse findPermission(@PathVariable String roleCode) {
        BusinessUtil.notNull(roleCode,SystemManagerEnum.ROLE_EMPTY_CODE);
        Role role = roleService.findRoleByCode(roleCode);
        BusinessUtil.notNull(role,SystemManagerEnum.ROLE_NOT_EXIST);
        List<Integer> resourceIds = permissionService.findPermissionIds(Collections.singletonList(role.getId()));
        return super.successResult(resourceIds);
    }

    /**
     * 给角色赋资源权限
     * @return
     */
    @OperationLog
    @PostMapping(value = "/savePermission")
    public BaseResponse empowerment(@RequestBody PermissionBean permissionBean) {
        BusinessUtil.notNull(permissionBean.getRoleCode(),SystemManagerEnum.ROLE_EMPTY_CODE);
        List<Integer> permissionIds = permissionBean.getPermissionIds();
        permissionService.savePermission(permissionIds,permissionBean.getRoleCode(),super.getCurrentUserId());
        return super.successResult();
    }

    /**
     * 给指定用户赋角色
     * @param loginName
     * @param roleCode
     * @return
     */
    @OperationLog
    @PostMapping(value = "/improveUserPerm")
    public BaseResponse improveUserPerm(@RequestParam String loginName,
                                        @RequestParam String roleCode){

        User user = userService.findUser(loginName);
        BusinessUtil.notNull(user,SystemManagerEnum.USER_NOT_EXISTS);
        Role role = roleService.findRoleByCode(roleCode);
        BusinessUtil.notNull(role,SystemManagerEnum.ROLE_NOT_EXIST);
        permissionService.improveUserPerm(role.getId(),user.getId(),super.getCurrentUser().getId());
        return super.successResult();
    }


    /**
     * 添加资源信息
     * @return
     */
    @OperationLog
    @PostMapping(value="/addResource")
    public BaseResponse addResource(@RequestBody Resource resource){
        if(resource.getParentId() == null
                || StringUtils.isEmpty(resource.getResourceName())
                || StringUtils.isEmpty(resource.getResourceUrl())
                || resource.getResourceType() == null){

            throw new BusinessException(SystemManagerEnum.RESOURCE_ILLEGAL.getCode(),
                    SystemManagerEnum.RESOURCE_ILLEGAL.getMsg());
        }
        //如果资源类型不在枚举中定义
        if(!Enums.RESOURCE_TYPE_ENUM.getResourceTypes().contains(resource.getResourceType().intValue())){
            throw new BusinessException(SystemManagerEnum.RESOURCE_TYPE_NOT_EXIST.getCode(),
                    SystemManagerEnum.RESOURCE_TYPE_NOT_EXIST.getMsg());
        }
        if(permissionService.findResource(resource.getParentId()) == null && 0 != resource.getParentId()){
            throw new BusinessException(SystemManagerEnum.RESOURCE_PARENT_NOT_EXIST.getCode(),
                    SystemManagerEnum.RESOURCE_PARENT_NOT_EXIST.getMsg());
        }
        if(permissionService.findResource(resource.getResourceName()) != null){
            throw new BusinessException(SystemManagerEnum.RESOURCE_EXIST.getCode(),
                    SystemManagerEnum.RESOURCE_EXIST.getMsg());
        }
        permissionService.saveResource(resource,super.getCurrentUser().getId());
        return super.successResult();
    }

    /**
     * 准备数据
     * @return
     */
    @GetMapping(value="/findRes/{resourceId}")
    public BaseResponse preEdit(@PathVariable Integer resourceId){
        Resource resource = permissionService.findResource(resourceId);
        BusinessUtil.notNull(resource,SystemManagerEnum.RESOURCE_NOT_EXIST);
        return super.successResult(resource);
    }

    /**
     * 修改资源信息
     * @return
     */
    @OperationLog
    @PostMapping(value="/editResource")
    public BaseResponse editResource(@RequestBody Resource resource){
        if(resource.getResourceType() == null
                || StringUtils.isEmpty(resource.getResourceName())
                || StringUtils.isEmpty(resource.getResourceUrl())
                || resource.getId() == null){

            throw new BusinessException(SystemManagerEnum.RESOURCE_ILLEGAL.getCode(),
                    SystemManagerEnum.RESOURCE_ILLEGAL.getMsg());
        }

        if(!Enums.RESOURCE_TYPE_ENUM.getResourceTypes().contains(resource.getResourceType())){
           throw new BusinessException(SystemManagerEnum.RESOURCE_TYPE_NOT_EXIST.getCode(),
                    SystemManagerEnum.RESOURCE_TYPE_NOT_EXIST.getMsg());
        }

        Resource res = permissionService.findResource(resource.getId());
        BusinessUtil.notNull(res,SystemManagerEnum.RESOURCE_NOT_EXIST);

        if(resource.getParentId() != 0){
            Resource parentRes = permissionService.findResource(resource.getParentId());
            BusinessUtil.notNull(parentRes,SystemManagerEnum.RESOURCE_PARENT_NOT_EXIST);
        }
        permissionService.saveResource(resource,super.getCurrentUser().getId());
        return super.successResult();
    }

    /**
     * 删除资源信息
     * @return
     */
    @OperationLog
    @DeleteMapping(value="/delResource/{resourceId}")
    public BaseResponse deleteResource(@PathVariable Integer resourceId){

        Resource resource = permissionService.findResource(resourceId);
        BusinessUtil.notNull(resource,SystemManagerEnum.RESOURCE_NOT_EXIST);

        List<Resource> resourceList = permissionService.findByParentId(resourceId);
        BusinessUtil.assertTrue(resourceList.isEmpty(),SystemManagerEnum.RESOURCE_HAS_CHILDREN);

        int result = permissionService.getRoleCountByResourceId(resourceId);
        BusinessUtil.assertFlase(result > 0,SystemManagerEnum.RESOURCE_USED);

        permissionService.deleteResource(resourceId);
        return super.successResult();
    }
}

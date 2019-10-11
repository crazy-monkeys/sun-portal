package com.crazy.portal.controller.system;

import com.crazy.portal.annotation.OperationLog;
import com.crazy.portal.bean.BaseResponse;
import com.crazy.portal.controller.BaseController;
import com.crazy.portal.entity.system.Role;
import com.crazy.portal.service.system.RoleService;
import com.crazy.portal.util.BusinessUtil;
import com.crazy.portal.util.ErrorCodes.SystemManagerEnum;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 22:49 2019/6/10
 * @Modified by:
 */
@RestController
@Slf4j
@RequestMapping("/permission")
public class RoleController extends BaseController {

    @Resource
    private RoleService roleService;

    /**
     * 获取当前页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/rolePageInfo")
    public BaseResponse userSetting(@RequestParam(required = false) String roleCode,
                                    @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                                    @RequestParam(required = false,defaultValue = "10") Integer pageSize) {

        PageInfo<Role> pager = roleService.queryRoleListPag(roleCode, pageNum,pageSize);
        return super.successResult(pager);
    }

    @GetMapping(value = "/findRoles")
    public BaseResponse findRoles() {
        List<Role> roles = roleService.findRoles();
        return super.successResult(roles);
    }

    /**
     * 新增角色
     * @return
     */
    @OperationLog
    @PostMapping(value = "/saveRole")
    public BaseResponse saveRole(@RequestBody Role role) {
        BusinessUtil.assertEmpty(role.getRoleCode(),SystemManagerEnum.ROLE_EMPTY_CODE);
        BusinessUtil.assertEmpty(role.getRoleName(),SystemManagerEnum.ROLE_EMPTY_NAME);
        BusinessUtil.notNull(role.getRoleType(),SystemManagerEnum.ROLE_EMPTY_TYPE);

        Role roleCodeQuery = roleService.findRoleByCode(role.getRoleCode());
        BusinessUtil.isNull(roleCodeQuery,SystemManagerEnum.ROLE_CODE_EXISTS);

        Role roleNameQuery = roleService.findRoleByName(role.getRoleName());
        BusinessUtil.isNull(roleNameQuery,SystemManagerEnum.ROLE_NAME_EXISTS);

        role.setActive((short)1);
        role.setCreateTime(new Date());
        role.setCreateUserId(super.getCurrentUser().getId());
        roleService.saveRole(role);
        return super.successResult();
    }

    /**
     * 查询角色详情
     * @return
     */
    @GetMapping(value = "/findRole/{roleCode}")
    public BaseResponse findRole(@PathVariable String roleCode) {
        Role role = roleService.findRoleByCode(roleCode);
        BusinessUtil.notNull(role,SystemManagerEnum.ROLE_NOT_EXIST);
        BusinessUtil.assertEmpty(role.getRoleName(),SystemManagerEnum.ROLE_NOT_EXIST);
        return super.successResult(role);
    }

    /**
     * 修改角色
     * @return
     */
    @OperationLog
    @PostMapping(value = "/updateRole")
    public BaseResponse updateRole(@RequestBody Role role) {
        BusinessUtil.notNull(role.getId(),SystemManagerEnum.ROLE_EMPTY_ID);
        BusinessUtil.assertEmpty(role.getRoleCode(),SystemManagerEnum.ROLE_EMPTY_CODE);

        Role currentRole = roleService.findRoleById(role.getId());
        BusinessUtil.notNull(currentRole,SystemManagerEnum.ROLE_NOT_EXIST);

        String currentRoleName = currentRole.getRoleName();
        String currentRoleCode = currentRole.getRoleCode();

        Role checkRoleName = roleService.findRoleByName(role.getRoleName());
        if(checkRoleName != null && !currentRoleName.equals(checkRoleName.getRoleName())){
            BusinessUtil.assertFlase(true,SystemManagerEnum.ROLE_NAME_EXISTS);
        }

        Role checkRoleCode = roleService.findRoleByCode(role.getRoleCode());
        if(checkRoleCode != null && !currentRoleCode.equals(checkRoleCode.getRoleCode())){
            BusinessUtil.assertFlase(true,SystemManagerEnum.ROLE_CODE_EXISTS);
        }

        role.setUpdateTime(new Date());
        role.setUpdateUserId(super.getCurrentUser().getId());
        roleService.saveRole(role);
        return super.successResult();
    }

    /**
     * 删除角色
     * @param roleCode
     * @return
     */
    @OperationLog
    @DeleteMapping("/delete/{roleCode}")
    public BaseResponse delete(@PathVariable String roleCode){
        roleService.deleteRole(roleCode);
        return super.successResult();
    }
}

package com.puxinxiaolin.weblog.admin.service.impl;

import com.puxinxiaolin.weblog.admin.model.vo.UpdateAdminUserPasswordRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminUserService;
import com.puxinxiaolin.weblog.common.domain.mapper.UserMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.utils.Response;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Response updatePassword(UpdateAdminUserPasswordRequestVO updateAdminUserPasswordRequestVO) {
        String username = updateAdminUserPasswordRequestVO.getUsername();
        String password = updateAdminUserPasswordRequestVO.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        int count = userMapper.updatePasswordByUsername(username, encodePassword);
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

}

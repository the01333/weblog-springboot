package com.puxinxiaolin.weblog.admin.service.impl;

import com.puxinxiaolin.weblog.admin.model.vo.user.FindUserInfoRequestVO;
import com.puxinxiaolin.weblog.admin.model.vo.user.UpdateAdminUserPasswordRequestVO;
import com.puxinxiaolin.weblog.admin.service.AdminUserService;
import com.puxinxiaolin.weblog.common.domain.mapper.UserMapper;
import com.puxinxiaolin.weblog.common.enums.ResponseCodeEnum;
import com.puxinxiaolin.weblog.common.utils.Response;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 修改密码
     *
     * @param updateAdminUserPasswordRequestVO
     * @return
     */
    @Override
    public Response updatePassword(UpdateAdminUserPasswordRequestVO updateAdminUserPasswordRequestVO) {
        String username = updateAdminUserPasswordRequestVO.getUsername();
        String password = updateAdminUserPasswordRequestVO.getPassword();

        String encodePassword = passwordEncoder.encode(password);

        int count = userMapper.updatePasswordByUsername(username, encodePassword);
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @Override
    public Response findUserInfo() {
        // 获取存储在 ThreadLocal 中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 拿到用户名
        String username = authentication.getName();

        FindUserInfoRequestVO findUserInfoRequestVO = FindUserInfoRequestVO.builder()
                .username(username).build();
        return Response.success(findUserInfoRequestVO);
    }

}

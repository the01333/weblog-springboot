package com.puxinxiaolin.weblog.jwt.handler;

import com.puxinxiaolin.weblog.common.utils.Response;
import com.puxinxiaolin.weblog.jwt.model.LoginResponseVO;
import com.puxinxiaolin.weblog.jwt.utils.JwtTokenHelper;
import com.puxinxiaolin.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 身份验证成功后的逻辑
 * @author: YCcLin
 * @date: 2025/1/15
 **/
@Component
@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtTokenHelper jwtTokenHelper;

    /**
     * 从 Authentication 中获取用户的 UserDetails 实例，接着获取用户名去生成 Token，最后返回数据
     *
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 从 authentication 对象中获取用户的 UserDetails 实例，这里是获取用户的用户名
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 通过用户名生成 Token
        String username = userDetails.getUsername();
        String token = jwtTokenHelper.generateToken(username);

        LoginResponseVO loginResponseVO = LoginResponseVO.builder()
                .token(token)
                .build();

        ResultUtil.ok(response, Response.success(loginResponseVO));
    }

}

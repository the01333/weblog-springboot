package com.puxinxiaolin.weblog.jwt.filter;

import com.puxinxiaolin.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description: 用于校验 Token 令牌
 * 继承 OncePerRequestFilter 确保每个请求只被过滤一次
 * @author: YCcLin
 * @date: 2025/1/15
 **/
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;

    @Resource
    private JwtTokenHelper jwtTokenHelper;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(tokenHeaderKey);

        // Token 格式 -> Authorization: Bearer <token>    所以需要判断是否以 Bearer 开头
        if (StringUtils.startsWith(header, tokenPrefix)) {
            // 截取 Token 令牌
            String token = StringUtils.substring(header, 7);
            log.info("token: {} ", token);

            // 判空
            if (StringUtils.isNotBlank(token)) {
                try {
                    jwtTokenHelper.validateToken(token);
                } catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                         IllegalArgumentException e) {
                    // 抛出异常，统一让 AuthenticationEntryPoint 处理响应参数
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                    return;
                } catch (ExpiredJwtException e) {
                    authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                    return;
                }
            }

            // 从 Token 中解析出用户名
            String username = jwtTokenHelper.getUsernameByToken(token);
            if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication()) &&
                    StringUtils.isNotBlank(username)) {
                // 根据用户名获取用户详情信息
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // 将用户信息存入 authentication，方便后续校验
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 继续执行下一个过滤器
        filterChain.doFilter(request, response);
    }

}

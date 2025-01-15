package com.puxinxiaolin.weblog.jwt.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.puxinxiaolin.weblog.jwt.exception.UsernameOrPasswordNullException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @description: 自定义继承了 AbstractAuthenticationProcessingFilter 的登录过滤器，处理 JWT 的用户身份验证过程
 * @author: YCcLin
 * @date: 2025/1/14
 **/
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 指定用户登录的访问地址，当请求路径匹配 /login 并且请求方法为 POST 时，该过滤器将被触发
     */
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 解析提交的 JSON 数据
        JsonNode jsonNode = objectMapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode = jsonNode.get("password");

        // 判断用户名、密码是否为空
        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode) ||
                StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        String username = usernameNode.textValue();
        String password = passwordNode.textValue();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 触发 Security 的身份验证管理器执行身份验证，并返回验证结果
        return getAuthenticationManager()
                .authenticate(usernamePasswordAuthenticationToken);
    }

}

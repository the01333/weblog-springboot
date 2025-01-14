package com.puxinxiaolin.weblog.jwt.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @description: 自定义 UserDetailsService 实现类
 * UserDetailsService: 从数据源中加载用户信息并转为 UserDetails，提供给 Security 进行身份验证
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO 从数据库中查询

        // 暂时先写死，密码为 xiaolin, 这里填写的密文，数据库中也是存储此种格式
        // authorities 用于指定角色，这里写死为 ADMIN 管理员
        return User.withUsername("xiaolin")
                .password("$2a$10$17r1EE4xWiYKmmCqQaAjdeb64G68iZZic7E9e2AOZy8LaCwhiDSWi")
                .authorities("ADMIN")
                .build();
    }

}

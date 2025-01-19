package com.puxinxiaolin.weblog.jwt.service.impl;

import com.puxinxiaolin.weblog.common.domain.dos.UserDO;
import com.puxinxiaolin.weblog.common.domain.dos.UserRoleDO;
import com.puxinxiaolin.weblog.common.domain.mapper.UserMapper;
import com.puxinxiaolin.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 自定义 UserDetailsService 实现类
 * UserDetailsService: 从数据源中加载用户信息并转为 UserDetails，提供给 Security 进行身份验证
 * @author: YCcLin
 * @date: 2025/1/14
 **/
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userMapper.findByUsername(username);

        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        // 用户角色
        List<UserRoleDO> roleDOList = userRoleMapper.selectByUsername(username);

        String[] roleArr = null;
        if (!CollectionUtils.isEmpty(roleDOList)) {
            List<String> roleList = roleDOList.stream()
                    .map(UserRoleDO::getRole)
                    .collect(Collectors.toList());
            roleArr = roleList.toArray(new String[roleList.size()]);
        }

        // authorities 用于指定角色
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }

}

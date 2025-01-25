package com.puxinxiaolin.weblog.admin.service;

import com.puxinxiaolin.weblog.admin.model.vo.user.UpdateAdminUserPasswordRequestVO;
import com.puxinxiaolin.weblog.common.utils.Response;

public interface AdminUserService {

    /**
     * 修改密码
     *
     * @param updateAdminUserPasswordRequestVO
     * @return
     */
    Response updatePassword(UpdateAdminUserPasswordRequestVO updateAdminUserPasswordRequestVO);

    /**
     * 获取当前登录用户信息
     * @return
     */
    Response findUserInfo();

}

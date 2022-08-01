package xyz.minic.xr.service.impl;

import xyz.minic.xr.bean.User;
import xyz.minic.xr.dao.UserDao;
import xyz.minic.xr.service.UserService;

/**
 * @author minic
 * @date 2022/07/22 16:13
 **/
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Override
    public User get(User user) {
        return ((UserDao) dao).get(user);
    }
}

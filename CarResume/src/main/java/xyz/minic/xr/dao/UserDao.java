package xyz.minic.xr.dao;

import xyz.minic.xr.bean.User;

public interface UserDao extends BaseDao<User> {
    User get(User user);
}

package xyz.minic.xr.service;

import xyz.minic.xr.bean.User;

public interface UserService extends BaseService<User>{
    User get(User user);
}

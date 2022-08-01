package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.User;
import xyz.minic.xr.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/22 16:13
 **/
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    @Override
    public boolean save(User mode) {
        Integer id = mode.getId();
        List<Object> args = new ArrayList<>();
        args.add(mode.getPassword());
        args.add(mode.getEmail());
        args.add(mode.getPhoto());
        args.add(mode.getIntro());
        args.add(mode.getName());
        args.add(mode.getBirthday());
        args.add(mode.getAddress());
        args.add(mode.getPhone());
        args.add(mode.getJob());
        args.add(mode.getTrait());
        args.add(mode.getInterests());

        String sql;
        if (id == null || id < 1) {
            sql = "INSERT INTO user(password, email, photo, intro, name, birthday, address, phone, job, trait, interests) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE user SET password = ?, email = ?, photo = ?, intro = ?, name = ?, birthday = ?, address = ?, phone = ?, job = ?, trait = ?, interests = ? WHERE id = ?";
            args.add(id);
        }
        return tpl.update(sql, args.toArray()) > 0;
    }

    @Override
    public User get(Integer id) {
        String sql = "SELECT id, created_time, password, email, photo, intro, name, birthday, address, phone, job, trait, interests FROM user WHERE id = ?";
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }

    @Override
    public List<User> list() {
        String sql = "SELECT id, created_time, password, email, photo, intro, name, birthday, address, phone, job, trait, interests FROM user";
        return tpl.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User get(User user) {
        String sql = "SELECT id, created_time, password, email, photo, intro, name, birthday, address, phone, job, trait, interests FROM user WHERE email = ? AND password = ?";
        List<User> users = tpl.query(sql, new BeanPropertyRowMapper<>(User.class), user.getEmail(), user.getPassword());
        return users.isEmpty() ? null : users.get(0);
    }
}

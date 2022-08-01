package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.Company;
import xyz.minic.xr.dao.CompanyDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/21 09:48
 **/
public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {

    @Override
    public boolean save(Company mode) {
        Integer id = mode.getId();
        List<Object> args = new ArrayList<>();
        args.add(mode.getName());
        args.add(mode.getLogo());
        args.add(mode.getWebsite());
        args.add(mode.getIntro());
        String sql;
        if (id == null || id < 1) {
            sql = "INSERT INTO company(name, logo, website, intro) VALUES(?, ?, ?, ?)";
        } else {
            sql = "UPDATE company SET name = ?, logo = ?, website = ?, intro = ? WHERE id = ?";
            args.add(id);
        }

        return tpl.update(sql, args.toArray()) > 0;
    }

    @Override
    public Company get(Integer id) {
        String sql = "SELECT id, created_time, name, logo, website, intro FROM company WHERE id = ?";
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(Company.class), id);
    }

    @Override
    public List<Company> list() {
        String sql = "SELECT id, created_time, name, logo, website, intro FROM company";
        return tpl.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }
//
//    @Override
//    protected String table() {
//        return "company";
//    }
}

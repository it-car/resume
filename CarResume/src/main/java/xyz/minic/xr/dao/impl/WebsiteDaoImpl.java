package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.Website;
import xyz.minic.xr.dao.WebsiteDao;
import xyz.minic.xr.util.Dbs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/14 14:21
 **/
public class WebsiteDaoImpl extends BaseDaoImpl<Website> implements WebsiteDao {

    /**
     * 新增or更新
     */
    public boolean save(Website website) {
        List<Object> args = new ArrayList<>();
        args.add(website.getFooter()); 
        Integer id = website.getId();
        String sql;
        if (id == null || id < 1) {//新增
            sql = "INSERT INTO website(footer) VALUES(?)";
        } else { //更新
            sql = "UPDATE website SET footer = ? WHERE id = ?";
            args.add(id);
        }
        return tpl.update(sql, args.toArray()) > 0;
    }

    /**
     * 获得单个对象
     * @return
     */
    @Override
    public Website get(Integer id) {
        return null;
    }

//    @Override
//    protected String table() {
//        return "website";
//    }

    /**
     * 查询所有
     * @return
     */
    public List<Website> list() {
        String sql = "SELECT id, created_time, footer FROM website";
        return tpl.query(sql, new BeanPropertyRowMapper<>(Website.class));
    }

}

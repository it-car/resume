package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.Award;
import xyz.minic.xr.dao.AwardDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/21 09:48
 **/
public class AwardDaoImpl extends BaseDaoImpl<Award> implements AwardDao {
    @Override
    public boolean save(Award award) {
        Integer id = award.getId();
        List<Object> args = new ArrayList<>();
        args.add(award.getName());
        args.add(award.getImage());
        args.add(award.getIntro());
        String sql;
        if (id == null || id < 1) {
            sql = "INSERT INTO award(name, image, intro) VALUES(?, ?, ?)";
        } else {
            sql = "UPDATE award SET name = ?, image = ?, intro = ? WHERE id = ?";
            args.add(id);
        }

        return tpl.update(sql, args.toArray()) > 0;
    }

    @Override
    public Award get(Integer id) {
        String sql = "SELECT id, created_time, name, image, intro FROM award WHERE id = ?";
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(Award.class), id);
    }

    @Override
    public List<Award> list() {
        String sql = "SELECT id, created_time, name, image, intro FROM award";
        return tpl.query(sql, new BeanPropertyRowMapper<>(Award.class));
    }

//    @Override
//    protected String table() {
//        return "award";
//    }
}

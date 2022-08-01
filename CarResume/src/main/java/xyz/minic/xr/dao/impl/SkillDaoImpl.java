package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.Skill;
import xyz.minic.xr.dao.SkillDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/16 19:42
 **/
public class SkillDaoImpl extends BaseDaoImpl<Skill> implements SkillDao {
    @Override
    public boolean save(Skill skill) {
        String sql;
        Integer id = skill.getId();
        List<Object> args = new ArrayList<>();
        args.add(skill.getName());
        args.add(skill.getLevel());

        if (id == null || id < 1) {
            sql = "INSERT INTO skill(name, level) VALUES(?, ?)";
        }else {
            sql = "UPDATE skill SET name = ?, level = ?  WHERE id = ?";
            args.add(id);
        }
        return tpl.update(sql,args.toArray()) > 0;
    }

    @Override
    public Skill get(Integer id) {
        String sql = "SELECT id, created_time, name, level FROM skill WHERE id = ?";
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(Skill.class), id);
    }

    @Override
    public List<Skill> list() {
        String sql = "SELECT id, created_time, name, level FROM skill";
        return tpl.query(sql, new BeanPropertyRowMapper<>(Skill.class));
    }

//    @Override
//    protected String table() {
//        return "skill";
//    }
}

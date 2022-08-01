package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import xyz.minic.xr.bean.Education;
import xyz.minic.xr.dao.EducationDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/15 22:39
 **/
public class EducationDaoImpl extends BaseDaoImpl<Education> implements EducationDao {

    //添加或更新
    public boolean save(Education education) {
        Integer id = education.getId();
        List<Object> args = new ArrayList<>();
        args.add(education.getName());
        args.add(education.getType());
        args.add(education.getIntro());
        args.add(education.getBeginDay());
        args.add(education.getEndDay());
        String sql;
        if (id == null || id < 1) { //新增
            sql = "INSERT INTO education(name, type, intro, begin_day, end_day) VALUE(?, ?, ?, ?, ?)";
        } else { //更改
            sql = "UPDATE education SET name = ?, type = ?, intro = ?,  begin_day = ?, end_day= ? WHERE id = ?";
            args.add(id);
        }
        return tpl.update(sql, args.toArray()) > 0;
    }

    //获取单个对象
    public Education get(Integer id) {
        String sql = "SELECT id, name, type, intro, begin_day, end_day FROM education WHERE id = ?";
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(Education.class), id);
    }

    //获取多个对象
    public List<Education> list() {
        String sql = "SELECT id, name, type, intro, begin_day, end_day FROM education";
        return tpl.query(sql, new BeanPropertyRowMapper<>(Education.class));
    }

//    @Override
//    protected String table() {
//        return "education";
//    }
}

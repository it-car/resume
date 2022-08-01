package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import xyz.minic.xr.bean.Company;
import xyz.minic.xr.bean.Experience;
import xyz.minic.xr.dao.ExperienceDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/21 09:48
 **/
public class ExperienceDaoImpl extends BaseDaoImpl<Experience> implements ExperienceDao {
    private static String listSql;
    private static String getSql;
    private static RowMapper<Experience> rowMapper;
    static {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("t1.id, t1.created_time, t1.job, t1.intro, t1.begin_day, t1.end_day, ");
        sql.append("t2.id, t2.created_time, t2.name, t2.logo, t2.website, t2.intro ");
        sql.append("FROM experience t1 JOIN company t2 ON t1.company_id = t2.id");
        listSql = sql.toString();
        getSql = listSql + " WHERE t1.id = ?";

        //自定义映射规则
        rowMapper = (resultSet, i) -> {
            Experience experience = new Experience();
            experience.setId(resultSet.getInt("t1.id"));
            experience.setCreatedTime(resultSet.getDate("t1.created_time"));
            experience.setIntro(resultSet.getString("t1.intro"));
            experience.setJob(resultSet.getString("t1.job"));
            experience.setBeginDay(resultSet.getDate("t1.begin_day"));
            experience.setEndDay(resultSet.getDate("t1.end_day"));

            Company company = new Company();
            experience.setCompany(company);

            company.setId(resultSet.getInt("t2.id"));
            company.setCreatedTime(resultSet.getDate("t2.created_time"));
            company.setIntro(resultSet.getString("t2.intro"));
            company.setLogo(resultSet.getString("t2.logo"));
            company.setWebsite(resultSet.getString("t2.website"));
            company.setName(resultSet.getString("t2.name"));

            return experience;
        };
    }

    @Override
    public boolean save(Experience mode) {
        Integer id = mode.getId();
        List<Object> args = new ArrayList<>();
        args.add(mode.getJob());
        args.add(mode.getCompany().getId());
        args.add(mode.getBeginDay());
        args.add(mode.getEndDay());
        args.add(mode.getIntro());
        String sql;
        if (id == null || id < 1) {
            sql = "INSERT INTO experience(job, company_id, begin_day, end_day, intro) VALUES(?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE experience SET job = ?, company_id = ?, begin_day = ?, end_day = ?, intro = ? WHERE id = ?";
            args.add(id);
        }

        return tpl.update(sql, args.toArray()) > 0;
    }

    @Override
    public Experience get(Integer id) {
        return tpl.queryForObject(getSql, rowMapper, id);
    }

    @Override
    public List<Experience> list() {
        return tpl.query(listSql, rowMapper);
    }

}

package xyz.minic.xr.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import xyz.minic.xr.bean.Company;
import xyz.minic.xr.bean.Project;
import xyz.minic.xr.bean.Project;
import xyz.minic.xr.dao.ExperienceDao;
import xyz.minic.xr.dao.ProjectDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author minic
 * @date 2022/07/21 09:48
 **/
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {
    private static String listSql;
    private static String getSql;
    private static RowMapper<Project> rowMapper;
    static {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("t1.id, t1.created_time, t1.name, t1.website, t1.image, t1.intro, t1.begin_day, t1.end_day, ");
        sql.append("t2.id, t2.created_time, t2.name, t2.logo, t2.website, t2.intro ");
        sql.append("FROM project t1 JOIN company t2 ON t1.company_id = t2.id");
        listSql = sql.toString();
        getSql = listSql + " WHERE t1.id = ?";

        //自定义映射规则
        rowMapper = (resultSet, i) -> {
            Project project = new Project();
            project.setId(resultSet.getInt("t1.id"));
            project.setCreatedTime(resultSet.getDate("t1.created_time"));
            project.setImage(resultSet.getString("t1.image"));
            project.setWebsite(resultSet.getString("t1.website"));
            project.setIntro(resultSet.getString("t1.intro"));
            project.setName(resultSet.getString("t1.name"));
            project.setBeginDay(resultSet.getDate("t1.begin_day"));
            project.setEndDay(resultSet.getDate("t1.end_day"));

            Company company = new Company();
            project.setCompany(company);

            company.setId(resultSet.getInt("t2.id"));
            company.setCreatedTime(resultSet.getDate("t2.created_time"));
            company.setIntro(resultSet.getString("t2.intro"));
            company.setLogo(resultSet.getString("t2.logo"));
            company.setWebsite(resultSet.getString("t2.website"));
            company.setName(resultSet.getString("t2.name"));

            return project;
        };
    }


    @Override
    public boolean save(Project mode) {

        Integer id = mode.getId();
        List<Object> args = new ArrayList<>();
        args.add(mode.getName());
        args.add(mode.getWebsite());
        args.add(mode.getImage());
        args.add(mode.getCompany().getId());
        args.add(mode.getBeginDay());
        args.add(mode.getEndDay());
        args.add(mode.getIntro());
        String sql;
        if (id == null || id < 1) {
            sql = "INSERT INTO project(name, website, image, company_id, begin_day, end_day, intro) VALUES(?, ?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE project SET name = ?, website = ?, image = ?, company_id = ?, begin_day = ?, end_day = ?, intro = ? WHERE id = ?";
            args.add(id);
        }

        return tpl.update(sql, args.toArray()) > 0;
    }

    @Override
    public Project get(Integer id) {
        return tpl.queryForObject(getSql, rowMapper, id);
    }

    @Override
    public List<Project> list() {
        return tpl.query(listSql, rowMapper);
    }

}

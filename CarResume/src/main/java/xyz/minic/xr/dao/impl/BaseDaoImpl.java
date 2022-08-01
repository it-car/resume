package xyz.minic.xr.dao.impl;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.minic.xr.dao.BaseDao;
import xyz.minic.xr.util.Dbs;
import xyz.minic.xr.util.Strings;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author minic
 * @date 2022/07/16 10:32
 **/
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    //数据库 springJDBC + druid连接池
    protected static JdbcTemplate tpl;
    static {
        try(InputStream is = Dbs.class.getClassLoader().getResourceAsStream("druid.properties");) {
            Properties properties = new Properties();
            properties.load(is);
            DataSource ds = DruidDataSourceFactory.createDataSource(properties);
            tpl = new JdbcTemplate(ds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String tableName = table();
    protected String table() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class beanCls = (Class) type.getActualTypeArguments()[0];
//        beanCls.getName();  -->  xyz.minic.xr.bean.Award
//        beanCls.getSimpleName(); -->Award
        return Strings.underlineCase(beanCls.getSimpleName());
    }
    
    public boolean remove(Integer id) {
        String sql = "DELETE FROM" + tableName +" WHERE id = ?";
        return tpl.update(sql, id) > 0;
    }

    //删除多个或者删除单个
    public boolean remove(List<Integer> ids) {
        List<Object> args = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(tableName).append(" WHERE id in (");
        for (Integer id : ids) {
            args.add(id);
            sql.append("?, ");
        }
        sql.replace(sql.length() - 2, sql.length(), ")");
        // DELETE FROM education WHERE id in (?, ?, ?)
        return tpl.update(sql.toString(), args.toArray()) > 0;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        return tpl.queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }
}

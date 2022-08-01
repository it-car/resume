package xyz.minic.xr.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author minic
 * @date 2022/07/14 14:16
 **/
//数据库连接
public class Dbs {
    private static JdbcTemplate tpl;
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

    public static JdbcTemplate getTpl() {
        return tpl;
    }
}

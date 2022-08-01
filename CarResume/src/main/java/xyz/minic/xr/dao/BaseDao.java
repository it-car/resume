package xyz.minic.xr.dao;

import java.util.List;

/**
 * @author minic
 * @date 2022/07/16 10:23
 **/
public interface BaseDao<T> {
    //添加或更新
    boolean save(T mode);

    //获取单个对象
    T get(Integer id);

    //获取多个对象
    List<T> list();

    //删除多个或者删除单个
    boolean remove(List<Integer> ids);
    boolean remove(Integer id);

    int count();
}

package xyz.minic.xr.service.impl;

import xyz.minic.xr.dao.BaseDao;
import xyz.minic.xr.service.BaseService;

import java.util.List;

/**
 * @author minic
 * @date 2022/07/16 10:51
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected BaseDao<T> dao = dao();
    protected BaseDao<T> dao() {
        // com.mj.xr.service.impl.WebsiteServiceImpl
        // com.mj.xr.dao.impl.WebsiteDaoImpl
        try {
            String clsName = getClass().getName()
                    .replace(".service.", ".dao.")
                    .replace("Service", "Dao");
            return (BaseDao<T>) Class.forName(clsName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //添加或更新
    public boolean save(T bean) {
        return dao.save(bean);
    }

    //获取单个对象
    public T get(Integer id) {
        return dao.get(id);
    }

    //获取多个对象
    public List<T> list() {
        return dao.list();
    }

    public boolean remove(Integer id) {
        return dao.remove(id);
    }

    @Override
    public int count() {
        return dao.count();
    }

    //删除多个或者删除单个
    public boolean remove(List<Integer> ids) {
        return dao.remove(ids);
    }

}

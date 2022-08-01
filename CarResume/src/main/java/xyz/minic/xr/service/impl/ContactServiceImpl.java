package xyz.minic.xr.service.impl;

import xyz.minic.xr.bean.Contact;
import xyz.minic.xr.bean.ContactListParam;
import xyz.minic.xr.bean.ContactListResult;
import xyz.minic.xr.dao.ContactDao;
import xyz.minic.xr.dao.impl.ContactDaoImpl;
import xyz.minic.xr.service.ContactService;

/**
 * @author minic
 * @date 2022/07/25 10:25
 **/
public class ContactServiceImpl extends BaseServiceImpl<Contact> implements ContactService {
    @Override
    public ContactListResult list(ContactListParam param) {
//        return (new ContactDaoImpl()).list(param); //错误 或者说不应该这样写
        return ((ContactDao) dao).list(param);
    }

    @Override
    public boolean read(Integer id) {
//        return (new ContactDaoImpl()).read(id); //错误 或者说不应该这样写
        return ((ContactDao) dao).read(id);
    }
}

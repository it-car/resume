package xyz.minic.xr.dao;

import xyz.minic.xr.bean.Contact;
import xyz.minic.xr.bean.ContactListParam;
import xyz.minic.xr.bean.ContactListResult;

public interface ContactDao extends BaseDao<Contact> {
    boolean read(Integer id);
    ContactListResult list(ContactListParam param);
}

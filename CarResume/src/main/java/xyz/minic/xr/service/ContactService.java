package xyz.minic.xr.service;

import xyz.minic.xr.bean.Contact;
import xyz.minic.xr.bean.ContactListParam;
import xyz.minic.xr.bean.ContactListResult;

public interface ContactService extends BaseService<Contact>{
    ContactListResult list(ContactListParam param);
    boolean read(Integer  id);
}

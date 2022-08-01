package xyz.minic.xr.bean;

import xyz.minic.xr.bean.base.BaseBean;

/**
 * @author minic
 * @date 2022/07/16 22:56
 **/
public class Company extends BaseBean {
    private String name;
    private String logo;
    private String website;
    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}

package xyz.minic.xr.bean;

import xyz.minic.xr.bean.base.DateBean;

/**
 * @author minic
 * @date 2022/07/16 22:57
 **/
public class Project extends DateBean {
    private String name;
    private String intro;
    private String website;
    private String  image;
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

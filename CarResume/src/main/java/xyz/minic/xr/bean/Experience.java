package xyz.minic.xr.bean;

import xyz.minic.xr.bean.base.DateBean;

/**
 * @author minic
 * @date 2022/07/16 22:57
 **/
public class Experience extends DateBean {
    private String job;
    private String intro;
    private Company company;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

}

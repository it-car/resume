package xyz.minic.xr.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import xyz.minic.xr.bean.base.BaseBean;

/**
 * @author minic
 * @date 2022/07/16 19:37
 **/
public class Skill extends BaseBean {
    private String name;
    private Integer level;
    /**
     * 0：了解
     * 1：熟悉
     * 2：掌握
     * 3：精通
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getLevelString() {
        switch (level) {
            case 1: return "熟悉";
            case 2: return "掌握";
            case 3: return "精通";
            default: return "了解";
        }
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

package zhongger.entity;


import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 大学实体类
 * @Date 2020.5.17
 */

public class University implements Serializable {
    private Integer id;
    private String universityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }
}

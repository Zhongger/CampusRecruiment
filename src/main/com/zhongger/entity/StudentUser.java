package zhongger.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 学生用户
 * @Date 2020.5.17
 */
@Data
@ToString
public class StudentUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private Integer universityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Integer universityId) {
        this.universityId = universityId;
    }
}

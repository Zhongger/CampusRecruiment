package zhongger.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 企业用户
 * @Date 2020.5.17
 */
@Data
@ToString
public class BusinessUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String companyId;//所在公司id
    private String companyName;//所在公司名称

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

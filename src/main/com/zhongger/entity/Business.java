package zhongger.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 公司实体类
 * @Date 2020.5.17
 */
@Data
@ToString
public class Business implements Serializable {
    private Integer id;
    private String companyId;
    private String companyName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

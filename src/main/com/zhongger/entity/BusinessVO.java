package zhongger.entity;

import java.io.Serializable;

/**
 * Created by Zhong Mingyi on 2020/6/27.
 */
public class BusinessVO implements Serializable {
    private Integer id;
    private String companyName;
    private String companyId;
    private String password;
    private String companyFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyFile() {
        return companyFile;
    }

    public void setCompanyFile(String companyFile) {
        this.companyFile = companyFile;
    }

    public BusinessVO(String companyName, String companyId, String password, String companyFile) {
        this.companyName = companyName;
        this.companyId = companyId;
        this.password = password;
        this.companyFile = companyFile;
    }

    public BusinessVO() {
    }

    @Override
    public String toString() {
        return "BusinessVO{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", password='" + password + '\'' +
                ", companyFile='" + companyFile + '\'' +
                '}';
    }
}

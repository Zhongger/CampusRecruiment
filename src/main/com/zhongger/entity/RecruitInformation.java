package zhongger.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Struct;
import java.util.Date;

/**
 * @Author Zhongger
 * @Description
 * @Date
 */
@Data
@ToString
public class RecruitInformation implements Serializable {
    private Integer id;//id
    private String requirement;//招聘要求
    private Integer companyId;//公司id
    private String companyName;//公司名称
    private String salary;//薪资 3k~4k
    private String deadLine;//截止日期
    private String address;//工作地点

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public RecruitInformation(Integer id, String requirement, Integer companyId, String companyName, String salary, String deadLine, String address) {
        this.id = id;
        this.requirement = requirement;
        this.companyId = companyId;
        this.companyName = companyName;
        this.salary = salary;
        this.deadLine = deadLine;
        this.address = address;
    }

    public RecruitInformation() {
    }
}

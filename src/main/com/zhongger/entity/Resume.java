package zhongger.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 简历
 * @Date 2020.5.17
 */
@Data
@ToString
public class Resume implements Serializable {
    private Integer id;
    private String studentUsername;//学生用户名
    private String studentName;//学生姓名
    private String email;//E-mail
    private String attachmentResume;//附件简历的路径

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAttachmentResume() {
        return attachmentResume;
    }

    public void setAttachmentResume(String attachmentResume) {
        this.attachmentResume = attachmentResume;
    }
}

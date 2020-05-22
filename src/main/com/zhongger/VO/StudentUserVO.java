package zhongger.VO;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description 前端传值的StudentUser
 * @Date 2020.5.17
 */
@Data
@ToString
public class StudentUserVO implements Serializable {
    private String username;
    private String password;

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
}

package zhongger.VO;

import lombok.Data;
import lombok.ToString;

/**
 * @Author Zhongger
 * @Description 封装前端传递的业务对象
 * @Date 2020.5.17
 */
@Data
@ToString
public class BusinessUserVO {
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

    @Override
    public String toString() {
        return "BusinessUserVO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

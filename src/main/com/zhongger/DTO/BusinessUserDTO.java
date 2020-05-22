package zhongger.DTO;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Zhongger
 * @Description
 * @Date
 */
@Data
@ToString
public class BusinessUserDTO implements Serializable {
    private String username;
    private String password;
}

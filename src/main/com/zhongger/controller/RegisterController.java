package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import sun.security.provider.MD5;
import zhongger.VO.BusinessUserVO;
import zhongger.VO.StudentUserVO;
import zhongger.dao.AdminDao;
import zhongger.dao.BusinessUserDao;
import zhongger.dao.StudentUserDao;
import zhongger.entity.BusinessUser;
import zhongger.entity.StudentUser;
import zhongger.utils.MyMD5Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 用户注册
 */
public class RegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");//确认密码
        password=MyMD5Util.encrypt(password);
        JSONObject jsonObject = new JSONObject();

        StudentUserVO studentUser = new StudentUserVO();
        studentUser.setUsername(username);
        studentUser.setPassword(password);
        try {
            String flag2 = StudentUserDao.selectUsername(studentUser);
            if ("ok".equals(flag2)){//用户名已存在
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");//注册失败
                jsonObject.put("msg","usernameIsExist");//用户名已存在
                resp.getWriter().print(jsonObject);
                return;
            }else {
                StudentUser user = new StudentUser();
                user.setUsername(username);
                user.setPassword(password);
                StudentUserDao.insert(user);
                jsonObject.put("code",2000);
                jsonObject.put("flag","success");//注册成功
                jsonObject.put("msg","register_success");
                jsonObject.put("user",user);
                resp.getWriter().print(jsonObject);
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

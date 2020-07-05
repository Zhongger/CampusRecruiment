package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import zhongger.DTO.BusinessUserDTO;
import zhongger.VO.BusinessUserVO;
import zhongger.VO.StudentUserVO;
import zhongger.dao.BusinessUserDao;
import zhongger.dao.StudentUserDao;
import zhongger.entity.StudentUser;
import zhongger.utils.MyMD5Util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description 登录
 * @Date 2020.5.17
 */


public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            jsonObject.put("code", 2000);
            jsonObject.put("flag", "fail");
            jsonObject.put("user", null);
            jsonObject.put("msg", "usernameOrPasswordIsBank");//用户名密码不能为空
            resp.getWriter().print(jsonObject);
            return;
        }
        password = MyMD5Util.encrypt(password);
        System.out.println(password);
        BusinessUserVO businessUserVO = new BusinessUserVO();
        businessUserVO.setUsername(username);
        businessUserVO.setPassword(password);
        StudentUserVO studentUserVO = new StudentUserVO();
        studentUserVO.setUsername(username);
        studentUserVO.setPassword(password);

        String flag1 = null;
        String flag2 = null;
        try {
            flag1 = BusinessUserDao.selectUsername(businessUserVO);
            if ("ok".equals(flag1)) {//企业用户名存在
                BusinessUserDTO businessUserDTO = BusinessUserDao.select(businessUserVO);
                if (businessUserDTO != null) {
                    jsonObject.put("code", 2000);
                    jsonObject.put("flag", "success");//登录成功
                    jsonObject.put("user", businessUserDTO);
                    jsonObject.put("msg", "login_success");
                    session.setAttribute("businessUser",businessUserDTO);
                    resp.getWriter().print(jsonObject);
                    return;
                } else {
                    jsonObject.put("code", 2000);
                    jsonObject.put("flag", "fail");//登录失败
                    jsonObject.put("user", null);
                    jsonObject.put("msg", "passwordError");//密码错误
                    resp.getWriter().print(jsonObject);
                    return;
                }
            }
            flag2 = StudentUserDao.selectUsername(studentUserVO);
            if ("ok".equals(flag2)) {//学生用户名存在
                StudentUser studentUser = StudentUserDao.select(studentUserVO);
                if (studentUser != null) {
                    jsonObject.put("code", 2000);
                    jsonObject.put("flag", "success");//登录成功
                    jsonObject.put("user", studentUser);
                    jsonObject.put("msg", "login_success");
                    session.setAttribute("studentUser",studentUser);
                    resp.getWriter().print(jsonObject);
                    return;
                } else {
                    jsonObject.put("code", 2000);
                    jsonObject.put("flag", "fail");//登录失败
                    jsonObject.put("user", null);
                    jsonObject.put("msg", "passwordError");//密码错误
                    resp.getWriter().print(jsonObject);
                    return;
                }

            }
            //用户名不存在，前往注册
            jsonObject.put("code", 2000);
            jsonObject.put("flag", "fail");//登录失败
            jsonObject.put("user", null);
            jsonObject.put("msg", "usernameIsNotExist");//密码错误
            resp.getWriter().print(jsonObject);
            return;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}

package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import zhongger.dao.AdminDao;
import zhongger.entity.Admin;
import zhongger.utils.MyMD5Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class AdminLoginController extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        password = MyMD5Util.encrypt(password);
        JSONObject jsonObject = new JSONObject();
        HttpSession session = req.getSession();
        Admin admin = new Admin(username, password);
        Admin adminFromDB = AdminDao.findByUsernamePassword(admin);
        if (adminFromDB!=null){
            jsonObject.put("code",2000);
            jsonObject.put("msg","login_success");
            jsonObject.put("admin",adminFromDB.getUsername());
            jsonObject.put("flag","success");
            resp.getWriter().print(jsonObject);
            session.setAttribute("admin",adminFromDB);
            return;
        }else {
            jsonObject.put("code",2000);
            jsonObject.put("msg","no admin");
            jsonObject.put("admin",null);
            jsonObject.put("flag","fail");
            resp.getWriter().print(jsonObject);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

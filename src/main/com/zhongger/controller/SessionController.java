package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.entity.BusinessUser;
import zhongger.entity.StudentUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class SessionController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        StudentUser studentUser = (StudentUser)session.getAttribute("studentUser");
        BusinessUser businessUser = (BusinessUser)session.getAttribute("businessUser");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",2000);
        jsonObject.put("msg","session");
        if (studentUser!=null){
            jsonObject.put("studentUser",studentUser);
        }
        if (businessUser!=null){
            jsonObject.put("businessUser",businessUser);
        }
        resp.getWriter().print(jsonObject);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

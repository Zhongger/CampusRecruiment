package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.dao.AdminDao;
import zhongger.entity.BusinessVO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Zhong Mingyi on 2020/6/27.
 */
public class AdminNoVerifyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String username = req.getParameter("username");
        JSONObject jsonObject = new JSONObject();
        try {
            List<BusinessVO> res = AdminDao.findAllVerifyBusinessNo(username);
            System.out.println(res);
            jsonObject.put("code",2000);
            jsonObject.put("msg","success");
            jsonObject.put("data",res);
            resp.getWriter().print(jsonObject);
            return;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            jsonObject.put("code",5000);
            jsonObject.put("msg","server error");
            resp.getWriter().print(jsonObject);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

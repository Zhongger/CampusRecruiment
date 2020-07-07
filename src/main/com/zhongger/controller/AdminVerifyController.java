package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.dao.AdminDao;
import zhongger.dao.BusinessDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class AdminVerifyController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String companyId = req.getParameter("companyId");
        String passOrNot = req.getParameter("passOrNot");
        JSONObject jsonObject = new JSONObject();
        try {
            int i = AdminDao.verifyCompanyRegister(username, companyId, passOrNot);
            //int flag = AdminDao.passOrNot(username, companyId);
            if (i  == 1){
                BusinessDao.registerFlag(companyId,passOrNot);//设置公司的信息已经验证
                jsonObject.put("code",2000);
                jsonObject.put("msg","verify success");
                resp.getWriter().print(jsonObject);
                return;
            }else {
                jsonObject.put("code",2000);
                jsonObject.put("msg","verify fail");
                resp.getWriter().print(jsonObject);
                return;
            }
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

package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.dao.BusinessDao;
import zhongger.entity.BusinessVO;
import zhongger.utils.MyMD5Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Zhong Mingyi on 2020/6/27.
 */
public class BusinessLoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyId = req.getParameter("companyId");
        String password = req.getParameter("password");
        resp.setCharacterEncoding("UTF-8");
        String password2 = MyMD5Util.encrypt(password);
        HttpSession session = req.getSession();
        BusinessVO businessVO = null;
        try {
            businessVO = BusinessDao.login(companyId, password2);
            JSONObject jsonObject = new JSONObject();
            if (businessVO!=null){
                jsonObject.put("code",2000);
                jsonObject.put("msg","login success");
                jsonObject.put("data",businessVO);
                session.setAttribute("businessUser",businessVO);
                resp.getWriter().print(jsonObject);
                return;
            }else {
                jsonObject.put("code",2000);
                jsonObject.put("msg","error");//企业号、密码不正确或未通过审核
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

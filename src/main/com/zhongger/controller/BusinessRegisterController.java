package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.dao.BusinessDao;
import zhongger.entity.BusinessVO;
import zhongger.utils.MyMD5Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class BusinessRegisterController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String companyName = req.getParameter("companyName");
        String companyId = req.getParameter("companyId");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");//确认密码
        JSONObject jsonObject = new JSONObject();
        if (!password.equals(rePassword)){
            jsonObject.put("code",2000);
            jsonObject.put("msg","password_unMatch");//两次输入的密码不一致
            resp.getWriter().print(jsonObject);
            return;
        }
        String password2 = MyMD5Util.encrypt(password);
        String companyFile = req.getParameter("companyFile");

        BusinessVO businessVO = new BusinessVO(companyName, companyId, password2, companyFile);
        try {
            int i = BusinessDao.insertToVerify(businessVO);
            if (i == 1){
                jsonObject.put("code",2000);
                jsonObject.put("msg","waiting verify");//企业用户注册完，等待管理员审核
                resp.getWriter().print(jsonObject);
                return;
            }else {
                jsonObject.put("code",2000);
                jsonObject.put("msg","companyId exist");//企业id已存在
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

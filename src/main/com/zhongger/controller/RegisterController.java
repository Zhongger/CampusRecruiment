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
        MyMD5Util.encrypt(password);
        String status = req.getParameter("status");//身份
        JSONObject jsonObject = new JSONObject();
        if (!password.equals(rePassword)){//两次输入的密码不一致
            jsonObject.put("code",2000);
            jsonObject.put("flag","success");//登录成功
            jsonObject.put("msg","password_unMatch");//两次输入的密码不一致
            resp.getWriter().print(jsonObject);
        }
        if ("student".equals(status)){
            String universityId = req.getParameter("universityName");
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
                }else {
                    StudentUser user = new StudentUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.setUniversityId(Integer.parseInt(universityId));
                    StudentUserDao.insert(user);
                    jsonObject.put("code",2000);
                    jsonObject.put("flag","success");//注册成功
                    jsonObject.put("msg","register_success");
                    jsonObject.put("user",user);
                    resp.getWriter().print(jsonObject);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else {
            String companyId = req.getParameter("companyId");
            String companyName = req.getParameter("companyName");
            BusinessUserVO businessUserVO = new BusinessUserVO();
            businessUserVO.setUsername(username);
            businessUserVO.setPassword(password);
            try {
                String flag1 = BusinessUserDao.selectUsername(businessUserVO);
                if ("ok".equals(flag1)){//用户名已存在
                    jsonObject.put("code",2000);
                    jsonObject.put("flag","fail");//注册失败
                    jsonObject.put("msg","usernameIsExist");//用户名已存在
                    resp.getWriter().print(jsonObject);
                }else {
                    BusinessUser businessUser = new BusinessUser();
                    businessUser.setUsername(username);
                    businessUser.setPassword(password);
                    businessUser.setCompanyId(companyId);
                    businessUser.setCompanyId(companyId);
                    businessUser.setCompanyName(companyName);
                    int flag = AdminDao.passOrNot(Integer.parseInt(companyId));
                    if (flag==1){
                        BusinessUserDao.insert(businessUser);
                        jsonObject.put("code",2000);
                        jsonObject.put("flag","success");//注册成功
                        jsonObject.put("msg","register_success");
                        jsonObject.put("user",businessUser);
                    }else {
                        jsonObject.put("code",2000);
                        jsonObject.put("flag","fail");//注册成功
                        jsonObject.put("msg","no verify");//未通过管理员验证，请等待
                        jsonObject.put("user",null);
                    }

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

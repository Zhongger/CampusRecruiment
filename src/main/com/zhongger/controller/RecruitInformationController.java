package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import zhongger.dao.RecruitInformationDao;
import zhongger.entity.BusinessUser;
import zhongger.entity.BusinessVO;
import zhongger.entity.RecruitInformation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Zhongger
 * @Description
 * @Date
 */
public class RecruitInformationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String operate = req.getParameter("operate");
        JSONObject jsonObject = new JSONObject();
        resp.setCharacterEncoding("UTF-8");
        System.out.println(operate);
        if (operate.equals("add")){
            String requirement = req.getParameter("requirement");
            HttpSession session = req.getSession();
            BusinessVO businessVO = (BusinessVO) session.getAttribute("businessUser");
            String companyId = businessVO.getCompanyId();
            String companyName = businessVO.getCompanyName();
            String salary = req.getParameter("salary");
            String deadLine = req.getParameter("deadLine");
            String address = req.getParameter("address");
            String applyPosition = req.getParameter("applyPosition");
            RecruitInformation recruitInformation = new RecruitInformation();
            recruitInformation.setRequirement(requirement);
            recruitInformation.setCompanyId(companyId);
            recruitInformation.setCompanyName(companyName);
            recruitInformation.setSalary(salary);
            recruitInformation.setDeadLine(deadLine);
            recruitInformation.setAddress(address);
            recruitInformation.setApplyPosition(applyPosition);
            try {
                int i = RecruitInformationDao.insert(recruitInformation);
                if (i>0){
                    jsonObject.put("code",2000);
                    jsonObject.put("flag","success");//添加成功
                    jsonObject.put("msg","add success");
                    jsonObject.put("data",recruitInformation);
                    resp.getWriter().println(jsonObject);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");//添加失败
                jsonObject.put("msg","add fail");
                resp.getWriter().println(jsonObject);
                return;
            }
        }else if (operate.equals("update")){
            String id = req.getParameter("id");
            String requirement = req.getParameter("requirement");
            HttpSession session = req.getSession();
            BusinessVO businessVO = (BusinessVO) session.getAttribute("businessUser");
            String companyId = businessVO.getCompanyId();
            String companyName = businessVO.getCompanyName();
            String salary = req.getParameter("salary");
            String deadLine = req.getParameter("deadLine");
            String address = req.getParameter("address");
            String applyPosition = req.getParameter("applyPosition");
            RecruitInformation recruitInformation = new RecruitInformation(Integer.parseInt(id),requirement,companyId,companyName,salary,deadLine,address,applyPosition);
            try {
                int update = RecruitInformationDao.update(recruitInformation);
                if (update>0){
                    jsonObject.put("code",2000);
                    jsonObject.put("flag","success");
                    jsonObject.put("msg","update success");
                    jsonObject.put("data",recruitInformation);
                    resp.getWriter().println(jsonObject);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");
                jsonObject.put("msg","update fail");
                resp.getWriter().println(jsonObject);
                return;
            }
        }else if (operate.equals("delete")){
            String id = req.getParameter("id");
            try {
                int delete = RecruitInformationDao.delete(Integer.parseInt(id));
                if (delete>0){
                    jsonObject.put("code",2000);
                    jsonObject.put("flag","success");
                    jsonObject.put("msg","delete success");
                    resp.getWriter().println(jsonObject);
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");
                jsonObject.put("msg","delete fail");
                resp.getWriter().println(jsonObject);
                return;
            }
        }else if (operate.equals("all")){
            List<RecruitInformation> list = null;
            try {
                list = RecruitInformationDao.selectList();
                jsonObject.put("code",2000);
                jsonObject.put("flag","success");
                jsonObject.put("msg","success");
                jsonObject.put("data",list);
                resp.getWriter().println(jsonObject);
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");
                jsonObject.put("msg","fail");
                jsonObject.put("data",null);
                resp.getWriter().println(jsonObject);
                return;
            }

        }else if (operate.equals("showDetail")){
            String id = req.getParameter("id");
            RecruitInformation recruitInformation = null;
            try {
                recruitInformation = RecruitInformationDao.selectByRecruitInfoId(Integer.parseInt(id));
                jsonObject.put("code",2000);
                jsonObject.put("flag","success");
                jsonObject.put("msg","success");
                jsonObject.put("data",recruitInformation);
                resp.getWriter().println(jsonObject);
                return;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");
                jsonObject.put("msg","fail");
                jsonObject.put("data",null);
                resp.getWriter().println(jsonObject);
                return;
            }
        } else{
            String condition = req.getParameter("condition");
            try {
                List<RecruitInformation> list = RecruitInformationDao.selectListBySearch(condition);
                jsonObject.put("code",2000);
                jsonObject.put("flag","success");
                jsonObject.put("msg","success");
                jsonObject.put("data",list);
                resp.getWriter().println(jsonObject);
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                jsonObject.put("code",2000);
                jsonObject.put("flag","fail");
                jsonObject.put("msg","select fail");
                jsonObject.put("data",null);
                resp.getWriter().println(jsonObject);
                return;
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

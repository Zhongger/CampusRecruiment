package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import zhongger.dao.RecruitInformationDao;
import zhongger.dao.ResumeDao;
import zhongger.entity.RecruitInformation;
import zhongger.entity.Resume;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
@MultipartConfig
public class ResumeController extends HttpServlet {

    private static int MAX_POST_SIZE = 1024*1024*100;


    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operate = req.getParameter("operate");
        JSONObject jsonObject = new JSONObject();
        resp.setCharacterEncoding("UTF-8");
        if (operate.equals("add")){//投递简历与上传简历文件
            String studentName = req.getParameter("studentName");
            String studentUsername = req.getParameter("studentUsername");
            String recruitInfoId = req.getParameter("recruitInfoId");
            String applyPosition = req.getParameter("applyPosition");
            String phoneNum = req.getParameter("phoneNum");
            String email = req.getParameter("email");
            String attachmentResume = "文件名";

            Resume resume = new Resume(studentUsername, Integer.parseInt(recruitInfoId), studentName, applyPosition, phoneNum, email, attachmentResume);
            int insert = 0;
            try {
                insert = ResumeDao.insert(resume);
                if (insert == 1){
                    jsonObject.put("code",2000);
                    jsonObject.put("msg","add success");
                    jsonObject.put("flag","success");
                    jsonObject.put("data",resume);
                    resp.getWriter().print(jsonObject);
                }else {
                    jsonObject.put("code",2000);
                    jsonObject.put("msg","add fail");
                    jsonObject.put("flag","fail");
                    jsonObject.put("data",null);
                    resp.getWriter().print(jsonObject);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                jsonObject.put("code",5000);
                jsonObject.put("msg","server error");
                jsonObject.put("flag","fail");
                jsonObject.put("data",null);
                resp.getWriter().print(jsonObject);
            }
        }else if (operate.equals("download")){//下载简历
            String file = req.getParameter("file"); //客户端传递的需要下载的文件名
            String path = req.getServletContext().getRealPath("")+"/"+file; //默认认为文件在当前项目的根目录
            FileInputStream fis = new FileInputStream(path);
            resp.setCharacterEncoding("utf-8");
            resp.setHeader("Content-Disposition", "attachment; filename="+file);
            ServletOutputStream out = resp.getOutputStream();
            byte[] bt = new byte[1024];
            int length = 0;
            while((length=fis.read(bt))!=-1){
                out.write(bt,0,length);
            }
            out.close();
        }else if (operate.equals("findByRecruitInfoId")){
            String recruitInfoId = req.getParameter("recuitInfoId");
            System.out.println(recruitInfoId);
            List<Resume> byRecruitInfoID = ResumeDao.findByRecruitInfoID(Integer.parseInt(recruitInfoId));
            jsonObject.put("code",2000);
            jsonObject.put("msg","success");
            jsonObject.put("flag","success");
            jsonObject.put("data",byRecruitInfoID);
            resp.getWriter().print(jsonObject);
        } else if (operate.equals("findByStuUsername")){
            String studentUsername = req.getParameter("studentUsername");
            List<Resume> byRecruitInfoID = ResumeDao.findByStudentUsername(studentUsername);
            jsonObject.put("code",2000);
            jsonObject.put("msg","success");
            jsonObject.put("flag","success");
            jsonObject.put("data",byRecruitInfoID);
            resp.getWriter().print(jsonObject);
        } else if (operate.equals("findRecruitInfoByStuUsername")){
            String studentUsername = req.getParameter("studentUsername");
            List<Integer> rids = ResumeDao.findRecruitInfoIdByStudentUsername(studentUsername);
            List<RecruitInformation> list = new ArrayList<>();
            for (Integer rid : rids) {
                RecruitInformation information = RecruitInformationDao.selectByRecruitInfoId(rid);
                list.add(information);
            }
            jsonObject.put("code",2000);
            jsonObject.put("msg","success");
            jsonObject.put("flag","success");
            jsonObject.put("data",list);
            resp.getWriter().print(jsonObject);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import zhongger.dao.ResumeDao;
import zhongger.entity.Resume;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Zhong Mingyi on 2020/7/6.
 */
public class SubmitResumeController extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        req.setCharacterEncoding("UTF-8");
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items = upload.parseRequest(req);
        StringBuffer sb = new StringBuffer();
        String resumeFile = null;
        for (FileItem item : items) {
            String name = item.getFieldName();
            InputStream inputStream = item.getInputStream();
            if (!name.equals("resumeFile")){
                String string = item.getString();
                string = new String(string.getBytes("ISO8859_1"), StandardCharsets.UTF_8);
                sb.append(string+"&&");
            }else {
                String[] split = sb.toString().split("&&");
                String studentName = split[0];
                String studentUsername = split[1];
                String recruitInfoId = split[2];
                String path=req.getServletContext().getRealPath("/");
                String fieldName = studentName+"_"+studentUsername+"_"+recruitInfoId+"_"+item.getName();
                String filePath = path+fieldName;
                resumeFile = fieldName;
                File file = new File(filePath);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                int line;
                while ((line = bufferedInputStream.read())!=-1){
                    fileOutputStream.write(line);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                bufferedInputStream.close();
            }
        }
        String[] split = sb.toString().split("&&");
        String studentName = split[0];
        String studentUsername = split[1];
        String recruitInfoId = split[2];
        String applyPosition = split[3];
        String phoneNum = split[4];
        String email = split[5];
        Resume resume = new Resume(studentUsername, Integer.parseInt(recruitInfoId), studentName, applyPosition, phoneNum, email, resumeFile);
        int insert = ResumeDao.insert(resume);
        if (insert == 1){
            jsonObject.put("code",2000);
            jsonObject.put("msg","add success");
            jsonObject.put("flag","success");
            jsonObject.put("data",resume);
            resp.getWriter().print(jsonObject);
            return;
        }else {
            jsonObject.put("code",2000);
            jsonObject.put("msg","add fail");
            jsonObject.put("flag","fail");
            jsonObject.put("data",null);
            resp.getWriter().print(jsonObject);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

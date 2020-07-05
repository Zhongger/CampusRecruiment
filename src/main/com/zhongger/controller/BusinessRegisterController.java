package zhongger.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import zhongger.dao.BusinessDao;
import zhongger.entity.BusinessVO;
import zhongger.utils.MyMD5Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class BusinessRegisterController extends HttpServlet {
    private static final int MAX_POST_SIZE = 100 * 1024 * 1024;
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
        String companyFile = null;
        for (FileItem item : items) {
            String name = item.getFieldName();
            InputStream inputStream = item.getInputStream();
            if (!name.equals("companyFile")){
                String string = item.getString();
                string = new String(string.getBytes("ISO8859_1"), StandardCharsets.UTF_8);
                sb.append(string+"&&");

            }else {
                String[] split = sb.toString().split("&&");
                String companyName = split[0];
                String companyId = split[1];
                String path=req.getServletContext().getRealPath("/");
                System.out.println(path);
                String fieldName = companyName+"_"+companyId+"_"+item.getName();
                String filePath = path+fieldName;
                companyFile = fieldName;
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
        String companyName = split[0];
        String companyId = split[1];
        String password = split[2];
        String password2 = MyMD5Util.encrypt(password);
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

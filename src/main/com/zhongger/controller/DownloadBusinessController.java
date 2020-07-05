package zhongger.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author Zhongger
 * @Description
 * @Date
 */
public class DownloadBusinessController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("fileName");
        String path = req.getServletContext().getRealPath("")+"/"+fileName; //默认认为文件在当前项目的根目录
        FileInputStream fis = new FileInputStream(path);
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Disposition", "attachment; filename="+fileName);
        ServletOutputStream out = resp.getOutputStream();
        byte[] bt = new byte[1024];
        int length = 0;
        while((length=fis.read(bt))!=-1){
            out.write(bt,0,length);
        }
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}

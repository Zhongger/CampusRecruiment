package zhongger.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Zhong Mingyi on 2020/7/5.
 */
public class AdminDownloadFileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

package zhongger.Filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        JSONObject jsonObject = new JSONObject();
        HttpSession session = request.getSession();
        Object studentUserSession = session.getAttribute("studentUser");
        Object businessUserSession = session.getAttribute("businessUser");
        System.out.println(businessUserSession);
        if (studentUserSession==null&&businessUserSession==null){
            jsonObject.put("code",2000);
            jsonObject.put("msg","Please Login");
            jsonObject.put("flag","fail");
            response.getWriter().print(jsonObject);
            return;
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}

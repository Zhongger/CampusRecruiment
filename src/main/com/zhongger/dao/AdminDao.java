package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import zhongger.config.C3P0Pool;
import zhongger.entity.Admin;
import zhongger.entity.BusinessVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Zhong Mingyi on 2020/6/26.
 */
public class AdminDao {
    public static Admin findByUsernamePassword(Admin admin) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String username = admin.getUsername();
        String password = admin.getPassword();
        String sql = "SELECT * FROM studentUser WHERE username='" + username + "' AND password='" + password + "'";
        statement = connection.prepareStatement(sql);
        resultSet = statement.executeQuery(sql);
        BeanHandler<Admin> beanHandler = new BeanHandler<>(Admin.class);
        Admin handle = beanHandler.handle(resultSet);
        return handle;
    }

    public static void verifyCompanyRegister(String username,String companyId,String passOrNot) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int flag = 0;
        if (passOrNot.equals("yes")){
            flag = 1;
        }else {
            flag = 0;
        }
        String sql = "insert into ref_admin_business(username,companyId,flag) values (?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1,username);
        statement.setString(2,companyId);
        statement.setInt(3,flag);//1表示通过
        statement.executeUpdate(sql);
        C3P0Pool.close(null,statement,connection);
    }

    public static int passOrNot(String username,String companyId) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select flag from ref_admin_business where username='"+username+"' and companyId='"+companyId+"'";
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanHandler<Integer> beanHandler = new BeanHandler<>(Integer.class);
        Integer flag = beanHandler.handle(resultSet);
        C3P0Pool.close(null,statement,connection);
        return flag;
    }


    public static int passOrNot(Integer companyId) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select flag from ref_admin_business where companyId ="+companyId;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanHandler<Integer> beanHandler = new BeanHandler<>(Integer.class);
        Integer flag = beanHandler.handle(resultSet);
        C3P0Pool.close(null,statement,connection);
        return flag;
    }

    public static List<BusinessVO> findAllVerifyBusinessNo(String username) throws SQLException {//查询所以未审核的企业
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select * from verify_company where is_verify = 0";//查出未审核的企业
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanListHandler<BusinessVO> beanHandler = new BeanListHandler<>(BusinessVO.class);
        List<BusinessVO> businessVOList = beanHandler.handle(resultSet);
        String sql2 = "select companyId from ref_admin_business where username ='"+username+"'";//查询出此管理员待审核的全部
        statement = connection.prepareStatement(sql2);
        ResultSet resultSet2 = statement.executeQuery(sql2);
        BeanListHandler<String> beanHandler2 = new BeanListHandler<>(String.class);
        List<String> companyIdList = beanHandler2.handle(resultSet2);
        List<BusinessVO> res = new ArrayList<>();
        for (String companyId : companyIdList) {
            List<BusinessVO> collect = businessVOList.stream().filter(businessVO -> !businessVO.getCompanyId().equals(companyId)).collect(Collectors.toList());
            res.addAll(collect);
        }
        C3P0Pool.close(null,statement,connection);
        return res;

    }
}

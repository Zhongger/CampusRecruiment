package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import zhongger.DTO.BusinessUserDTO;
import zhongger.VO.BusinessUserVO;
import zhongger.config.C3P0Pool;
import zhongger.entity.Business;
import zhongger.entity.BusinessUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description 企业用户数据库操作类
 * @Date 2020.5.17
 */
public class BusinessUserDao {
    //增,用户注册
    public static int insert(BusinessUser businessUser) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO businessUser (username,password,companyId,companyName) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, businessUser.getUsername());
            statement.setString(2, businessUser.getPassword());
            statement.setString(3, businessUser.getCompanyId());
            statement.setString(4, businessUser.getCompanyName());
            updateLine = statement.executeUpdate();
            connection.commit();//提交事务
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();//回滚
        } finally {
            C3P0Pool.close(null, statement, connection);//关闭连接
        }
        return updateLine;
    }

    //查，用于登录
    public static BusinessUserDTO select(BusinessUserVO businessUserVO) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        BusinessUserDTO businessUserDTO = null;
        ResultSet resultSet = null;
        String username = businessUserVO.getUsername();
        String password = businessUserVO.getPassword();
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT username,password FROM businessUser WHERE username='" + username + "' AND password='" + password + "' FOR UPDATE";
            statement=connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            BeanHandler<BusinessUserDTO> beanHandler = new BeanHandler<>(BusinessUserDTO.class);
            businessUserDTO = beanHandler.handle(resultSet);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            C3P0Pool.close(resultSet, statement, connection);
        }
        return businessUserDTO;
    }

    //查用户名是否存在
    public static String selectUsername(BusinessUserVO businessUserVO) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        BusinessUser business = null;
        String username = businessUserVO.getUsername();
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM businessUser WHERE username='" + username + "' FOR UPDATE";
            statement=connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            BeanHandler<BusinessUser> beanHandler = new BeanHandler<>(BusinessUser.class);
            business = beanHandler.handle(resultSet);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            C3P0Pool.close(resultSet, statement, connection);
        }
        if (business == null) {
            return "usernameIsNotExist";//用户名不存在
        } else {
            return "ok";//用户名存在
        }
    }
}

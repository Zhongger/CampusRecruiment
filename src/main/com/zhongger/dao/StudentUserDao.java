package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import zhongger.VO.StudentUserVO;
import zhongger.config.C3P0Pool;
import zhongger.entity.StudentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description
 * @Date
 */
public class StudentUserDao {
    //增,用户注册
    public static int insert(StudentUser studentUser) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO businessUser (username,password,universityId) VALUES (?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, studentUser.getUsername());
            statement.setString(2, studentUser.getPassword());
            statement.setInt(3, studentUser.getUniversityId());
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
    public static StudentUser select(StudentUserVO studentUserVO) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        StudentUser studentUser = null;
        ResultSet resultSet = null;
        String username = studentUserVO.getUsername();
        String password = studentUserVO.getPassword();
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM studentUser WHERE username='" + username + "' AND password='" + password + "' FOR UPDATE";
            statement=connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            BeanHandler<StudentUser> beanHandler = new BeanHandler<>(StudentUser.class);
            studentUser = beanHandler.handle(resultSet);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            C3P0Pool.close(resultSet, statement, connection);
        }
        return studentUser;
    }

    //查用户名是否存在
    public static String selectUsername(StudentUserVO studentUserVO) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StudentUser studentUser = null;
        String username = studentUserVO.getUsername();
        try {
            connection.setAutoCommit(false);
            String sql = "SELECT * FROM studentUser WHERE username='" + username + "' FOR UPDATE";
            statement=connection.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);
            BeanHandler<StudentUser> beanHandler = new BeanHandler<>(StudentUser.class);
            studentUser = beanHandler.handle(resultSet);
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            C3P0Pool.close(resultSet, statement, connection);
        }
        if (studentUser == null) {
            return "usernameIsNotExist";//用户名不存在
        } else {
            return "ok";//用户名存在
        }
    }
}

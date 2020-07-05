package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import zhongger.DTO.BusinessUserDTO;
import zhongger.config.C3P0Pool;
import zhongger.entity.Resume;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Zhongger
 * @Description Resume表的操作类
 * @Date 2020.5.17
 */
public class ResumeDao {
    //新增用户简历
    public static int insert(Resume resume) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO resume (studentUsername,studentName,applyPosition,phoneNum,email,attachmentResume,recruitInfoId) VALUES (?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, resume.getStudentUsername());
            statement.setString(2, resume.getStudentName());
            statement.setString(3, resume.getApplyPosition());
            statement.setString(4, resume.getPhoneNum());
            statement.setString(5, resume.getEmail());
            statement.setString(6, resume.getAttachmentResume());
            statement.setInt(7,resume.getRecruitInfoId());
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
    //查询某个岗位的接收到的所有简历
    public static List<Resume> findByRecruitInfoID(Integer recruitInfoId) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select * from resume where recruitInfoId="+recruitInfoId;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanListHandler<Resume> beanHandler = new BeanListHandler<>(Resume.class);
        List<Resume> res = beanHandler.handle(resultSet);
        return res;
    }

    public static List<Resume> findByStudentUsername(String studentUsername) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select * from resume where studentUsername='"+studentUsername+"'";
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanListHandler<Resume> beanHandler = new BeanListHandler<>(Resume.class);
        List<Resume> res = beanHandler.handle(resultSet);
        return res;
    }

    public static List<Integer> findRecruitInfoIdByStudentUsername(String studentUsername) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "select recruitInfoId from resume where studentUsername='"+studentUsername+"'";
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanListHandler<Integer> beanHandler = new BeanListHandler<>(Integer.class);
        List<Integer> res = beanHandler.handle(resultSet);
        return res;
    }
}

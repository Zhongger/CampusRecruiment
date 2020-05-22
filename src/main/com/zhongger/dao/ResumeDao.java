package zhongger.dao;

import zhongger.config.C3P0Pool;
import zhongger.entity.Resume;
import zhongger.entity.StudentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description Resume表的操作类
 * @Date 2020.5.17
 */
public class ResumeDao {
    //增,用户注册
    public static int insert(Resume resume) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO resume (studentUsername,studentName,email,attachmentResume) VALUES (?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, resume.getStudentUsername());
            statement.setString(2, resume.getStudentName());
            statement.setString(3, resume.getEmail());
            statement.setString(4, resume.getAttachmentResume());
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
}

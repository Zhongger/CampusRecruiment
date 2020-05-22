package zhongger.dao;

import zhongger.config.C3P0Pool;
import zhongger.entity.Business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description 公司数据库操作类
 * @Date 2020.5.17
 */
public class BusinessDao {
    //增
    public static int insert (Business business) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO business (companyId,companyName) VALUES (?,?)" ;
            statement = connection.prepareStatement(sql);
            statement.setString(1,business.getCompanyId());
            statement.setString(2,business.getCompanyName());
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

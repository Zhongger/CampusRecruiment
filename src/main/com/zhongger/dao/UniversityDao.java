package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import zhongger.config.C3P0Pool;
import zhongger.entity.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author Zhongger
 * @Description university表数据库操作类
 * @Date 2020.5.17
 */
public class UniversityDao {
    //查大学
    public static List<University> selectList() throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM university FOR UPDATE" ;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        BeanListHandler<University> beanListHandler =new BeanListHandler<>(University.class);
        List<University> result = beanListHandler.handle(resultSet);
        C3P0Pool.close(resultSet,statement,connection);
        return result;
    }
}

package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import zhongger.config.C3P0Pool;
import zhongger.entity.RecruitInformation;

import java.sql.*;
import java.util.List;

/**
 * @Author Zhongger
 * @Description RecruitInformation表的数据库操作，引入了锁机制来解决并发修改问题
 * @Date 2020.5.17
 */
public class RecruitInformationDao {
    //增
    public static int insert (RecruitInformation recruitInformation) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String sql = "INSERT INTO recruitInformation (requirement,companyId,companyName,salary,deadLine,address,version,applyPosition) VALUES (?,?,?,?,?,?,?,?)" ;
            statement = connection.prepareStatement(sql);
            statement.setString(1,recruitInformation.getRequirement());
            statement.setInt(2,recruitInformation.getCompanyId());
            statement.setString(3,recruitInformation.getCompanyName());
            statement.setString(4, recruitInformation.getSalary());
            statement.setString(5, recruitInformation.getDeadLine());
            statement.setString(6, recruitInformation.getAddress());
            statement.setInt(7,0);//从第0个版本开始
            statement.setString(8,recruitInformation.getApplyPosition());//从第0个版本开始
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
    //删
    public static int delete (Integer id) throws SQLException{
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            String lock="SELECT * FROM recruitInformation FOR UPDATE";
            String sql = "DELETE FROM recruitInformation WHERE id="+id ;
            connection.prepareStatement(lock);
            statement = connection.prepareStatement(sql);
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

    //查
    public static List<RecruitInformation> selectList() throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM recruitInformation FOR UPDATE" ;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        BeanListHandler<RecruitInformation> beanListHandler =new BeanListHandler<>(RecruitInformation.class);
        List<RecruitInformation> result = beanListHandler.handle(resultSet);
        C3P0Pool.close(resultSet,statement,connection);
        return result;
    }

    public static RecruitInformation selectByRecruitInfoId(Integer id) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM recruitInformation WHERE id="+id;
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        BeanHandler<RecruitInformation> beanHandler =new BeanHandler<>(RecruitInformation.class);
        RecruitInformation result = beanHandler.handle(resultSet);
        C3P0Pool.close(resultSet,statement,connection);
        return result;
    }

    public static List<RecruitInformation> selectListBySearch(String condition) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        String sql = "SELECT * FROM recruitInformation where requirement like '%"+condition+"%' or companyName like '%"+condition+"%' or " +
                "salary like '%"+condition+"%' or deadLine like '%"+condition+"%' or address like '%"+condition+"%' or  applyPosition like '%"+condition+"%'";
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        BeanListHandler<RecruitInformation> beanListHandler =new BeanListHandler<>(RecruitInformation.class);
        List<RecruitInformation> result = beanListHandler.handle(resultSet);
        C3P0Pool.close(resultSet,statement,connection);
        return result;
    }


    //改
    public static int update(RecruitInformation  recruitInformation) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            Integer id = recruitInformation.getId();
            String sql = "UPDATE recruitInformation SET requirement=?,companyId=?,companyName=?,salary=?,deadLine=?,address=?,applyPosition=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,recruitInformation.getRequirement());
            statement.setInt(2,recruitInformation.getCompanyId());
            statement.setString(3,recruitInformation.getCompanyName());
            statement.setString(4,recruitInformation.getSalary());
            statement.setString(5,recruitInformation.getDeadLine());
            statement.setString(6,recruitInformation.getAddress());
            statement.setString(7,recruitInformation.getApplyPosition());
            statement.setInt(8,id);
            System.out.println(sql);
            updateLine = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            C3P0Pool.close(null,statement,connection);
        }
        return updateLine;
    }


}

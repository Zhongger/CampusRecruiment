package zhongger.dao;

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
            String sql = "INSERT INTO recruitInformation (requirement,companyId,salary,deadLine,address,infoId) VALUES (?,?,?,?,?,?)" ;
            statement = connection.prepareStatement(sql);
            statement.setString(1,recruitInformation.getRequirement());
            statement.setString(2,recruitInformation.getCompanyId());
            statement.setString(3, recruitInformation.getSalary());
            statement.setDate(4, (Date) recruitInformation.getDeadLine());
            statement.setString(5, recruitInformation.getAddress());
            statement.setInt(6, recruitInformation.getInfoId());
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


    //改
    public static int update(RecruitInformation  recruitInformation) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        int updateLine = 0;
        try {
            connection.setAutoCommit(false);//开启事务
            Integer id = recruitInformation.getId();
            String lock="SELECT version FROM recruitInformation WHERE id=" + id + "+ FOR UPDATE";
            ResultSet resultSet = statement.executeQuery(lock);
            int version=0;//版本号，用于乐观锁
            while (true){
                if (updateLine==1){
                    break;
                }
                while (resultSet.next()){
                    version = resultSet.getInt("version");
                }
                String sql = "UPDATE recruitInformation SET requirement="+recruitInformation.getRequirement()+
                        ",companyId="+recruitInformation.getCompanyId()+
                        ",salary="+recruitInformation.getSalary()+
                        ",deadLine="+recruitInformation.getDeadLine()+
                        ",address="+recruitInformation.getAddress()+
                        " WHERE id="+id +"AND version="+version;
                updateLine = statement.executeUpdate(sql);//updateLine=0，重试
            }
            String updateVersion = "UPDATE recruitInformation SET version="+(version+1);
            statement.executeUpdate(updateVersion);//更新版本号

        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
        }finally {
            C3P0Pool.close(null,statement,connection);
        }
        return updateLine;
    }
}

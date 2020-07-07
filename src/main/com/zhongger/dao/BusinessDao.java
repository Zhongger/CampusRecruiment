package zhongger.dao;

import org.apache.commons.dbutils.handlers.BeanHandler;
import zhongger.config.C3P0Pool;
import zhongger.entity.Business;
import zhongger.entity.BusinessVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author Zhongger
 * @Description 公司数据库操作类
 * @Date 2020.5.17
 */
public class BusinessDao {
    //增
    public static int insertToVerify(BusinessVO businessVO) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        connection.setAutoCommit(false);
        String sql = "insert into verify_company (companyName,companyId,password,companyFile,is_verify) values (?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1,businessVO.getCompanyName());
        statement.setString(2,businessVO.getCompanyId());
        statement.setString(3,businessVO.getPassword());
        statement.setString(4,businessVO.getCompanyFile());
        statement.setInt(5,0);//为0的时候是指未通过验证
        int i = statement.executeUpdate();
        connection.commit();
        C3P0Pool.close(null,statement,connection);
        return i;
    }
    //修改验证
    public static int registerFlag(String companyId,String passOrNot) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        connection.setAutoCommit(false);
        int flag = 0;
        if (passOrNot.equals("yes")){
            flag = 1;
        }else {
            flag = -1;
        }
        String sql = "update verify_company set is_verify="+flag+" where companyId='"+companyId+"'";
        statement = connection.prepareStatement(sql);
        int i = statement.executeUpdate(sql);
        connection.commit();
        C3P0Pool.close(null,statement,connection);
        return i;
    }

    public static BusinessVO login(String companyId,String password) throws SQLException {
        Connection connection = C3P0Pool.getConnection();
        PreparedStatement statement = null;
        connection.setAutoCommit(false);
        String sql = "select * from verify_company where companyId='"+companyId+"' and password='"+password+"' and is_verify=1";
        statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        BeanHandler<BusinessVO> businessVOBeanHandler = new BeanHandler<>(BusinessVO.class);
        BusinessVO businessVO = businessVOBeanHandler.handle(resultSet);
        return businessVO;

    }
}

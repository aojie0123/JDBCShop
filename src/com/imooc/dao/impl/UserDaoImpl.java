package com.imooc.dao.impl;

import com.imooc.dao.UserDao;
import com.imooc.domain.User;
import com.imooc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(User user) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //  获得连接
            con = JDBCUtils.getConnection();
            //  编写SQL语句
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            //  预编译SQL
            pstm = con.prepareStatement(sql);
            //  设置参数
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getPassword());
            //  执行
            rs = pstm.executeQuery();
            if (rs.next()) {
                User existUser = new User();
                existUser.setUid(rs.getInt("uid"));
                existUser.setUsername(rs.getString("username"));
                existUser.setPassword(rs.getString("password"));
                return existUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  释放资源
            JDBCUtils.release(rs, pstm, con);
        }
        return null;
    }
}

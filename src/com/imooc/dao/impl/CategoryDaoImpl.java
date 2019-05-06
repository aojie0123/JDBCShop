package com.imooc.dao.impl;

import com.imooc.dao.CategoryDao;
import com.imooc.domain.Category;
import com.imooc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Category> list = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT * FROM category";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt("cid"), rs.getString("cname"), rs.getString("cdesc"));
                list.add(category);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
        return list;
    }
}

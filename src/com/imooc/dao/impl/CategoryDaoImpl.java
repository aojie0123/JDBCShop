package com.imooc.dao.impl;

import com.imooc.dao.CategoryDao;
import com.imooc.domain.Category;
import com.imooc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Category> list = new ArrayList<Category>();
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

    @Override
    public void save(Category category) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "INSERT INTO category (cname, cdesc) VALUES (?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, category.getCname());
            pstm.setString(2, category.getCdesc());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
    }

    @Override
    public Category findOne(Integer cid) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Category category = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT * FROM category WHERE cid = ?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, cid);
            rs = pstm.executeQuery();
            if (rs.next()) {
                category = new Category(rs.getInt("cid"), rs.getString("cname"), rs.getString("cdesc"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
        return category;
    }

    @Override
    public void update(Category category) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE category SET cname=?,cdesc=? WHERE cid=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1,category.getCname());
            pstm.setString(2,category.getCdesc());
            pstm.setInt(3,category.getCid());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
    }
}

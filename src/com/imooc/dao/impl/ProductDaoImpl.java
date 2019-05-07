package com.imooc.dao.impl;

import com.imooc.dao.ProductDao;
import com.imooc.domain.Category;
import com.imooc.domain.Product;
import com.imooc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    @Override
    public List<Product> finAll() {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Product> list = new ArrayList<Product>();
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT * FROM product p, category c WHERE p.cid=c.cid ORDER BY pid ASC";
            pstm = con.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setPid(rs.getInt("pid"));
                product.setPname(rs.getString("pname"));
                product.setAuthor(rs.getString("author"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setFilename(rs.getString("filename"));
                product.setPath(rs.getString("path"));
                product.getCategory().setCid(rs.getInt("cid"));
                product.getCategory().setCname(rs.getString("cname"));
                product.getCategory().setCdesc(rs.getString("cdesc"));
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
        return list;
    }

    @Override
    public void save(Product product) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "INSERT INTO product VALUES (null, ?, ?, ?, ?, ?, ?, ?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, product.getPname());
            pstm.setString(2, product.getAuthor());
            pstm.setDouble(3, product.getPrice());
            pstm.setString(4, product.getDescription());
            pstm.setString(5, product.getFilename());
            pstm.setString(6, product.getPath());
            pstm.setInt(7, product.getCategory().getCid());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
    }

    @Override
    public Product findOne(Integer pid) {
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "SELECT * FROM product p, category c WHERE p.cid = c.cid and p.pid = ?";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, pid);
            rs = pstm.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("pid"),rs.getString("pname"),rs.getString("author"),rs.getDouble("price"),rs.getString("description"),rs.getString("filename"),rs.getString("path"),new Category(rs.getInt("cid"), rs.getString("cname"), rs.getString("cdesc")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(rs, pstm, con);
        }
        return null;
    }

    @Override
    public void update(Product product) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "UPDATE product SET pname=?,author=?,price=?,description=?,filename=?,path=?,cid=? WHERE pid=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, product.getPname());
            pstm.setString(2, product.getAuthor());
            pstm.setDouble(3, product.getPrice());
            pstm.setString(4, product.getDescription());
            pstm.setString(5, product.getFilename());
            pstm.setString(6, product.getPath());
            pstm.setInt(7, product.getCategory().getCid());
            pstm.setInt(8, product.getPid());
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(pstm, con);
        }
    }
}

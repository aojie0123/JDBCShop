package com.imooc.service.impl;

import com.imooc.dao.CategoryDao;
import com.imooc.dao.ProductDao;
import com.imooc.dao.impl.CategoryDaoImpl;
import com.imooc.dao.impl.ProductDaoImpl;
import com.imooc.domain.Category;
import com.imooc.domain.Product;
import com.imooc.service.CategoryService;
import com.imooc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> findAll() {
        CategoryDao categoryDao = new CategoryDaoImpl();
        return categoryDao.findAll();
    }

    @Override
    public void save(Category category) {
        CategoryDao categoryDao = new CategoryDaoImpl();
        categoryDao.save(category);
    }

    @Override
    public Category findOne(Integer cid) {
        CategoryDao categoryDao = new CategoryDaoImpl();
        return categoryDao.findOne(cid);
    }

    @Override
    public void update(Category category) {
        CategoryDao categoryDao = new CategoryDaoImpl();
        categoryDao.update(category);
    }

    @Override
    public void delete(Integer cid) {
        /**
         * 事物管理：在业务层统一创建连接对象，保证多个DAO中使用同一连接：
         * 1、连接创建后，将连接对象传给DAO
         * 2、创建一个连接对象，将连接绑定到当前线程当中.(ThreadLocal)
         */
        Connection con = null;
        try {
            con = JDBCUtils.getConnection();
            //  开启事物
            con.setAutoCommit(false);
            //  删除分类前，将所属分类的商品处理
            ProductDao productDao = new ProductDaoImpl();
            List<Product> list = productDao.finByCid(cid);
            for (Product product : list) {
                product.getCategory().setCid(null);
                productDao.update(con, product);
            }

            //  删除分类
            CategoryDao categoryDao = new CategoryDaoImpl();
            categoryDao.delete(con, cid);
            con.commit();
        } catch (Exception e) {
            //  回滚事物
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.imooc.dao;

import com.imooc.domain.Product;

import java.sql.Connection;
import java.util.List;

public interface ProductDao {
    List<Product> finAll();

    void save(Product product);

    Product findOne(Integer pid);

    void update(Product product);

    void delete(Integer pid);

    List<Product> finByCid(Integer cid);

    void update(Connection con, Product product);
}

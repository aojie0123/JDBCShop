package com.imooc.domain;

public class Product {

    private Integer pid;
    private String pname;
    private String author;
    private double price;
    private String description;
    private String filename;
    private String path;
    private Integer cid;

    public Product() {
    }

    public Product(Integer pid, String pname, String author, double price, String description, String filename, String path, Integer cid) {
        this.pid = pid;
        this.pname = pname;
        this.author = author;
        this.price = price;
        this.description = description;
        this.filename = filename;
        this.path = path;
        this.cid = cid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", filename='" + filename + '\'' +
                ", path='" + path + '\'' +
                ", cid=" + cid +
                '}';
    }
}

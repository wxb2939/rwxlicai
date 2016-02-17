package com.xem.mzbphoneapp.entity;

import java.io.Serializable;

/**
 * 门店服务查询
 *
 * Created by xuebing on 15/8/3.
 */
public class StoreService implements Serializable {

    private int id;
    private String logo;
    private String storeName;
    private String serviceDoing;
    private String servideTodo;
    private String servideDone;

    public StoreService() {
    }

    public StoreService(int id, String logo, String storeName, String serviceDoing, String servideTodo, String servideDone) {
        this.id = id;
        this.logo = logo;
        this.storeName = storeName;
        this.serviceDoing = serviceDoing;
        this.servideTodo = servideTodo;
        this.servideDone = servideDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getServiceDoing() {
        return serviceDoing;
    }

    public void setServiceDoing(String serviceDoing) {
        this.serviceDoing = serviceDoing;
    }

    public String getServideTodo() {
        return servideTodo;
    }

    public void setServideTodo(String servideTodo) {
        this.servideTodo = servideTodo;
    }

    public String getServideDone() {
        return servideDone;
    }

    public void setServideDone(String servideDone) {
        this.servideDone = servideDone;
    }
}

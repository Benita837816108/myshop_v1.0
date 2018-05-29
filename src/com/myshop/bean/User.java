package com.myshop.bean;

import java.io.Serializable;

public class User implements Serializable {
    private  String uid;
    private  String username;
    private  String password;
    private  String name;
    private  String email;
    private  String telephone;
    private  String hobby;
    private  String birthday;
    private  String sex;
    private  Integer state;
    private  String code;

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getHobby() {
        return hobby;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public Integer getState() {
        return state;
    }

    public String getCode() {
        return code;
    }

    public void setUid(String uid) {

        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

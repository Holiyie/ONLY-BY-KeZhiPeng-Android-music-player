package com.example.musicplayer;

public class User {
    //_id ’À∫≈£¨pwd √‹¬Î£¨name Í«≥∆£¨ sex –‘±
    private String _id;
    private String pwd = null;
    private String name = null;
    private String sex = null;
    

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

   

    public String getPassword() {
        return pwd;
    }

    public void setPassword(String password) {
        this.pwd = password;
    }

    public String getUser_name() {
        return name;
    }

    public void setUser_name(String user_name) {
        this.name = user_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}

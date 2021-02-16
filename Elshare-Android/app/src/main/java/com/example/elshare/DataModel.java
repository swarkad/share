package com.example.elshare;

public class  DataModel
{
    String name =null;
    int id_ = 0;
    int user_id=0;

    public DataModel(String name, int id_,int user_id) {
        this.name = name;
        this.id_ = id_;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id_;
    }
    public int getUser_id() {
        return user_id;
    }
}

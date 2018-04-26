package com.ssynhtn.helloworld.model;

/**
 * Created by huangtongnao on 2018/3/29.
 */

public class User {
    public int id;
    public int age;
    public String name;


    public User() {
    }

    public User(int id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id " + id + " age " + age + " name " + name;
    }
}

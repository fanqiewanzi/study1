package com.example.learn.demo.exception;

public class UserNotExist extends Exception{
    public UserNotExist()
    {
        super("用户不存在");
    }
}

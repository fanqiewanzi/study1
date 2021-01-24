package com.example.learn.demo.exception;

public class TokenUnavailable extends Exception{
    public TokenUnavailable()
    {
        super("token失效");
    }

}

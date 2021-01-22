package com.example.learn.demo.service;

import com.example.learn.demo.modle.Account;

import java.util.Map;


public interface AccountService {
    Map<String, Object> Rigister(Account account);
    Map<String,Object> Login(String id,String password);
}

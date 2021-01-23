package com.example.learn.demo.service;

import com.example.learn.demo.modle.Account;
import com.example.learn.demo.modle.Response;

import java.util.Map;


public interface AccountService {
    Response Rigister(Account account);
    Response Login(String id,String password);
}

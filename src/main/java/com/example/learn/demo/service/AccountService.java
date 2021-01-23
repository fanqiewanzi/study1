package com.example.learn.demo.service;

import com.example.learn.demo.modle.Account;
import com.example.learn.demo.modle.Response;


public interface AccountService {
    Response rigister(Account account);
    Response login(String id,String password);
}

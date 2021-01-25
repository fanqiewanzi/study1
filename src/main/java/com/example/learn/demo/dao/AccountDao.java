package com.example.learn.demo.dao;

import com.example.learn.demo.modle.Account;


public interface AccountDao {
    Account getAccountById(String id);

    Account getAccountByInput(String id);

    Account getAccountByPhoneNumber(String phoneNumber);

    void insert(Account account);
}

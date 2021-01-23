package com.example.learn.demo.controller;

import com.example.learn.demo.modle.Account;
import com.example.learn.demo.modle.Response;
import com.example.learn.demo.service.AccountServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;



@Slf4j
@RestController
@RequestMapping("/index")
public class AccountController {
    @Resource
    private AccountServiceImp accountServiceImp;

    @GetMapping("/login")
    Response login(@RequestParam String id, @RequestParam String password)
    {
        return accountServiceImp.login(id,password);
    }
    @PostMapping("/register")
    Response rigister(@RequestBody Account account)
    {
        return accountServiceImp.rigister(account);
    }
}

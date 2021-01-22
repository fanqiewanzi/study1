package com.example.learn.demo.controller;

import com.example.learn.demo.modle.Account;
import com.example.learn.demo.service.AccountServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/index")
public class AccountController {
    @Resource
    private AccountServiceImp accountServiceImp;

    @GetMapping("/login")
    Map<String, Object> Login(@RequestParam String id,@RequestParam String password)
    {
        return accountServiceImp.Login(id,password);
    }
    @PostMapping("/register")
    Map<String, Object> Rigister(@RequestBody Account account)
    {
        return accountServiceImp.Rigister(account);
    }
}

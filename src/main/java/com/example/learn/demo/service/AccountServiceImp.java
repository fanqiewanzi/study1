package com.example.learn.demo.service;

import com.example.learn.demo.dao.AccountDao;
import com.example.learn.demo.modle.Account;
import com.example.learn.demo.modle.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("AccountService")
public class AccountServiceImp implements AccountService{
    @Resource
    AccountDao accountDao;
    @Override
    public Response rigister(Account account) {
        Account account1=accountDao.getAccountById(account.getId());
        /*判断id是否被人注册*/
        if(account1!=null)
        {
            return new Response("400","这个id被被人注册过了");
        }
        account1=accountDao.getAccountByPhoneNumber(DigestUtils.md5Hex(account.getPhoneNumber()));
        /*判断手机号是否被人注册*/
        if(account1!=null)
        {
            return new Response("400","手机号已注册");
        }
        /*对密码进行加密进行注册*/
        account.setPassword(DigestUtils.md5Hex(account.getPassword()));
        account.setPhoneNumber(DigestUtils.md5Hex(account.getPhoneNumber()));
        accountDao.insert(account);
        return new Response("200","注册成功");
    }

    @Override
    public Response login(String id,String password) {
        Map<String, Object> map=new HashMap<>();
        try{
        Account account=accountDao.getAccountByPhoneNumber(id);
        if(account==null)
        {
            account=accountDao.getAccountById(id);
        }
        /*对输入密码进行加密与数据库中存储密码进行比较*/
        if(account.getPassword().equals(DigestUtils.md5Hex(password)))
        {
            return new Response("200","登陆成功");
        }
        else
        {
            return new Response("403","账号或密码错误");
        }
        }catch (Exception e)
        {
            return new Response("400","用户不存在");
        }
    }
}

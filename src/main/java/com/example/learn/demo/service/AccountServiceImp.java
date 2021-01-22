package com.example.learn.demo.service;

import com.example.learn.demo.dao.AccountDao;
import com.example.learn.demo.modle.Account;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("AccountServiceImp")
public class AccountServiceImp implements AccountService{
    @Resource
    AccountDao accountDao;
    @Override
    public Map<String, Object> Rigister(Account account) {
        Map<String, Object> map=new HashMap<>();
        Account account1=accountDao.getAccountById(account.getId());
        /*判断id是否被人注册*/
        if(account1!=null)
        {
            map.put("code","400");
            map.put("message","这个id被被人注册过了");
            return map;
        }
        account1=accountDao.getAccountByPhoneNumber(DigestUtils.md5Hex(account.getPhoneNumber()));
        /*判断手机号是否被人注册*/
        if(account1!=null)
        {
            map.put("code","400");
            map.put("message","手机号已注册");
            return map;
        }
        /*对密码进行加密进行注册*/
        account.setPassword(DigestUtils.md5Hex(account.getPassword()));
        account.setPhoneNumber(DigestUtils.md5Hex(account.getPhoneNumber()));
        accountDao.insert(account);
        map.put("code","200");
        map.put("message","注册成功");
        map.put("id",account.getId());
        return map;
    }

    @Override
    public Map<String, Object> Login(String id,String password) {
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
            map.put("code","200");
            map.put("message","登录成功");
            map.put("id",account.getId());
            return map;
        }
        else
        {
            map.put("code","400");
            map.put("message","账号或密码错误");
            return map;
        }
        }catch (Exception e)
        {
            map.put("code","400");
            map.put("message","用户不存在");
            return map;
        }
    }
}

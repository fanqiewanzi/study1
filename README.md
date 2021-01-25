# study1
练习1
API文档：
https://www.showdoc.com.cn/1235298286270760?page_id=6217595980194172


问题：
尝试用token和shiro来做登录验证
在网上看他们做的千奇百怪的看不怎么懂
主要是卡在各种各样的工具类、拦截器、jwt还有shiro上
但token的概念我应该差不多懂了
token主要是按一个算法组成的字符串，每当用户进行一个请求的时候都需要过一拦截器进行token认证,token有时限但能一直刷新
学得比较慢:(


# 基于jwt的token验证

##### 大致思路：

基于token的登录，是不存在回话状态，大概思路，在用户初次登录的时候，校验用户账号密码，成功后给其生成一个token，token=用户ID+时间戳+过期时间+一个自己平台规定的签名，使用jjwt生成一个令牌，然后对其进行存库，用户每次访问接口，都会在头部Headers中带上token，后来拦截器对其进行拦截，如果token为空或错误则让其登录，如果有token，获取token进行其解析，取出里面的用户ID，根据用户ID查询数据库中所存token，判断其是否正确，正确使其登录，错误则提示登录。

1.用户登录产生token

2.用户请求时带token进行请求

3.对token进行验证

4.对权限进行验证

5.返回用户资源

这里的token不需要存储

只需要请求时带上token服务器中会以签名来做一个验证的初始化

将token传进verifier对象进行验证并返回结果

所以说token在这种情况下不需要存储在服务端


1、注册接口
简要描述
注册账号
请求URL
http://localhost:8989/index/register
请求方式
post
请求参数示例
{
    "id": "654321",
        "phoneNumber": "15002342721",
            "password": "123456789"
}
json字段说明
字段名	必选	类型	说明
id	是	string	用户ID
phoneNumber	是	string	电话号码
password	是	string	密码
返回示例
{
  "code": "200",
  "id": "654321",
  "message": "注册成功"
}
返回参数说明
参数名	类型	说明
id	string	用户ID
备注
2、登录接口
简要描述
根据用户ID或手机号登录
请求URL
http://localhost:8989/index/login
请求方式
get
请求参数
参数名	必选	类型	说明
id	是	string	电话号或者用户ID号
password	是	string	密码
返回示例
{
  "code": "200",
  "message": "登陆成功",
  "data": {
    "id": "123456789",
    "phoneNumber": "c02ef143e7667c4f23e78804bae2f297",
    "password": "c33367701511b4f6020ec61ded352059",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMjM0NTY3ODkiLCJwaG9uZU51bWJlciI6ImMwMmVmMTQzZTc2NjdjNGYyM2U3ODgwNGJhZTJmMjk3IiwiZXhwIjoxNjExNTYyMTAwLCJpYXQiOjE2MTE1NjAzMDB9.cbC0lx6AHF_Ts8BnZRIVod0fmvJxZcMpLI7o65FHWWE"
  }
}
返回参数说明
参数名	类型	说明
id	string	用户账号
备注
3、主页
简要描述
无
请求URL
http://localhost:8989/index/profile
请求方式
get
Header
header	必选	类型	说明
token	是	string	无
返回示例
hello word
备注
4、状态码
状态码	描述
200	操作成功
400	参数校验错误
403	用户未通过身份验证，拒绝访问
500	系统内部错误，请联系管理员




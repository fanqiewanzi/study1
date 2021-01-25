package com.example.learn.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.learn.demo.exception.TokenUnavailable;
import com.example.learn.demo.modle.Account;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

        /**
         签发对象：这个用户的id
         签发时间：现在
         有效时间：30秒
         载荷内容：暂时设计为：这个人的名字，这个人的昵称
         加密密钥：这个人的id加上一串字符串
         */
        public static String createToken(Account account) {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            Calendar nowTime = Calendar.getInstance();
            nowTime.add(Calendar.SECOND,30);
            Date expiresDate = nowTime.getTime();

            /**
             * Token三部分：1.Header    2.Playload  3.Signature
             * withHeader()是Token的Header
             * withAudience()是Token中Playload中的签发对象
             * withIssuedAt()是Token中Playload中的发行时间
             * withExpiresAt()是Token中Playload中的有效时间
             * withClaim()是Token中Playload中的载荷（可以有多个）
             * sign()是Token中的signature，存储在服务端，用于创建和解密Token*/
            return JWT.create().withHeader(map)
                    .withAudience(account.getId())
                    .withIssuedAt(new Date())
                    .withExpiresAt(expiresDate)
                    .withClaim("phoneNumber", account.getPhoneNumber())
                    .sign(Algorithm.HMAC256(account.getId()+"HelloLehr"));
        }

        /**
         * 检验合法性，其中secret参数就应该传入的是用户的id
         * @param token
         * @throws TokenUnavailable
         */
        public static void verifyToken(String token, String secret) throws TokenUnavailable {
            DecodedJWT jwt = null;
            try {
                JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret+"HelloLehr")).build();
                jwt = verifier.verify(token);
            } catch (Exception e) {
                //效验失败
                //这里抛出的异常是我自定义的一个异常，你也可以写成别的
                throw new TokenUnavailable();
            }
        }

        /**
         * 获取签发对象
         */
        public static String getAudience(String token) throws TokenUnavailable {
            String audience = null;
            try {
                audience = JWT.decode(token).getAudience().get(0);
            } catch (JWTDecodeException j) {
                //这里是token解析失败
                throw new TokenUnavailable();
            }
            return audience;
        }


        /**
         * 通过载荷名字获取载荷的值
         */
        public static Claim getClaimByName(String token, String name){
            return JWT.decode(token).getClaim(name);
        }
}




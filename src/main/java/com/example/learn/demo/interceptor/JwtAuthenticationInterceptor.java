package com.example.learn.demo.interceptor;

import com.example.learn.demo.dao.AccountDao;
import com.example.learn.demo.exception.NeedToLogin;
import com.example.learn.demo.exception.UserNotExist;
import com.example.learn.demo.modle.Account;
import com.example.learn.demo.modle.PassToken;
import com.example.learn.demo.utils.JwtUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

    public class JwtAuthenticationInterceptor implements HandlerInterceptor {

        @Resource
        AccountDao accountDao;

        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
            // 从请求头中取出 token  这里需要和前端约定好把jwt放到请求头一个叫token的地方
            String token = httpServletRequest.getHeader("token");
            // 如果不是映射到方法直接通过
            if (!(object instanceof HandlerMethod)) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) object;
            Method method = handlerMethod.getMethod();

            //检查是否有passtoken注释，有则跳过认证
            if (method.isAnnotationPresent(PassToken.class)) {
                PassToken passToken = method.getAnnotation(PassToken.class);
                if (passToken.required()) {
                    return true;
                }
            }
            //默认全部检查
            else {
                System.out.println("被jwt拦截需要验证");
                // 执行认证
                if (token == null) {
                    //登陆失效，无token
                    throw new NeedToLogin();
                }

                // 获取 token 中的 user Name
                String userId = JwtUtils.getAudience(token);

                //找找看是否有这个user
                Account account=accountDao.getAccountByInput(userId);

                if (account == null) {
                        throw new UserNotExist();
                }

                // 验证 token
                JwtUtils.verifyToken(token, userId);

//                //获取载荷内容
//                String phoneNumber = JwtUtils.getClaimByName(token, "phoneNumber").asString();
//
//                //放入attribute以便后面调用
//                httpServletRequest.setAttribute("phoneNumber", phoneNumber);
                return true;
            }
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               Object o, ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    Object o, Exception e) throws Exception {
        }
    }


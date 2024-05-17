package com.jarkial.login.configuration.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestStatisticsInterceptor implements AsyncHandlerInterceptor{

    private ThreadLocal<Long> time = new ThreadLocal<>();

    @Autowired
    HibernateStatisticsInterceptor hibernateStatisticsInterceptor;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        time.set(System.currentTimeMillis());
        hibernateStatisticsInterceptor.startCounter();
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
        Long queryCount = hibernateStatisticsInterceptor.getQueryCount();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception{
        long duration =0;
        try{
            if(time.get()!=null)
            duration = System.currentTimeMillis() - time.get();
        }catch(Exception exception2){
            exception2.printStackTrace();
        }
        Long queryCount = hibernateStatisticsInterceptor.getQueryCount();
        hibernateStatisticsInterceptor.clearCounter();
        time.remove();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        hibernateStatisticsInterceptor.clearCounter();
        time.remove();
    }
}

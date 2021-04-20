package com.example.buaadataplatform.module.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PlatformInterpretor())
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/login","/user/login", "/user/register", "/test/queryAnswerVideoInfoByTestId", "/article/**", "/static/**");
    }

    class PlatformInterpretor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                 Object arg2) throws Exception {
            try{
                HttpSession session = request.getSession();
                if(session.getAttribute("authority")==null||session.getAttribute("user_id")==null){
                    System.out.println("页面过期");
                    response.setContentType("application/json;charset=utf-8");//指定返回的格式为JSON格式
                    response.setCharacterEncoding("UTF-8");//setContentType与setCharacterEncoding的顺序不能调换，否则还是无法解决中文乱码的问题
                    response.setStatus(250);
                    String jsonStr ="{\"status\":\"overtime\"}";
                    PrintWriter out =response.getWriter() ;
                    out.write(jsonStr);
                    out.close();
                    return false;
//                    return true;
                }
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

    }

}

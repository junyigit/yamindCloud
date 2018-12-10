package com.yamind.cloud.common.support.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取浏览器Cookie
        Cookie [] cookies =request.getCookies();
        String lang=null;
        if(cookies!=null&&cookies.length>0){
            for (Cookie cookie:cookies) {
                //获取lang语言设置
                if(cookie.getName().equals("lang")){
                    //保证安全 做出格式检测
                    if(cookie.getValue().indexOf("_")>0){
                        lang=cookie.getValue();
                    }
                    break;
                }
            }
        }
        //设置为默认国际化语言
        // Locale locale=Locale.getDefault();
        Locale locale=new Locale("zh","CN");

        //如果Cookie中存在其他语言配置
        if(!StringUtils.isBlank(lang)){
            //转换为cookie配置
            locale=new Locale(lang.split("_")[0],lang.split("_")[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        //默认不实现  后期扩展有用!
        //默认不实现  后期扩展有用!
        //默认不实现  后期扩展有用!
    }
}
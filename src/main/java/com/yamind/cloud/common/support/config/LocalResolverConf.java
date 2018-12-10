package com.yamind.cloud.common.support.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocalResolverConf {
    /**
     * 配置本地语言解析为Session会话解析方式
     * 此处可以设置默认国际化方案，不设置的话为系统自动检测浏览器版本设定国际化语言解析方案
     * @return
     */
    @Bean(name="localeResolver")
    public LocaleResolver localeResolverBean() {
        return new MyLocaleResolver();
    }
}

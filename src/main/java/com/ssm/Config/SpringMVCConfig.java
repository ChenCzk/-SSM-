package com.ssm.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.ssm.*")  //扫描包
//@EnableWebMvc  //启动SpringWebMVC
public class SpringMVCConfig  {



    /*
     * 视图解析器
     * */
    @Bean(name = "viewResolver")
    public ViewResolver initViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/JSP/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


}

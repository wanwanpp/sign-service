package com.site.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Created by wang0 on 2016/9/29.
 */

@Configuration
public class ErrorPageConfig {

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return new MyCustomizer();
    }

    //配置内嵌的servlet容器
    private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/403"));
//            设置contextpath
            //            container.setContextPath("/signService");
            //            container.setSsl(new Ssl());
        }
    }
}

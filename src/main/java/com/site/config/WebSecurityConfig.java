package com.site.config;

import com.site.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    UserDetailsService customUserService() { //2
        return new MemberService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and().authorizeRequests()
                .antMatchers("/*.html").permitAll()
//                .antMatchers("/excelout/**").hasAnyRole("SUPER_ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/").defaultSuccessUrl("/signDetail", true)
                .failureUrl("/").permitAll()
//                已超时的seesion重定向页面
                .and().sessionManagement().invalidSessionUrl("/")
//        退出登录时删除session对应的cookie
                .and().logout().deleteCookies("JSESSIONID")
                .and().rememberMe().key("remember-me").tokenValiditySeconds(1209600).rememberMeParameter("remember-me")
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().disable(); //6
    }
}

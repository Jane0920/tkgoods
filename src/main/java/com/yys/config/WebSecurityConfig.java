package com.yys.config;

import com.yys.service.impl.LoginServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * Created by xyr on 2017/10/16.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 忽略静态文件
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/css/**", "/js/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/login/**", "/index")
                .permitAll()
                // 其他地址的访问均需验证权限（需要登录）
                .anyRequest().authenticated().and()
                // 添加验证码验证
                //.addFilterAt(myUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).exceptionHandling()
                //.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login_page")).and()
                // 指定登录页面的请求路径
                .formLogin().loginPage("/login_page")
                // 登陆处理路径
                .loginProcessingUrl("/login").permitAll().and()
                // 退出请求的默认路径为logout，下面改为signout，
                // 成功退出登录后的url可以用logoutSuccessUrl设置
                .logout().logoutUrl("/signout").logoutSuccessUrl("/login_page").permitAll().and()
                // 关闭csrf
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl()).passwordEncoder(new Md5PasswordEncoder());

    }

    @Bean
    public LoginServiceImpl userDetailsServiceImpl() {
        return new LoginServiceImpl();
    }

    /*@Bean
    public MyUsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter myFilter = new MyUsernamePasswordAuthenticationFilter();
        myFilter.setAuthenticationManager(authenticationManagerBean());
        myFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        myFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        myFilter.setRememberMeServices(tokenBasedRememberMeServices());
        return myFilter;
    }
*/
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/login/success");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/login/failure");
    }

}

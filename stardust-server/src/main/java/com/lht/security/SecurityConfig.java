package com.lht.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author lht
 * @date 2021/4/4 - 19:15
 * @description: 安全框架配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsServiceImpl userDetailsService;
    @Resource
    private VerifyCaptchaFilter verifyCaptchaFilter;
    @Resource
    private AuthenticationEntryPointHandler authenticationEntryPointHandler;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private MyAccessDeniedHandler accessDeniedHandler;
    @Resource
    private MyLogoutSuccessHandler logoutSuccessHandler;
    @Resource
    private MyAuthenticationFailureHandler authenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET,
                "/swagger-resources/**",
                "/PearAdmin/**",
                "/component/**",
                "/admin/**",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/doc.html",
                "/webjars/**",
                "/v2/**",
                "/druid/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(verifyCaptchaFilter, UsernamePasswordAuthenticationFilter.class);
        //关闭csrf
        http.csrf().disable()
                //未登陆时返回 JSON 格式的数据给前端
                .httpBasic().authenticationEntryPoint(authenticationEntryPointHandler)
                .and()
                .authorizeRequests()
                //任何人都能访问 验证码接口以及前端页面的所有接口
                .antMatchers("/captcha","/front/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //登录页面 不设限访问
                .loginPage("/login.html")
                //拦截的请求
                .loginProcessingUrl("/login")
                // 登录成功
                .successHandler(loginSuccessHandler)
                // 登录失败
                .failureHandler(authenticationFailureHandler)
                .permitAll()
                .and()
                .rememberMe().rememberMeParameter("rememberme")
                // 防止iframe 造成跨域
                .and()
                .headers()
                .frameOptions()
                .disable();

        http.logout().permitAll()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler);
//                .logoutSuccessUrl("/login.html");

        // 禁用缓存
        http.headers().cacheControl();

        // 无权访问 JSON 格式的数据
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }

    @Bean
    public BCryptPasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

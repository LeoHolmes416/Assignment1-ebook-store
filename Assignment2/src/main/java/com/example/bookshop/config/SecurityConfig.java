package com.example.bookshop.config;

import com.example.bookshop.entity.User;
import com.example.bookshop.filter.JwtAuthencationTokenFilter;
import com.example.bookshop.filter.RestAuthenticationEntryPoint;
import com.example.bookshop.filter.RestfulAccessDeniedHandler;
import com.example.bookshop.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Autowired
    UserMapper userMapper;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }



    /**
     * SpringSecurity定义的核心接口
     * ，用于根据用户名获取用户信息，需要自行实现
     * @return org.springframework.security.core.userdetails.UserDetailsService
     * @author huangzifan
     * @since 2020-10-28 11:00
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            System.err.println("username------->"+username);
            User user = userMapper.findByUsername(username);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            if(null!=user){
                return user;
            }
            return null;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/*.html" +
                        "/login",
                "/book/index",
                "/type/*",
                "/logout",
                "/css/**",
                "/**/*.html",
                "/**/*.jpg",
                "/**/*.png",
                "/js/**",
                "/index.html",
                "/favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha/**"
        );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,// 允许对于网站静态资源的无授权访问
                        "/",
                        "/book/index",
                        "/type/*",
                        "/*.html",
                        "detail.html",
                        "/**/*.html",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**")
                .permitAll()
                .antMatchers("/login", "/logout")//对登陆注册要允许匿名访问
                .permitAll()
                //除了上面 所有请求都要拦截
                .anyRequest()//除了上面外的所有请求全部需要鉴权认证
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();




        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        // 添加自定义为授权和未登录时的处理器
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

    }


    /**
     * 在用户名和密码校验前添加的过滤器
     * ，如果有jwt的token，会自行根据token信息进行登录。
     * @return JwtAuthenticationTokenFilter
     * @author huangzifan
     * @since 2020-10-28 11:01
     */
    @Bean
    public JwtAuthencationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}

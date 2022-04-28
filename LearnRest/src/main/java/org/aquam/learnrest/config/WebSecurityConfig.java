package org.aquam.learnrest.config;

import lombok.RequiredArgsConstructor;
import org.aquam.learnrest.config.jwt.FilterChainExceptionHandler;
import org.aquam.learnrest.config.jwt.JwtConfigurer;
import org.aquam.learnrest.config.jwt.JwtTokenProvider;
import org.aquam.learnrest.exception.handler.AppExceptionHandler;
import org.aquam.learnrest.repository.UserRepository;
import org.aquam.learnrest.service.impl.UserServiceImpl;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.util.Arrays;
import java.util.List;

//@ComponentScan({"com.delivery.request"})
//@EntityScan("com.delivery.domain")
//@EnableJpaRepositories("com.delivery.repository")
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("org.aquam")
// @EnableJpaRepositories
//@EntityScan("org.aquam.model")
@EnableJpaRepositories("org.aquam.learnrest.repository")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationSuccessHandler SuccessLoginHandler;
    private final JwtTokenProvider jwtTokenProvider;

    // private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    // private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    // private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    // private final AppExceptionHandler appExceptionHandler;
    private final FilterChainExceptionHandler filterChainExceptionHandler;

    // hasAuthority("ROLE_ADMIN") = hasRole("ADMIN")
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> {
            var cors = new CorsConfiguration();
            cors.setAllowedOrigins(List.of("http://localhost:3000"));
            cors.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(List.of("*"));
            // cors.setAllowCredentials(true);
            return cors;
        });
        http.csrf().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/learn/register").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/learn/login").permitAll();
        // http.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler);
        http.apply(new JwtConfigurer(jwtTokenProvider, filterChainExceptionHandler));
        // http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint);
        // http.exceptionHandling()
        //    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));

    }



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    /*
    @Bean
    public FilterRegistrationBean hiddenHttpMethodFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
     */



}

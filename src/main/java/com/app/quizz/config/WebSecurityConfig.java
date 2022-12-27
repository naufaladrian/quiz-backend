package com.app.quizz.config;
import com.app.quizz.jwt.AccessDeniedEntryPoint;
import com.app.quizz.jwt.JwtAuthEntryPoint;
import com.app.quizz.jwt.JwtAuthTokenFilter;
import com.app.quizz.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailservice;

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    private AccessDeniedEntryPoint accessDeniedEntryPoint;
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // API controller
            "/",
            "/v1/api/traffic-global-user/connect",
            "/api/image",
            "/v1/api/user/admin/login"
    };
    @Bean
    public JwtAuthTokenFilter authTokenFilter(){return new JwtAuthTokenFilter();}

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailservice)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    protected void configure(HttpSecurity http)throws Exception{
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and()
                .exceptionHandling().accessDeniedHandler(accessDeniedEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}

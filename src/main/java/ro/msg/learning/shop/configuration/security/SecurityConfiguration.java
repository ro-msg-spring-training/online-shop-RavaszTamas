package ro.msg.learning.shop.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Value("${security.type}")
    String securityType;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        if(securityType.equals("with-basic"))
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/css/**","/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
                    .and()
                    .formLogin()
                    .loginPage("/login").failureUrl("/login-error");
        else if(securityType.equals("with-form"))
            httpSecurity
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/products/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);
        else
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/css/**","/index").permitAll()
                    .antMatchers("/user/**").hasRole("USER")
                    .and()
                    .formLogin()
                    .loginPage("/login").failureUrl("/login-error");


        httpSecurity.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

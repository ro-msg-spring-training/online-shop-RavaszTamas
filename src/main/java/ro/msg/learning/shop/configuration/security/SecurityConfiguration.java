package ro.msg.learning.shop.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (securityType.equals("with-form"))
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/stocks").permitAll()
                    .antMatchers(HttpMethod.POST, "/orders").permitAll()
                    .antMatchers(HttpMethod.GET, "/orders").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
//                    .antMatchers("/css/**","/index").permitAll()
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").failureUrl("/login-error");
        else if (securityType.equals("with-basic"))
            httpSecurity.csrf().disable()
                    .authorizeRequests()
//                .antMatchers("/").permitAll()
                    .antMatchers("/products").permitAll()
                    .antMatchers(HttpMethod.POST, "/orders").permitAll()
                    .antMatchers(HttpMethod.GET, "/orders").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint);
        else
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/stocks").permitAll()
                    .antMatchers(HttpMethod.POST, "/orders").permitAll()
                    .antMatchers(HttpMethod.GET, "/orders").permitAll()
                    .antMatchers("/h2-console/**").permitAll()
//                    .antMatchers("/css/**","/index").permitAll()
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login").failureUrl("/login-error");

        httpSecurity.headers().frameOptions().disable();
        httpSecurity.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
    }

    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}

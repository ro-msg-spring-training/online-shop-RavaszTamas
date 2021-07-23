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

    public static final String ORDERS = "/orders";
    public static final String H_2_CONSOLE = "/h2-console/**";
    public static final String STOCKS = "/stocks";
    public static final String LOGIN = "/login";
    public static final String LOGIN_ERROR = "/login-error";
    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Value("${security.type}")
    String securityType;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (securityType.equals("with-form"))
            httpSecurity
                    .authorizeRequests()
                    .antMatchers(STOCKS).permitAll()
                    .antMatchers(HttpMethod.POST, ORDERS).permitAll()
                    .antMatchers(HttpMethod.GET, ORDERS).permitAll()
                    .antMatchers(H_2_CONSOLE).permitAll()
//                    .antMatchers("/css/**","/index").permitAll()
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage(LOGIN).failureUrl(LOGIN_ERROR);
        else if (securityType.equals("with-basic"))
            httpSecurity.csrf().disable()
                    .authorizeRequests()
//                .antMatchers("/").permitAll()
                    .antMatchers("/products").permitAll()
                    .antMatchers(HttpMethod.POST, ORDERS).permitAll()
                    .antMatchers(HttpMethod.GET, ORDERS).permitAll()
                    .antMatchers(H_2_CONSOLE).permitAll()
//                .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint);
        else
            httpSecurity
                    .authorizeRequests()
                    .antMatchers(STOCKS).permitAll()
                    .antMatchers(HttpMethod.POST, ORDERS).permitAll()
                    .antMatchers(HttpMethod.GET, ORDERS).permitAll()
                    .antMatchers(H_2_CONSOLE).permitAll()
//                    .antMatchers("/css/**","/index").permitAll()
//                    .antMatchers("/user/**").hasRole("USER")
//                    .antMatchers("/products/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage(LOGIN).failureUrl(LOGIN_ERROR);

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

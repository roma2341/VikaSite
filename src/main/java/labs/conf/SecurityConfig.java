package labs.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
     // дозволити анонімним користувачам заходити на ці каталоги
            .antMatchers("/", "/register","/index","/signup","/login",
            		"/css/*","/images/*","/js/*","/css/fonts/*","/vika/css/*")
            .permitAll() 
            .anyRequest().authenticated() // всі інші запити потребують аутентифікації
            .and()
        .formLogin()
            .loginPage("/")  // URL логіну
            .usernameParameter("login_logining") // назва параметру з логіном на формі
            .passwordParameter("password_logining")
            .permitAll() // дозволити всім заходити на форму логіну
            .and()
        .logout()
            .permitAll(); 
        
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(new BCryptPasswordEncoder()); 
    }

}

package mk.ukim.finki.finkit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
  	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  	
    auth.jdbcAuthentication()
      .dataSource(dataSource)
      .passwordEncoder(passwordEncoder)
      .usersByUsernameQuery("select login as principal, password as credentials, true from users where login = ?")
      .authoritiesByUsernameQuery("select login as principal, role as role from users where login = ?");
    
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.authorizeRequests().antMatchers("/h2-console/**").authenticated();
    http.authorizeRequests().antMatchers("/t/*/newpost/**").authenticated();
    http.authorizeRequests().antMatchers("/newtopic/**").authenticated();
    
    http.authorizeRequests().anyRequest().permitAll();
    http.csrf().disable();
    http.headers().frameOptions().sameOrigin();
    
    http.formLogin().loginPage("/").permitAll();
    http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
    
    http.rememberMe()
      .rememberMeParameter("rememberMe")
      .tokenValiditySeconds(60 * 60 * 24);
    
    // http.exceptionHandling().accessDeniedPage("/error");
  }
  
}
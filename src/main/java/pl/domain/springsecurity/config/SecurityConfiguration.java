package pl.domain.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import pl.domain.springsecurity.model.InMemoryUserList;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private InMemoryUserList inMemoryUserList;

    @Autowired
    public SecurityConfiguration(InMemoryUserList inMemoryUserList) {
        this.inMemoryUserList = inMemoryUserList;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        inMemoryUserList.getUserAppList()
                .forEach(user -> {
                    try {
                        auth.inMemoryAuthentication()
                                .withUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin")
                .hasRole("ADMIN")
                .antMatchers("/user")
                .authenticated()
                .antMatchers("/byebye")
                .authenticated()
                .and()
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/byebye")
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}

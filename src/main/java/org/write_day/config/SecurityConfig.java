package org.write_day.config;

import org.write_day.services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/**").permitAll()
                .antMatchers("/api", "/api**").authenticated()
                .antMatchers("/api/v1/register", "/**").anonymous()
                .anyRequest().permitAll();

        http.formLogin()
                /**
                 * URL для авторизации
                 * */
                .loginProcessingUrl("/login")
                /**
                 * Страница авторизации
                 * */
                .loginPage("/login")
                /**
                 * Перенаправление при ошибке
                 * */
                .failureUrl("/status")
                /**
                 * Перенаправление при удачной авторизации
                 * */
                .defaultSuccessUrl("/status")
                /**
                 * Праметры формы логина
                 * @param username - логин
                 * @param password - пароль
                 * */
                .usernameParameter("username")
                .passwordParameter("password");

        /** Выход */
        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/status")
                .invalidateHttpSession(true);

        /** Отключаю csrf
         * ибо буду юзать rest api
         * */
        http.csrf()
                .disable()
                /** репозиторий для доступа к токенам*/
                .rememberMe().tokenRepository(persistentTokenRepository)
                /** Парметр для получения токена*/
                .tokenValiditySeconds(86400000).key("remember-me");

        /** Перенаправлять если нет доступа */
        http.exceptionHandling().accessDeniedPage("/403");

    }

}

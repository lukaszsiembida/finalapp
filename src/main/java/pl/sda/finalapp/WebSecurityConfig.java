package pl.sda.finalapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.sda.finalapp.users.Role;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource; //domyslnie wstrzykie aktualne zrodlo bazy danych zdefiniowana w application.properties

    @Autowired
    private PasswordEncoder passwordEncoder;

    //jakie Urle sa chronione i na jakich urlach dziala mechanizm logowania
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/products/**").hasAnyAuthority(Role.ADMIN) //na dany url moga wejsc osoby zalogowane i z danym authority
//                .antMatchers("/products/**").hasAnyRole("ADMIN")
                .antMatchers("/login", "/register").permitAll() //wpuszczamy wszystkich na dane urle
                .antMatchers("/css/**").permitAll()
                .anyRequest().authenticated() //na kazde miejsce w aplikacji musisz byc zalogowany
                .and()
                .csrf().disable()
                .formLogin() //konfiguracja sposobu logowania sie
                .loginPage("/login")//tu zostaniemy przekierowani, jesli nie jestesmy zalogowani
                .usernameParameter("username") //nazwa parametru, ktory bedzie w formularzu logowania
                .passwordParameter("password")
                .loginProcessingUrl("/login-process") //url na ktory zostanie wystany request po kliknieciu zaloguj
                .failureUrl("/login?error=1") //tu zostaniemy przekierowani po blędzie
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");

    }

    //konfiguracja sposobu autentykacji(kim jestes - logowanie)
    // i autoRyzacji(jaką masz Role/uprawnienia)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select u.E_MAIL, u.PASSWORD_HASH, 1 from User u where u.E_MAIL =?") //sprawdza, czy mozemy sie zaogowac
                .authoritiesByUsernameQuery("select u.E_MAIL, r.ROLE_NAME, 1 from User u \n" +
                        "join USER_ROLE ur on ur.USER_ID=u.ID\n" +
                        "join ROLE r on r.ID = ur.ROLES_ID\n" +
                        "where u.E_MAIL =?") //pobiera uprawnienia i role(authorities)
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }
}

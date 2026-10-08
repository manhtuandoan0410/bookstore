package fi.haagahelia.bookstore.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity (securedEnabled = true)
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests( authorize -> authorize
                .requestMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
            )
            . formLogin( formlogin -> formlogin
                .loginPage("/login")
                .defaultSuccessUrl("/booklist", true)
                .permitAll()
            )
            .logout( logout -> logout
                .permitAll()
            );
            return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("ADMIN")
            .build();

            List<UserDetails> users = new ArrayList<>();
            users.add(user);
        return new InMemoryUserDetailsManager(users);
    }
}

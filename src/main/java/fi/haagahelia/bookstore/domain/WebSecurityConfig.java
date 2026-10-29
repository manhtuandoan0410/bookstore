package fi.haagahelia.bookstore.domain;

// import java.util.ArrayList;
// import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //strictly restrict right of user and public users
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/booklist", "/login", "/css/**", "/signup").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books", "/api/books/**").permitAll()
                        .requestMatchers(
                                "/book/delete/**",
                                "/book/edit/**",
                                "/book/addbook/**",
                                "/api/books",
                                "/api/books/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formlogin -> formlogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/booklist", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );
        return http.build();
    }

//     @Bean
//     public UserDetailsService userDetailsService() {
//         UserDetails admin = User.withDefaultPasswordEncoder()
//                 .username("admin")
//                 .password("password")
//                 .roles("ADMIN")
//                 .build();

//         UserDetails user1 = User.withDefaultPasswordEncoder()
//                 .username("customer1")
//                 .password("customer1")
//                 .roles("CUSTOMER")
//                 .build();

//         List<UserDetails> users = new ArrayList<>();
//         users.add(admin);
//         users.add(user1);
//         return new InMemoryUserDetailsManager(users);
//     }

}

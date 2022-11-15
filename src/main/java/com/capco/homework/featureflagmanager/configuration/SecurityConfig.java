package com.capco.homework.featureflagmanager.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Value("${admin.user-name}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Bean
    public PasswordEncoder getBcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // we would probably want something more fancy in real life situation
    public InMemoryUserDetailsManager get() {
        UserDetails admin = User.withUsername(adminUsername)
                .password(getBcryptPasswordEncoder().encode(adminPassword))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(List.of(admin));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests((autz) -> autz
                        .antMatchers("/api/business/**").permitAll()
                        .antMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                )
                .httpBasic() // just an example...
                .and()
                .csrf()
                .disable() // cutting corners for simplicity
                .build();
    }
}
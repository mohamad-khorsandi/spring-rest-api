package ir.sobhan.security;

import ir.sobhan.dao.UserRepository;
import ir.sobhan.model.entity.peopleEntities.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;


@RequiredArgsConstructor
@Configuration
public class Config {
    private final UserRepository userRepository;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(){return new UserDetailsService() {
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            AppUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user name not found"));
            Collection<SimpleGrantedAuthority> authorities = user.getAuthorities();

            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    };}
}

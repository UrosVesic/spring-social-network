package rs.ac.bg.fon.springsocialnetwork.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.bg.fon.springsocialnetwork.jwt.JwtFilter;

import java.util.Arrays;

/**
 * @author UrosVesic
 */
@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig{

    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                        .antMatchers("/api/auth/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/topic/**")
                        .permitAll()
                        .antMatchers("/api/post/authAll")
                        .hasAuthority("USER")
                        .antMatchers(HttpMethod.GET, "/api/post/{id}")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/post/all")
                        .permitAll()
                        .antMatchers("/api/post/secured/**")
                        .hasAuthority("ADMIN")
                        .antMatchers("/sba-websocket/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/comment/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/user/**")
                        .permitAll()
                        .antMatchers("/api/admin/**")
                        .hasRole("ADMIN")
                        .antMatchers("/api/role/**")
                        .permitAll()
                        .antMatchers("/api/user/{username}/assign/{rolename}")
                        .permitAll()
                        .antMatchers(HttpMethod.DELETE,"/api/user/**")
                        .hasAuthority("ADMIN")
                        .antMatchers("/api/message/**")
                        .hasAuthority("USER")
                        .anyRequest()
                        .authenticated()
                        .and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authManager(HttpSecurity http)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

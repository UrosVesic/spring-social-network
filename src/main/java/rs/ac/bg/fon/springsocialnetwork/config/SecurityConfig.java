package rs.ac.bg.fon.springsocialnetwork.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import rs.ac.bg.fon.springsocialnetwork.jwt.JwtFilter;
import rs.ac.bg.fon.springsocialnetwork.jwt.JwtProvider;
import rs.ac.bg.fon.springsocialnetwork.service.UserDetailsServiceImpl;

import java.util.Arrays;

/**
 * @author UrosVesic
 */
@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
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
                        .anyRequest()
                        .authenticated());
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }




    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

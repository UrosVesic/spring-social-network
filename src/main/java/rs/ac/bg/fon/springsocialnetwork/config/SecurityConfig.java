package rs.ac.bg.fon.springsocialnetwork.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
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
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author UrosVesic
 */
@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig{

    private UserDetailsService userDetailsService;
    private RsaKeyProperties rsaKeyProperties;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                        .antMatchers("/api/auth/**")
                        .permitAll()
                        .antMatchers(HttpMethod.OPTIONS,"/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/topic/**")
                        .permitAll()
                        .antMatchers("/api/post/authAll")
                        .hasAuthority("SCOPE_USER")
                        .antMatchers(HttpMethod.GET, "/api/post/{id}")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/post/all")
                        .permitAll()
                        .antMatchers("/api/post/secured/**")
                        .hasAuthority("SCOPE_ADMIN")
                        .antMatchers("/sba-websocket/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/comment/**")
                        .permitAll()
                        .antMatchers(HttpMethod.GET, "/api/user/**")
                        .permitAll()
                        .antMatchers("/api/admin/**")
                        .hasAuthority("SCOPE_ADMIN")
                        .antMatchers("/api/role/**")
                        .permitAll()
                        .antMatchers("/api/user/{username}/assign/{rolename}")
                        .permitAll()
                        .antMatchers(HttpMethod.DELETE,"/api/user/**")
                        .hasAuthority("SCOPE_ADMIN")
                        .antMatchers("/api/message/**")
                        .hasAuthority("SCOPE_USER")
                        .anyRequest()
                        .authenticated()
                        .and().sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and().oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getPublicKey()).build();
    }


    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk  = new RSAKey.Builder(rsaKeyProperties.getPublicKey()).privateKey(rsaKeyProperties.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
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

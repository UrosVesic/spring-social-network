package rs.ac.bg.fon.springsocialnetwork.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rs.ac.bg.fon.springsocialnetwork.mapper.UserMapper;

@Configuration
public class IocContainer {
    @Bean
    public UserMapper userMapper(){
        return new UserMapper();
    }
}

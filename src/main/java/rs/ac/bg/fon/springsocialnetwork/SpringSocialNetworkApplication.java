package rs.ac.bg.fon.springsocialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import rs.ac.bg.fon.springsocialnetwork.config.AppConfig;
import rs.ac.bg.fon.springsocialnetwork.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class, RsaKeyProperties.class})

public class SpringSocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSocialNetworkApplication.class, args);
	}

}

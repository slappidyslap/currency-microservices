package kg.musabaev.accountprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests()
				.requestMatchers("/**").hasAnyAuthority("SCOPE_account.read")
				.and()
				.oauth2ResourceServer()
				.jwt()
				.and()
				.and()
				.build();
	}
}

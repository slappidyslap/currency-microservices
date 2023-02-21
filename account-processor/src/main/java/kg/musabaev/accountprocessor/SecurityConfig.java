package kg.musabaev.accountprocessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(matchers -> matchers
						.requestMatchers(HttpMethod.POST, "/**").hasAuthority("SCOPE_account.write")
						.requestMatchers(HttpMethod.PUT, "/**").hasAuthority("SCOPE_account.write")
						.requestMatchers(HttpMethod.GET, "/**").hasAuthority("SCOPE_account.read"))
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
				.build();
	}
}

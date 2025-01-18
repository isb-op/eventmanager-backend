package com.neki.eventmanager.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.neki.eventmanager.cors.CorsConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	SecurityFilter securityFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
						.permitAll().requestMatchers(HttpMethod.POST, "/account/sign-in").permitAll()
						.requestMatchers(HttpMethod.POST, "/account/sign-up").permitAll()
						.requestMatchers(HttpMethod.POST, "/events/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.GET, "/events/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/events/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/events/**").hasRole("ADMIN").anyRequest().authenticated())
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package fr.eni.encheres.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean // Utilisateur temporaire
	public UserDetailsService userDetailsService() {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String passwordChiffreAdmin = passwordEncoder.encode("admin");
		String passwordChiffreUser = passwordEncoder.encode("user");
		
		UserDetails admin = User.builder()
			.username("admin")
			.password(passwordChiffreAdmin)
			.roles("USER", "ADMIN")
			.build();
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordChiffreUser)
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user, admin);
	}

}

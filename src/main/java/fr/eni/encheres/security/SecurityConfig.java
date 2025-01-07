package fr.eni.encheres.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	/*@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } */
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL));
		
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/" , "/encheres" ,"/accueil", "/creer").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/images/**").permitAll()
				.requestMatchers("/detailsUtilisateurs").authenticated()
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(form-> form

					.loginPage("/login")

					.defaultSuccessUrl("/", true)

					.defaultSuccessUrl("/", true) 


					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/", true)

					.permitAll())
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
				.logoutSuccessUrl("/")
				.addLogoutHandler(clearSiteData)
				)
		;

		return http.build();
	}

	/* @Bean // Utilisateur temporaire 
	public UserDetailsService users() {
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
	} */
	
	@Bean
	public UserDetailsService users(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		// Configuration de la requête permettant de vérifier que l'utilisateur a bien accès
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, 1 FROM UTILISATEURS WHERE pseudo = ?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role FROM UTILISATEURS where pseudo = ?");
		
		return jdbcUserDetailsManager;
	}

}

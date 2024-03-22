package com.thiago.mensageria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * O spring security funciona a base de filtros e varios filtros juntos criam
 * uma cadeia de filtros (FilterChain). 1- Mas como saber qual filtro utilizar?
 * Simples, o spring security tem um DelegatingFilterProxy que delega pra um
 * FilterChainProxy, que por sua vez age como um seletor para decidir qual
 * filterchain será executado, mas isso tambem já esta incluso no spring
 * security e só sera implementado em codigo em situações mais especiais como
 * limpar o SecurityContext para evitar haver vazamento de memoria e para
 * implementar o HttpFirewall que protege contra certos tipos de ataques.
 * 
 * Vale resaltar que os filtros naturalmente ocorrem antes dos servlets para
 * poderem manusear os dados ou valida-los. Outro ponto a se lembrar é que
 * quando se quiser criar um filtro personalizado, não se pode anotar ele
 * com @component e nem com @bean porque pode acabar dizendo ao spring que vai
 * ser inicializado e inicializar duas vezes porque por ele ser injetado na
 * função filterchain (como no codigo abaixo) ele já será inicializado sem o
 * spring inicializar ele.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

	// aqui criamos os roles que podem acessar nossa aplicação, na memoria.
	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user").password(bCryptPasswordEncoder.encode("1234")).roles("USER").build());
		manager.createUser(User.withUsername("admin").password(bCryptPasswordEncoder.encode("123"))
				.roles("USER", "ADMIN").build());
		return manager;
	}

	/*
	 * configura a ordem de verificação dos filtros, caso determinado filtro tenha
	 * sido configurado no projeto. 1- CSRF FILTER 2- USER E PASSWORD AUTHENTICATION
	 * FILTER (like login) 3- BASIC AUTHENTICATION FILTER 4- AUTORIZATION FILTER (if
	 * the user can do the task or not)
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(Customizer.withDefaults())
		.authorizeHttpRequests(
				authorize -> authorize
				.requestMatchers("/**").hasAnyRole("admin", "user")
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.formLogin(Customizer.withDefaults());
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService)
				.passwordEncoder(bCryptPasswordEncoder).and().build();
	}
}

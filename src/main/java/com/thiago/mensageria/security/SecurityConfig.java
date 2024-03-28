package com.thiago.mensageria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

	// aqui criamos os roles que podem acessar nossa aplicação, na memoria, posteriormente será trocada para memoria no banco de dados.
	/*
	@Bean
	public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		manager.createUser(User.withUsername("user").password(bCryptPasswordEncoder.encode("1234")).roles("USER").build());
		manager.createUser(User.withUsername("admin").password(bCryptPasswordEncoder.encode("123"))
				.roles("USER", "ADMIN").build());
		return manager;
	}
	*/
	
	/* criando o maneger de autenticação, que é a api que diz para o spring security como performar a authenticação
	   o userDetailsService é o que foi contruido antes para criação dos roles com senhas e usuarios e aqui é onde dizemos ao spring
	   que esses detalhes que devem ser utilizados. A só a nivel pra não ter duvidas, o ProviderManeger implementa o AuthenticationManeger.
	   
	   Obs: no caso de usar o banco de dados, essa função é desnecessaria.
	*/
	/*
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		
		ProviderManager provider =  new ProviderManager(authenticationProvider);
		provider.setEraseCredentialsAfterAuthentication(false);
		return provider;
	}
	*/
	
	@Autowired
	private SecurityDatabaseService service;
	
	 @Autowired
	 public void globalUserDetails(AuthenticationManagerBuilder auth, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
	        auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder);
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
				.requestMatchers(HttpMethod.POST, "/login").permitAll()
				.requestMatchers("/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated())
		.httpBasic(Customizer.withDefaults())
		.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll());
		return http.build();
	}
}

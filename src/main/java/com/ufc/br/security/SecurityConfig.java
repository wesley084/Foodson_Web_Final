package com.ufc.br.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/", "/index", "/listarPratos", "/cadastroPessoa","/loginCadastro#paracadastro","/loginCadastro#para", "/salvarPessoa").permitAll()
		.antMatchers("/css/**", "/script/**", "/img/**","/imagens/**").permitAll()
		.antMatchers("/cesta", "/historico").hasRole("USER")
		
		.anyRequest().authenticated()//hasAnyRole
		.and().formLogin()
//		.loginPage("/loginCadastro#paralogin")
//		.loginProcessingUrl("/loginEntrar")
//		.defaultSuccessUrl("/index")
		.permitAll()
		.and().logout().logoutSuccessUrl("/").permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}

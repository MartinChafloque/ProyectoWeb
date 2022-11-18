package com.example.editorial;

import com.example.editorial.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class EditorialServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EditorialServicesApplication.class, args);
	}

	//La clase interna WebSecurityConfig nos permite especificar la configuración de acceso a los recursos publicados.
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
					.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
					.authorizeRequests()
					.antMatchers(HttpMethod.GET, "/login").permitAll() //permitir  que el usuario haga login (no necesita autorización).
					.antMatchers(HttpMethod.GET, "/editorials/{id}").permitAll()
					.anyRequest().authenticated();
		}
	}
}

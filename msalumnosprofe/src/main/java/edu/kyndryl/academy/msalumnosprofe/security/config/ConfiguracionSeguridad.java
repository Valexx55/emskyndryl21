package edu.kyndryl.academy.msalumnosprofe.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

//aquí configuramos la seguridad de nuestro microservicio
//anotaciones

@EnableWebSecurity //indicamos que aquí se gestiona la seguridad
@Configuration//así Spring al levantarse, trata esta clase
public class ConfiguracionSeguridad {
	
	@Bean //esta clase la usa Spring para acceder a las credenciales de los usuarios en el Sistema
	@Profile({"dev"})//indicamos el perfil/entorno al que aplica esta configuración
	public UserDetailsService userDetailsEnMemoria ()
	{
		UserDetailsService userDetailsService = null;
		
		List<UserDetails> usuarios = List.of (
				User.withUsername("user").password("{noop}user").roles("USER").build(),
				User.withUsername("admin").password("{noop}admin").roles("ADMIN").build()
				);
		
		userDetailsService = new InMemoryUserDetailsManager(usuarios);
		
		return userDetailsService;
		
	}
	
	@Bean //este clase representa la política de seguridad
	@Profile({"dev"})//indicamos el perfil/entorno al que aplica esta configuración
	public SecurityFilterChain filtroDesarrollo (HttpSecurity httpSecurity) throws Exception
	{
		
		return httpSecurity.csrf(c -> c.disable()).authorizeHttpRequests
			(auth -> auth.requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
				.requestMatchers("/alumno/**").authenticated())
				.httpBasic(Customizer.withDefaults()).build();
		
	}

}

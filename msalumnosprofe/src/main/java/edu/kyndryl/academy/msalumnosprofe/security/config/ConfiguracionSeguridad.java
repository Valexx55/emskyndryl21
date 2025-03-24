package edu.kyndryl.academy.msalumnosprofe.security.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.kyndryl.academy.msalumnosprofe.security.filter.JWTFiltroATH;
import edu.kyndryl.academy.msalumnosprofe.security.filter.JWTFiltroATZ;
import edu.kyndryl.academy.msalumnosprofe.security.service.UserDetailServiceImp;

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
	
	@Bean
	@Profile({"prod"})
	public PasswordEncoder passordEncoder ()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Profile({"prod"})
	public UserDetailsService userDetailServiceBD ()
	{
		return new UserDetailServiceImp();
	}
	
	@Bean
	@Profile({"prod"})
	public AuthenticationManager authenticationManager (AuthenticationConfiguration aConfiguration) throws Exception
	{
		return aConfiguration.getAuthenticationManager(); //esto por defecto, pilla tu UserDetailService y tu PaswordEncoder configurados
	}
	
	
	
	@Bean //este clase representa la política de seguridad
	@Profile({"prod"})//indicamos el perfil/entorno al que aplica esta configuración
	public UsernamePasswordAuthenticationFilter crearFiltroAutentication (AuthenticationManager am)
	{
		JWTFiltroATH jwtFiltroATH = null;
		
			jwtFiltroATH = new JWTFiltroATH();
			jwtFiltroATH.setAuthenticationManager(am);
			//definimos la url con la que se activa
			jwtFiltroATH.setFilterProcessesUrl("/alumno/login");
		
		
		return jwtFiltroATH;
	}
	
	
	@Bean //este clase representa la política de seguridad
	@Profile({"prod"})//indicamos el perfil/entorno al que aplica esta configuración
	public SecurityFilterChain filtroProduccionJWT (HttpSecurity httpSecurity, AuthenticationManager am, JWTFiltroATH jAth, JWTFiltroATZ jAtz) throws Exception
	{
		
		return httpSecurity.csrf(c -> c.disable()).authenticationManager(am)
				.authorizeHttpRequests
			(auth -> auth.requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN")
				.requestMatchers("/alumno/**").authenticated())
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(sesion-> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilter(jAth)
				.addFilterBefore(jAtz, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}
	
	/*public static void main(String[] args) {
		String contrasenia = "user";
		var cripto =  new BCryptPasswordEncoder();
		String contrasenia_codificada = cripto.encode(contrasenia);
		System.out.println(contrasenia_codificada);
	}*/

}

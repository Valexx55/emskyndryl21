package edu.kyndryl.academy.msalumnosprofe.security.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/**")
public class MiFiltro implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("HOLA:::PASANDO POR AQUÍ");
		//le dejo pasar
		//obtener la cabecera de Autorización
		HttpServletRequest hr = (HttpServletRequest) request;
		String cadenaauth =  hr.getHeader("Authorization");
		System.out.println(cadenaauth);
		String cadenadesc =  Base64.getDecoder().decode(cadenaauth).toString();
		System.out.println(cadenadesc);
		
		chain.doFilter(request, response);
		
		System.out.println("HOLA:::VUELVO POR AQUÍ");
	}

}

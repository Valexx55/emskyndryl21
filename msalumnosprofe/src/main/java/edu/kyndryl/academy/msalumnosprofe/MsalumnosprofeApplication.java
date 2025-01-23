package edu.kyndryl.academy.msalumnosprofe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication//clase de configuracion pendiente comentar
@EnableDiscoveryClient //activamos el cliente eureka
public class MsalumnosprofeApplication {

	//TODO comentar mejoras
	public static void main(String[] args) {
		SpringApplication.run(MsalumnosprofeApplication.class, args);
	}

}

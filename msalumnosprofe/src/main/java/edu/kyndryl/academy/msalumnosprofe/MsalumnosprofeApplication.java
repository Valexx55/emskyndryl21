package edu.kyndryl.academy.msalumnosprofe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication//clase de configuracion pendiente comentar
@EnableDiscoveryClient //activamos el cliente eureka
@EntityScan("edu.kyndryl.academy.mscomunprofe.entity")//con esta anotación, ayudo a Spring a localizar las entidades, puesto que se encuentran en un paquete distintos del raíz
//@ComponentScan //esta anotación, sería obligada para localizar COntroller, Service y Repository, si estuvieran en distintos paquetes del raíz
@EnableFeignClients//activamos el feign
public class MsalumnosprofeApplication {

	//TODO comentar mejoras
	public static void main(String[] args) {
		SpringApplication.run(MsalumnosprofeApplication.class, args);
	}

}

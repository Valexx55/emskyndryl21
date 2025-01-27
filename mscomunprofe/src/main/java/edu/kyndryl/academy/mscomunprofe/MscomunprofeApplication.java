package edu.kyndryl.academy.mscomunprofe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("edu.kyndryl.academy.mscomunprofe.entity")//con esta anotación, ayudo a Spring a localizar las entidades, puesto que se encuentran en un paquete distintos del raíz
public class MscomunprofeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MscomunprofeApplication.class, args);
	}

}

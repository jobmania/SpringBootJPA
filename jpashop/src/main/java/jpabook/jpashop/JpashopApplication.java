package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}


	// V1 Hibernate 모듈 -> 엔티티를 직접 반환 ! --> 사용하면 안된다 !
	@Bean
	Hibernate5Module hibernate5Module(){
		return new Hibernate5Module();
	}

}

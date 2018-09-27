package cn.itcast.springboot.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
//@Configuration
public class HelloApplication {

	@RequestMapping(value="/hello")
	public String hello() {
		return "hello word";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}
}

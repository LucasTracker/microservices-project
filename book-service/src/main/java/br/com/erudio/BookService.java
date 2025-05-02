package br.com.erudio;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookService {

	public static void main(String[] args) {
		SpringApplication.run(BookService.class, args);
	}

}

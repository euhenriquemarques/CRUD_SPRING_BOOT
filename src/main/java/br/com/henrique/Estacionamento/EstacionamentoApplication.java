package br.com.henrique.Estacionamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class EstacionamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamentoApplication.class, args);
	}
	
	@RequestMapping(name =  "/",method = RequestMethod.GET)
	public String teste() {
		return "ok";
	}

}

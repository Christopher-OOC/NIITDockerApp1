package com.example.niit.DockerApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class DockerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerAppApplication.class, args);
	}
}

@RestController
class DockerController {

	@GetMapping(value = "/message")
	public String test() {

		return """
				Welcome to Docker! You have just
				started a new journey!
				""";
	}

	@GetMapping(value = "/test")
	public Map<String, String> jsonOutput() {

		return Map.of(
				"name", "Christopher",
				"faculty", "MMS",
				"floor", "4th"
		);
	}

}

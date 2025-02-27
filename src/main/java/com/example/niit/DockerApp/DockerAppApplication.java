package com.example.niit.DockerApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DockerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DockerAppApplication.class, args);
	}
}

@Controller
class DockerController {

	Logger LOGGER = LoggerFactory.getLogger(DockerController.class);

	@GetMapping(value = "/")
	public String homePage(Model model) throws IOException, ClassNotFoundException {
		model.addAttribute("message", new Message());
		List<Message> messageList = new ArrayList<>();
		InputStream stream = new ClassPathResource("/data/messages.txt").getInputStream();
		Scanner scanner = new Scanner(stream);

		while (scanner.hasNext()) {
			messageList.add(new Message(scanner.nextLine(), scanner.nextLine()));
		}
		scanner.close();

		if (messageList.size() == 0) {
			model.addAttribute("noContent", "No message");
		}
		else {
			model.addAttribute("messages", messageList);
		}

		return "index";
	}

	@PostMapping(value = "/submit")
	public String postMessage(@ModelAttribute("message") Message message,
							  Model model,
							  RedirectAttributes redirectAttributes) throws IOException {

		redirectAttributes.addFlashAttribute(  "success", "Your message is sent successfully!");
		File file = new ClassPathResource("/data/messages.txt").getFile();
		PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));

		writer.println(message.getTitle());
		writer.println(message.getContent());
		writer.close();

		return "redirect:/";
	}
}


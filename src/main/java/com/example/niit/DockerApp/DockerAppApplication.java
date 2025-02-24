package com.example.niit.DockerApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

		File file = new File("C:/Users/Christopher-JavaLord/Downloads/DockerApp/src/main/resources/messages.dat");
		List<Message> messageList = new ArrayList<>();

		if (file.length() == 0) {
			model.addAttribute("noContent", "No message");
		}
		else {
			Message obj;
				try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
					obj = (Message)inputStream.readObject();
					while (obj != null) {
						messageList.add(obj);
						obj = (Message) inputStream.readObject();
					}
				}
				catch (Exception ex) {
					obj = null;
				}

			model.addAttribute("messages", messageList);
		}

		System.out.println("Size: " + messageList.size());
		messageList.forEach(System.out::println);

		return "index";
	}

	@PostMapping(value = "/submit")
	public String postMessage(@ModelAttribute("message") Message message,
							  Model model,
							  RedirectAttributes redirectAttributes) throws IOException {

		redirectAttributes.addFlashAttribute(  "success", "Your message is sent successfully!");

		LOGGER.info("Your message, {}", message);
		File file = new File("C:/Users/Christopher-JavaLord/Downloads/DockerApp/src/main/resources/messages.dat");
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file, true));
		outputStream.writeObject(message);
		outputStream.writeChars("\r\n");

		outputStream.close();

		return "redirect:/";
	}
}


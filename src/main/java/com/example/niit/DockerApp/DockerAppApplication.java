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
import java.util.Map;

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

		File file = new File("C:/Users/Christopher-JavaLord/Downloads/DockerApp/src/main/resources/messages.txt");
		List<Message> messageList = new ArrayList<>();

		if (file.length() == 0) {
			model.addAttribute("noContent", "No message");
		}
		else {
			ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));


			while (inputStream.available() != 0) {
				System.out.println("Messages: " + inputStream.readObject());
				messageList.add((Message) inputStream.readObject());
			}
		}
		messageList.forEach(System.out::println);

		model.addAttribute("messages", messageList);

		return "index";
	}

	@PostMapping(value = "/submit")
	public String postMessage(@ModelAttribute("message") Message message,
							  Model model,
							  RedirectAttributes redirectAttributes) throws IOException {

		redirectAttributes.addFlashAttribute(  "success", "Your message is sent successfully!");

		LOGGER.info("Your message, {}", message);
		File file = new File("C:/Users/Christopher-JavaLord/Downloads/DockerApp/src/main/resources/messages.txt");
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(message);

		return "redirect:/";
	}
}

class Message implements Serializable {
	private String title;
	private String content;

	public Message() {

	}

	public Message(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message{" +
				"title='" + title + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}

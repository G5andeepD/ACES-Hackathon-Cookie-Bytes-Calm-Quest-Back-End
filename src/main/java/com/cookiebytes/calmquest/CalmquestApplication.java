package com.cookiebytes.calmquest;

import com.cookiebytes.calmquest.message.Message;
import com.cookiebytes.calmquest.message.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class CalmquestApplication {


	public static void main(String[] args) {
		SpringApplication.run(CalmquestApplication.class, args);
	}
@Bean
	public CommandLineRunner commandLineRunner(MessageRepository messageRepository){
		return args -> {
			messageRepository.save(new Message(0L, "sad", "Better days are ahead!"));
			messageRepository.save(new Message(0L, "fear", "Fear to growth, embrace it!"));
			messageRepository.save(new Message(0L, "happy", "Happiness spreads, be contagiously joyful!"));
			messageRepository.save(new Message(0L, "angry", "Anger fuels determination, achieve greatness!"));
			messageRepository.save(new Message(0L, "neutral", "Make each day positively impactful!"));


				};
	}

}

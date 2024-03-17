package com.westside.west_side_auto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class WestSideAutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WestSideAutoApplication.class, args);
	}

	private EmailSenderService sender;

	// @EventListener(AppliicationReadyEvent.class)
	public void sendEmail(){
		sender.sendEmail("samrath2004@gmail.como","test", "This is a test");
	}

}

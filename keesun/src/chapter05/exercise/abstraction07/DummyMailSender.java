package chapter05.exercise.abstraction07;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class DummyMailSender implements MailSender {

	public void send(SimpleMailMessage simpleMessage) throws MailException {
		
	}

	public void send(SimpleMailMessage[] simpleMessages) throws MailException {
		
	}

}

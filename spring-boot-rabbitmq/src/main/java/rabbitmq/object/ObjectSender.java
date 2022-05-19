package rabbitmq.object;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rabbitmq.domain.User;

@Component
public class ObjectSender {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	public void send(User user) {
		System.out.println("Sender object: " + user.toString());
		this.rabbitTemplate.convertAndSend("object", user);
	}

}
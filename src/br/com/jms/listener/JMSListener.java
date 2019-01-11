package br.com.jms.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Listener para consumir as mensagens da activemq 
 * 
 * @author Paulo Henrique Maia Soares
 *
 */
public class JMSListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println("Recebendo a mensagem: " + textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}

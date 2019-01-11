package br.com.jms.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.jms.model.Pedido;

/**
 * 
 * Consumidor de topicos da activemq utilizando seletores
 * 
 * @author Paulo Henrique Maia Soares
 *
 */
public class ConsumidorTopicoSelector {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {

		System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

		Connection connection = factory.createConnection();
		connection.setClientID("client2");

		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Topic topico = (Topic) context.lookup("loja");
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura-selector", "ebook is null OR ebook=false", false);

		System.out.println("Aguardando mensagens...");

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println("Recebendo a mensagem: " + pedido);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		new Scanner(System.in).nextLine();

		connection.close();
		context.close();

	}

}
package br.com.jms.consumer;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.MessageConsumer;
import javax.naming.InitialContext;

import br.com.jms.listener.JMSListener;
import br.com.jms.utils.JMSUtils;

/**
 * Consumidor de filas da activemq
 * 
 * @author Paulo Henrique Maia Soares
 *
 */
public class ConsumidorFila {

	@SuppressWarnings("resource") 
	public static void main(String[] args) {

		InitialContext context = null;
		Connection connection = null;
		JMSListener jmsListener = new JMSListener();

		try {
			context = new InitialContext();
			connection = JMSUtils.createConnection(context);
			connection.start();

			MessageConsumer consumer = JMSUtils.createConsumer(context, connection, "loja");

			System.out.println("Aguardando mensagens...");
			
			consumer.setMessageListener(jmsListener);

			new Scanner(System.in).nextLine();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JMSUtils.close(connection);
			JMSUtils.close(context);
		}

	}

}
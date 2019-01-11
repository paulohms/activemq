package br.com.jms.utils;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Classe utilitatia para prover uma conexao
 * 
 * @author Paulo Henrique Maia Soares
 *
 */
public class JMSUtils {

	private static final String	USER		= "admin";
	private static final String	PASSWORD	= "admin";

	private JMSUtils() {
	}

	public static Connection createConnection(InitialContext context) throws NamingException, JMSException {
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		return factory.createConnection(USER, PASSWORD);
	}

	public static MessageConsumer createConsumer(InitialContext context, Connection connection, String queue) throws JMSException, NamingException {
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) context.lookup(queue);
		return session.createConsumer(destination);
	}

	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(InitialContext context) {
		if (context != null) {
			try {
				context.close();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

}

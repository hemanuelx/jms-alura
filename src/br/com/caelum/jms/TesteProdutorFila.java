package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteProdutorFila {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(destination);

        Message message = session.createTextMessage("<pedido><id>123</id></pedido>");
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}

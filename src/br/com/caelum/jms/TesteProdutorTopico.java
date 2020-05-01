package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TesteProdutorTopico {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);

        Message message = session.createTextMessage("<pedido><id>456</id><e-book>false</e-book<</pedido>");
        message.setBooleanProperty("ebook", false);
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}

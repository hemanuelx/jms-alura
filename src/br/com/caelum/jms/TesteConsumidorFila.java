package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Scanner;

public class TesteConsumidorFila {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext context = new InitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination destination = (Destination) context.lookup("financeiro");
        MessageConsumer consumer = session.createConsumer(destination);

        // Navega sobre as mensagens sem consumir
        //Destination fila = (Destination) context.lookup("financeiro");
        //QueueBrowser browser = session.createBrowser((Queue) fila);

        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}

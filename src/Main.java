import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Main {
	// URL of the JMS server. DEFAULT_BROKER_URL will just mean
    // that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // Name of the queue we will be sending messages to
    private static String subject = "TESTQUEUE1";
    
	public static void main (String[] args) throws JMSException{
		Producer producer = new Producer(url, subject);
		Consumer consumer = new Consumer(url, subject);
		producer.produce();
		consumer.consume();
	}

}

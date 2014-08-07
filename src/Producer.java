
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.mongodb.BasicDBObject;

public class Producer {
 
    private String url;     // URL of the JMS server.
    private String subject; // Name of the queue we will be sending messages to
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer producer;
    private DummyJMSThread dummyJMSThread = new DummyJMSThread();

    public Producer(String url, String subject) {
    	this.url = url;
    	this.subject = subject;
    }
    
    public void begin() throws JMSException{
    	// Getting JMS connection from the server and starting it
        connectionFactory = new ActiveMQConnectionFactory(url);
        
        connection = connectionFactory.createConnection();
        connection.start();

        // JMS messages are sent and received using a Session. We will
        // create here a non-transactional session object. If you want
        // to use transactions you should set the first parameter to 'true'
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        // Destination represents here our queue 'TESTQUEUE' on the
        // JMS server. You don't have to do anything special on the
        // server to create it, it will be created automatically.
        destination = session.createTopic(subject);

        // MessageProducer is used for sending messages (as opposed
        // to MessageConsumer which is used for receiving them)
        producer = session.createProducer(destination);
    }
    
    public void produce() throws JMSException{
    	new Thread(dummyJMSThread).start();
    }
    
    public void close() throws JMSException{   	
    	System.out.println("Closing producing connection.");
    	connection.close();
    }
    
    private class DummyJMSThread extends Thread {
    	public void run() {
    		try {
	    		begin();
	    		sendMessages();
	    		close();
    		}
    		catch (Throwable e) {
    			System.out.println(e.getMessage());
    		}
    	}
    	
    	private void sendMessages() {
    		try {
	    		for(int i = 0; i < 6; i++) {
	    			TextMessage message = session.createTextMessage();
	    			message.setText(createDummyMessage(i));
	    	        // Here we are sending the message!
	    			System.out.println("Sending message '" + ((TextMessage)message).getText()+ "'");
	    	        producer.send(message);
	    	        System.out.println("Sent message '" + ((TextMessage)message).getText() + "'");
	    	        Thread.sleep(1000);
	    		}
    		}
    		catch(Throwable e) {
    			System.out.println(e.getMessage());
    		}
    	}
    	private String createDummyMessage(int messageNumber)throws JSONException {
    		JSONObject object = new JSONObject();
			object.put("timestamp", new Date());
			object.put("pv_name", "channel" + messageNumber);
			object.put("text_message", "message" + messageNumber);
			Map<String, String> properties = new HashMap<String, String>();
			for(int j = 0; j < (int)(Math.random()*10); j++) {
				int rand = (int)(Math.random()*3);
				switch(rand) {
				case 0:
					properties.put("property" + j, "red");
					break;
				case 1:
					properties.put("property" + j, "blue");
					break;
				case 2:
					properties.put("property" + j, "green");
					break;
				}
			}
			object.put("properties", properties);
			return object.toString();
    	}
    }
}


package org.mycompany;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SQLProcessor implements Processor {
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final Logger logger = LoggerFactory.getLogger(SQLProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		
		logger.info("Read line:[{}]",exchange.getIn().getBody().toString());
        String[] req=exchange.getIn().getBody().toString().split("\\|");
        logger.info("split str: {} [{},{}]",new Object[]{req.length,req[0],req[1]});
		String query = "INSERT INTO ORG_S(ID, NAME, LOCATION) VALUES (" + this.getRandomNumber() + ", '"
				+ this.getRandomString() +"_"+req[0]+ "', '" + this.getRandomString() +"_"+req[1]+ "')";
		exchange.getIn().setBody(query);
	}

	private String getRandomString() {
		StringBuffer randStr = new StringBuffer(8);
		SecureRandom secureRandom = new SecureRandom();
		for (int i = 0; i < 8; i++)
			randStr.append(CHAR_LIST.charAt(secureRandom.nextInt(CHAR_LIST.length())));
		return randStr.toString();

	}

	private int getRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}
}

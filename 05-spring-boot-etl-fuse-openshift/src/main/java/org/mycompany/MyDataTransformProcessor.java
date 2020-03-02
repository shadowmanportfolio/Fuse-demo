/**
 * 
 */
package org.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sammz
 *
 */
public class MyDataTransformProcessor implements Processor {
	private final Logger logger = LoggerFactory.getLogger(MyDataTransformProcessor.class);
	@Override
	public void process(Exchange exchange) throws Exception {
			logger.info(">>>>>>>>>>>> This is data filter and transformer");
	}

}

/**
 * 
 */
package org.mycompany;

import java.io.InputStream;
import java.io.StringWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import oracle.sql.CLOB;

/**
 * @author sammz
 *
 */

@Component
public class clob2str implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {		
        CLOB body = exchange.getIn().getBody(CLOB.class);
        InputStream in = body.getAsciiStream();
        StringWriter w = new StringWriter();
        IOUtils.copy(in, w);
        exchange.getOut().setBody(w.toString());
        System.out.println(body);
	}

}

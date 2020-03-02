/*
 * #%L
 * Wildfly Camel :: Testsuite
 * %%
 * Copyright (C) 2013 - 2017 RedHat
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.wildfly.camel.examples.test.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.wildfly.camel.examples.test.common.FileConsumerTestSupport;
import org.wildfly.camel.examples.test.common.ServerLogReader;
import org.wildfly.camel.test.common.http.HttpRequest;

public abstract class AbstractJPAExampleTest extends FileConsumerTestSupport {

    @Test
    public void testFileProcessed() throws Exception {
        ServerLogReader.awaitLogMessage(getExpectedLogMessage(), 10000);
        HttpRequest.HttpResponse result = HttpRequest.get("http://localhost:8080/rest/api/books/order/1").getResponse();
        Assert.assertEquals(200, result.getStatusCode());
    }

    @Override
    protected String getExpectedLogMessage() {
        return String.format(".*%s.*Processed order.*", getContextName());
    }
}

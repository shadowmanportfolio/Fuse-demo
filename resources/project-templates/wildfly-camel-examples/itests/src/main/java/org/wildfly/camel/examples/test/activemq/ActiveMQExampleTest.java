/*
 * #%L
 * Wildfly Camel :: Testsuite
 * %%
 * Copyright (C) 2013 - 2014 RedHat
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
package org.wildfly.camel.examples.test.activemq;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.dmr.ModelNode;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.runner.RunWith;
import org.wildfly.camel.examples.test.jms.AbstractJMSExampleTest;
import org.wildfly.camel.test.common.utils.DMRUtils;
import org.wildfly.camel.test.common.utils.WildFlyCli;

@RunAsClient
@RunWith(Arquillian.class)
@ServerSetup({ActiveMQExampleTest.ActiveMQRarSetupTask.class})
public class ActiveMQExampleTest extends AbstractJMSExampleTest {

    private static final String ACTIVEMQ_EXAMPLE_WAR = "example-camel-activemq.war";
    private static final String ACTIVEMQ_RAR = "activemq-rar.rar";

    static class ActiveMQRarSetupTask implements ServerSetupTask {

        @Override
        public void setup(ManagementClient managementClient, String s) throws Exception {
            ModelNode batchNode = DMRUtils.batchNode()
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar", "add(archive=activemq-rar.rar)")
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar", "write-attribute(name=transaction-support,value=NoTransaction)")
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar/connection-definitions=QueueConnectionFactory", "add(class-name=org.apache.activemq.ra.ActiveMQManagedConnectionFactory, jndi-name=java:/ActiveMQConnectionFactory)")
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar/connection-definitions=QueueConnectionFactory/config-properties=ServerUrl", "add(value=vm://localhost?broker.persistent=false&broker.useJmx=false&broker.useShutdownHook=false)")
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar/admin-objects=OrdersQueue", "add(class-name=org.apache.activemq.command.ActiveMQQueue, jndi-name=java:/OrdersQueue)")
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar/admin-objects=OrdersQueue/config-properties=PhysicalName", "add(value=OrdersQueue)")
                .build();
            managementClient.getControllerClient().execute(batchNode);
            new WildFlyCli().run("reload").assertSuccess();
        }

        @Override
        public void tearDown(ManagementClient managementClient, String s) throws Exception {
            ModelNode batchNode = DMRUtils.batchNode()
                .addStep("subsystem=resource-adapters/resource-adapter=amq-ra.rar", "remove")
                .build();

            managementClient.getControllerClient().execute(batchNode);
        }
    }

    @Deployment(name = ACTIVEMQ_RAR, testable = false, order = 1)
    public static ResourceAdapterArchive createRarDeployment() {
        return ShrinkWrap.createFromZipFile(ResourceAdapterArchive.class, new File("target/examples/" + ACTIVEMQ_RAR));
    }

    @Deployment(name = ACTIVEMQ_EXAMPLE_WAR, testable = false, order = 2)
    public static WebArchive createDeployment() {
        return ShrinkWrap.createFromZipFile(WebArchive.class, new File("target/examples/" + ACTIVEMQ_EXAMPLE_WAR));
    }

    @Override
    protected String getContextName() {
        return "camel-activemq-context";
    }
}

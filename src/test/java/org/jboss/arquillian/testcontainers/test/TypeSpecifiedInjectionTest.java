/*
 * Copyright The Arquillian Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.arquillian.testcontainers.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.testcontainers.api.DockerRequired;
import org.jboss.arquillian.testcontainers.api.Testcontainer;
import org.jboss.arquillian.testcontainers.test.common.SimpleTestContainer;
import org.jboss.arquillian.testcontainers.test.common.WildFlyContainer;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.opentest4j.TestAbortedException;
import org.testcontainers.containers.GenericContainer;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@ExtendWith(ArquillianExtension.class)
@RunAsClient
@DockerRequired(TestAbortedException.class)
public class TypeSpecifiedInjectionTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Testcontainer(type = WildFlyContainer.class)
    private GenericContainer<?> wildfly;

    @Testcontainer(type = SimpleTestContainer.class)
    private GenericContainer<?> container;

    @Test
    public void checkWildFly() {
        Assertions.assertNotNull(wildfly);
        Assertions.assertTrue(wildfly.isRunning());
    }

    @Test
    public void checkSimpleTestContainer() {
        Assertions.assertNotNull(container, "Expected the container to be injected.");
        Assertions.assertTrue(container.isRunning(), "Expected the container to be running");
    }
}

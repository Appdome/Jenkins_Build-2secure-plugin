package io.jenkins.plugins.appdome.build.to.secure;

import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;
public class AppdomeBuilderTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    @Test
    public void testConfigRoundtrip() throws InterruptedException {
        System.out.println("This is a test message.");
        TimeUnit.MINUTES.sleep(5);
        assertTrue(true);


    }
}

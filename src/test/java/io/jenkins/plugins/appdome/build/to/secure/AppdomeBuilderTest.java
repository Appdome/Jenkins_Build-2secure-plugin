package io.jenkins.plugins.appdome.build.to.secure;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import com.cloudbees.plugins.credentials.matchers.IdMatcher;
import hudson.EnvVars;
import hudson.model.FreeStyleBuild;
import hudson.model.FreeStyleProject;
import hudson.model.Result;
import hudson.security.ACL;
import hudson.slaves.EnvironmentVariablesNodeProperty;
import hudson.util.Secret;
import io.jenkins.plugins.appdome.build.to.secure.platform.android.AndroidPlatform;
import io.jenkins.plugins.appdome.build.to.secure.platform.android.certificate.method.PrivateSign;

import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.jvnet.hudson.test.JenkinsRule;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class AppdomeBuilderTest {

    @Rule
    public JenkinsRule jenkins = new JenkinsRule();

    //    final String outputLocation = "/Users/idanhauser/work/output/081123144058_LocalMac_LOCAL_parallel/appdome_builder";
    final boolean buildWithLogs = false;
    final boolean BuildToTest = false;
    final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMjg1YTRmNTAtNjAyZi0xMWVkLWFkMTYtMTFlM2RjZjJlYjA1Iiwic2FsdCI6Ijc0OGM5OWZhLTQwY2MtNDVhNC04M2I5LWU3ZTQ3NDU1MDg0YSJ9.lhSU5MOCwnvixbmAuygJoC9rKHQfkf0upSD4ows0B-E";
    final String teamId = "46002310-7cab-11ee-bfde-d76f94716e7a";


    @Before
    public void setUp() throws Exception {
        setCommonEnvironmentVariables();
    }
    private void setCommonEnvironmentVariables() {
        EnvironmentVariablesNodeProperty prop = new EnvironmentVariablesNodeProperty();
        EnvVars env = prop.getEnvVars();
        env.put("APPDOME_SERVER_BASE_URL", "https://qamaster.dev.appdome.com");
        jenkins.jenkins.getGlobalNodeProperties().add(prop);
    }

    @Test
    public void testAndroidPrivateSignBuild() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
                // Create configuration objects
        PrivateSign privateSign = new PrivateSign("8DF593C1B6EAA6EADADCE36831FE82B08CAC8D74");
        privateSign.setGoogleSigning(false);
        executeShellCommand("pwd");
        executeShellCommand("ls -a");
        AndroidPlatform androidPlatform = new AndroidPlatform(privateSign);
        androidPlatform.setAppPath("https://appdome-automation-vanilla-apps.s3.amazonaws.com/Thomas/PipelineFiles/Apps/TimeCard.apk?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAUMYEOMD5MIW5WTF7%2F20231115%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Date=20231115T100713Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=9a22e8b2ddd38149d1fcd49c274769dcb547cdfdadd85d7ed8b5f7c90bf24f35");
        androidPlatform.setFusionSetId("8c693120-7cab-11ee-8275-c54d0e1c9b7a");


        AppdomeBuilder appdomeBuilder = new AppdomeBuilder(Secret.fromString(token), teamId, androidPlatform, null);

        appdomeBuilder.setBuildToTest(null);
        appdomeBuilder.setBuildWithLogs(this.buildWithLogs);

        project.getBuildersList().add(appdomeBuilder);
        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        System.err.println("TEST TEST TEST TEST");
        String consoleOutput = build.getLog();
        System.out.println("build console output = " + consoleOutput);
        System.out.println("build status = " + build.getResult().toString());
        jenkins.assertBuildStatus(Result.SUCCESS, build); // Check build status
    }

    @Test
    public void testAndroidPrivateSignBuild2() throws Exception {
        FreeStyleProject project = jenkins.createFreeStyleProject();
        // Create configuration objects
        PrivateSign privateSign = new PrivateSign("8DF593C1B6EAA6EADADCE36831FE82B08CAC8D74");
        privateSign.setGoogleSigning(false);
        executeShellCommand("pwd");
        executeShellCommand("ls -a");
        AndroidPlatform androidPlatform = new AndroidPlatform(privateSign);
        androidPlatform.setAppPath("https://appdome-automation-vanilla-apps.s3.amazonaws.com/Thomas/PipelineFiles/Apps/FileFinder.aab?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAUMYEOMD5MIW5WTF7%2F20231115%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Date=20231115T100200Z&X-Amz-Expires=3600&X-Amz-SignedHeaders=host&X-Amz-Signature=fadf03b8da641b13fa3ee0bbece8c26f87a99eb9c8f7faf0701869f379b07ce9");
        androidPlatform.setFusionSetId("8c693120-7cab-11ee-8275-c54d0e1c9b7a");


        AppdomeBuilder appdomeBuilder = new AppdomeBuilder(Secret.fromString(token), teamId, androidPlatform, null);

        appdomeBuilder.setBuildToTest(null);
        appdomeBuilder.setBuildWithLogs(this.buildWithLogs);

        project.getBuildersList().add(appdomeBuilder);
        FreeStyleBuild build = jenkins.buildAndAssertSuccess(project);
        System.err.println("TEST TEST TEST TEST");
        String consoleOutput = build.getLog();
        System.out.println("build console output = " + consoleOutput);
        System.out.println("build status = " + build.getResult().toString());
        jenkins.assertBuildStatus(Result.SUCCESS, build); // Check build status
    }

    private void executeShellCommand(String command) {
        System.out.println(command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            process.waitFor();
            reader.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}

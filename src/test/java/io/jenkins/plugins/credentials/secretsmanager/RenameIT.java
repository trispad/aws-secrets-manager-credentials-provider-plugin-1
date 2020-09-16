package io.jenkins.plugins.credentials.secretsmanager;

import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.CreateSecretResult;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class RenameIT {

    private static final String SECRET_STRING = "supersecret";

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final AWSSecretsManagerRule secretsManager = new AutoErasingAWSSecretsManagerRule();

    @Rule
    public final RuleChain chain = RuleChain
            .outerRule(Rules.awsAccessKey("fake", "fake"))
            .around(jenkins)
            .around(secretsManager);

    @Test
    @ConfiguredWithCode(value = "/rename.yml")
    public void shouldRenameCredentials() {
        // Given
        final CreateSecretResult foo = createSecretWithName("staging-foo", SECRET_STRING);
        final CreateSecretResult bar = createSecretWithName("staging-bar", SECRET_STRING);
        final CreateSecretResult baz = createSecretWithName("baz", SECRET_STRING);

        // When
        final List<StringCredentials> credentials = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        assertThat(credentials)
                .extracting("id")
                .contains("foo", "bar", "baz");
    }

    @Test
    @ConfiguredWithCode(value = "/rename.yml")
    public void shouldRenameCredentialsAndHandleNameCollisions() {
        // Given
        final CreateSecretResult stagingFoo = createSecretWithName("staging-foo", SECRET_STRING);
        final CreateSecretResult foo = createSecretWithName("foo", SECRET_STRING);

        // When
        final List<StringCredentials> credentials = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        fail("How should the name collision be handled?");
    }

    @Test
    @ConfiguredWithCode(value = "/rename.yml")
    public void shouldRenameCredentialsAndUseOriginalNameToFetchSecret() {
        // Given
        final CreateSecretResult stagingFoo = createSecretWithName("staging-foo", SECRET_STRING);

        // When
        final StringCredentials foo = jenkins.getCredentials().lookup(StringCredentials.class, "foo");

        // Then
        assertThat(foo.getSecret().getPlainText())
                .isEqualTo(SECRET_STRING);
    }

    private CreateSecretResult createSecretWithName(String name, String secretString) {
        final CreateSecretRequest request = new CreateSecretRequest()
                .withName(name)
                .withSecretString(secretString)
                .withTags(AwsTags.type(Type.string));

        return secretsManager.getClient().createSecret(request);
    }
}

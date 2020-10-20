package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import io.jenkins.plugins.credentials.secretsmanager.config.Fields;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.transformer.ReplaceFirst;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractIdIT {

    protected abstract PluginConfiguration getPluginConfiguration();

    protected abstract void setId(String regex, String replacement);

    @Test
    public void shouldSupportDefault() {
        // When
        final PluginConfiguration config = getPluginConfiguration();

        // Then
        assertThat(Optional.ofNullable(config.getFields()).map(Fields::getId)).isEmpty();
    }

    @Test
    public void shouldSupportReplaceFirst() {
        // Given
        final String regex = "foo-";
        final String replacement = "";
        setId(regex, replacement);

        // When
        final PluginConfiguration config = getPluginConfiguration();

        // Then
        assertThat(config.getFields())
                .extracting("id")
                .isEqualTo(new ReplaceFirst(regex, replacement));
    }
}

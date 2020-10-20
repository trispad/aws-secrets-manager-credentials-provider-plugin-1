package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.transformer.Default;
import io.jenkins.plugins.credentials.secretsmanager.config.transformer.ReplaceFirst;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractIdIT {

    protected abstract PluginConfiguration getPluginConfiguration();

    protected abstract void setId();

    protected abstract void setId(String regex, String replacement);

    @Test
    public void shouldSupportDefault() {
        // Given
        setId();

        // When
        final PluginConfiguration config = getPluginConfiguration();

        // Then
        assertThat(config.getBeta().getFields())
                .extracting("id")
                .isEqualTo(new Default());
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
        assertThat(config.getBeta().getFields())
                .extracting("id")
                .isEqualTo(new ReplaceFirst(regex, replacement));
    }
}

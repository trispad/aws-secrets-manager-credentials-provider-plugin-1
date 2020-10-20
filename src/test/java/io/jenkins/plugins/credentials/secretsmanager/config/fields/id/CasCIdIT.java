package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class CasCIdIT extends AbstractIdIT {

    @Rule
    public final JenkinsRule r = new JenkinsConfiguredWithCodeRule();

    @Override
    protected PluginConfiguration getPluginConfiguration() {
        return (PluginConfiguration) r.jenkins.getDescriptor(PluginConfiguration.class);
    }

    @Override
    protected void setId(String regex, String replacement) {
        // no-op (configured by annotations)
    }

    @Override
    @Test
    @ConfiguredWithCode("/config/fields/id/default.yml")
    public void shouldSupportDefault() {
        super.shouldSupportDefault();
    }

    @Override
    @Test
    @ConfiguredWithCode("/config/fields/id/replaceFirst.yml")
    public void shouldSupportReplaceFirst() {
        super.shouldSupportReplaceFirst();
    }
}

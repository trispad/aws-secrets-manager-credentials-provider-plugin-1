package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.util.JenkinsConfiguredWithWebRule;
import io.jenkins.plugins.credentials.secretsmanager.util.PluginConfigurationForm;
import org.junit.Rule;

public class WebIdIT extends AbstractIdIT {

    @Rule
    public final JenkinsConfiguredWithWebRule r = new JenkinsConfiguredWithWebRule();

    @Override
    protected PluginConfiguration getPluginConfiguration() {
        return (PluginConfiguration) r.jenkins.getDescriptor(PluginConfiguration.class);
    }

    @Override
    protected void setId(String regex, String replacement) {
        r.configure(form -> {
            final PluginConfigurationForm f = new PluginConfigurationForm(form);
            f.setReplaceFirstTransformation(regex, replacement);
        });
    }
}

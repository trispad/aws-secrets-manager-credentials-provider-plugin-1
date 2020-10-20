package io.jenkins.plugins.credentials.secretsmanager.config.fields.description;

import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.util.JenkinsConfiguredWithWebRule;
import org.junit.Rule;

public class WebDescriptionIT extends AbstractDescriptionIT {

    @Rule
    public final JenkinsConfiguredWithWebRule r = new JenkinsConfiguredWithWebRule();

    @Override
    protected PluginConfiguration getPluginConfiguration() {
        return (PluginConfiguration) r.jenkins.getDescriptor(PluginConfiguration.class);
    }

    @Override
    protected void setDescription(boolean description) {
        r.configure(form -> {
            form.getInputByName("_.description").setChecked(description);
        });
    }
}

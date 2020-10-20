package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.util.JenkinsConfiguredWithWebRule;
import org.junit.Rule;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class WebIdIT extends AbstractIdIT {

    @Rule
    public final JenkinsConfiguredWithWebRule r = new JenkinsConfiguredWithWebRule();

    @Override
    protected PluginConfiguration getPluginConfiguration() {
        return (PluginConfiguration) r.jenkins.getDescriptor(PluginConfiguration.class);
    }

    @Override
    protected void setId() {
        throw new NotImplementedException();
    }

    @Override
    protected void setId(String regex, String replacement) {
        throw new NotImplementedException();
    }
}

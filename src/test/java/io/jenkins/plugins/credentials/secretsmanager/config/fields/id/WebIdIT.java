package io.jenkins.plugins.credentials.secretsmanager.config.fields.id;

import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.util.JenkinsConfiguredWithWebRule;
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
            final HtmlSelect select = form.getFirstByXPath("//td[contains(string(@class),'setting-name') and text()='ID']/following-sibling::td[contains(string(@class),'setting-main')]/select[contains(string(@class),'dropdownList')]");
            select.getOptionByText("String#replaceFirst").setSelected(true);
            form.getInputByName("_.regex").setValueAttribute(regex);
            form.getInputByName("_.replacement").setValueAttribute(replacement);
        });
    }
}

package io.jenkins.plugins.credentials.secretsmanager.config;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import io.jenkins.plugins.credentials.secretsmanager.config.transformer.Default;
import io.jenkins.plugins.credentials.secretsmanager.config.transformer.Transformer;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.Serializable;

public class Fields extends AbstractDescribableImpl<Fields> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Whether to show the credential's description field or hide it. Defaults to true. */
    private Boolean description;

    /** How to present the credential's ID field. Defaults to passthrough (no transformation). */
    private Transformer id;

    @DataBoundConstructor
    public Fields(Boolean description, Transformer id) {
        this.description = description;
        this.id = id;
    }

    public Boolean getDescription() {
        return description;
    }

    @DataBoundSetter
    public void setDescription(Boolean description) {
        this.description = description;
    }

    public Transformer getId() {
        return id;
    }

    @DataBoundSetter
    public void setId(Transformer id) {
        this.id = id;
    }

    @Extension
    @Symbol("fields")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends Descriptor<Fields> {

        public Transformer getDefaultTransformer() {
            return new Default();
        }

        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.fields();
        }
    }
}

package io.jenkins.plugins.credentials.secretsmanager.config.transformer;

import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;

import java.io.Serializable;

public abstract class Transformer extends AbstractDescribableImpl<Transformer> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Transform the string using some operation.
     *
     * @param id the raw string
     * @return the transformed string
     */
    public abstract String transform(String id);

    public abstract static class DescriptorImpl extends Descriptor<Transformer> {
        /**
         * {@inheritDoc}
         */
        protected DescriptorImpl() {
            super();
        }

        /**
         * {@inheritDoc}
         */
        protected DescriptorImpl(Class<? extends Transformer> clazz) {
            super(clazz);
        }
    }
}

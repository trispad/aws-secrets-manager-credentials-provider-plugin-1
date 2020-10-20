package io.jenkins.plugins.credentials.secretsmanager.config.transformer;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.ListSecretsRequest;
import com.amazonaws.services.secretsmanager.model.ListSecretsResult;
import com.amazonaws.services.secretsmanager.model.SecretListEntry;
import hudson.Extension;
import hudson.Util;
import hudson.util.FormValidation;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import io.jenkins.plugins.credentials.secretsmanager.config.EndpointConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import jenkins.model.Jenkins;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.verb.POST;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReplaceFirst extends Transformer {

    private String regex;

    private String replacement;

    @DataBoundConstructor
    public ReplaceFirst(String regex, String replacement) {
        this.regex = regex;
        this.replacement = replacement;
    }

    public String getRegex() {
        return regex;
    }

    @DataBoundSetter
    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getReplacement() {
        return replacement;
    }

    @DataBoundSetter
    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }

    @Override
    public String transform(String id) {
        return id.replaceFirst(fixNullAndTrim(regex), fixNullAndTrim(replacement));
    }

    /**
     * Convert null to empty string, and trim whitespace.
     */
    private static String fixNullAndTrim(String s) {
        return Util.fixNull(s).trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReplaceFirst that = (ReplaceFirst) o;
        return Objects.equals(regex, that.regex) &&
                Objects.equals(replacement, that.replacement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regex, replacement);
    }

    @Extension
    @Symbol("replaceFirst")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends Transformer.DescriptorImpl {
        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.replaceFirst();
        }

        public FormValidation doCheckRegex(@QueryParameter String regex) {
            if (Util.fixEmptyAndTrim(regex) == null) {
                return FormValidation.warning("Regex should not be empty");
            }
            return FormValidation.ok();
        }

        @POST
        @SuppressWarnings("unused")
        public FormValidation doTestTransformation(
                @QueryParameter("regex") final String regex,
                @QueryParameter("replacement") final String replacement) {
            Jenkins.getInstance().checkPermission(Jenkins.ADMINISTER);

            final PluginConfiguration pluginConfiguration = PluginConfiguration.getInstance();

            final AWSSecretsManagerClientBuilder builder = AWSSecretsManagerClient.builder();

            Optional.ofNullable(pluginConfiguration.getEndpointConfiguration())
                    .map(EndpointConfiguration::build)
                    .ifPresent(builder::withEndpointConfiguration);

            final AWSSecretsManager client = builder.build();

            final ListSecretsResult result = client.listSecrets(new ListSecretsRequest());

            final Map<String, Long> transformedIds = result.getSecretList().stream()
                    .map(SecretListEntry::getName)
                    .map(name -> name.replaceFirst(regex, replacement))
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

            final boolean transformedIdsAreNotUnique = transformedIds.values().stream()
                    .anyMatch(count -> count > 1);

            if (transformedIdsAreNotUnique) {
                final StringBuilder sb = new StringBuilder();
                sb.append(Messages.transformationProducedCredentialIdsThatWereNotUnique()).append(":\n");
                transformedIds
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() > 1)
                        .forEach(entry ->
                                sb.append(Messages.duplicateCredentialId(entry.getKey(), entry.getValue())).append("\n"));
                return FormValidation.error(sb.toString());
            } else {
                return FormValidation.ok(Messages.success());
            }
        }
    }
}

package io.jenkins.plugins.credentials.secretsmanager.config.transformer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class ReplaceFirstTest {

    private static Transformer replaceFirst(String regex, String replacement) {
        return new ReplaceFirst(regex, replacement);
    }

    @Test
    public void shouldAllowNullRegex() {
        assertThat(replaceFirst(null, "").transform("foo-secret"))
                .isEqualTo("foo-secret");
    }

    @Test
    public void shouldAllowEmptyRegex() {
        assertThat(replaceFirst("", "").transform("foo-secret"))
                .isEqualTo("foo-secret");
    }

    @Test
    public void shouldTrimWhitespaceFromRegex() {
        assertThat(replaceFirst(" foo- ", "").transform("foo-secret"))
                .isEqualTo("secret");
    }

    @Test
    public void shouldAllowNullReplacement() {
        assertThat(replaceFirst("foo-", null).transform("foo-secret"))
                .isEqualTo("secret");
    }

    @Test
    public void shouldAllowEmptyReplacement() {
        assertThat(replaceFirst("foo-", "").transform("foo-secret"))
                .isEqualTo("secret");
    }

    @Test
    public void shouldTrimWhitespaceFromReplacement() {
        assertThat(replaceFirst("foo-", " bar- ").transform("foo-secret"))
                .isEqualTo("bar-secret");
    }

    @Test
    public void shouldTrimWhitespaceFromEmptyReplacement() {
        assertThat(replaceFirst("foo-", "  ").transform("foo-secret"))
                .isEqualTo("secret");
    }

    @Test
    public void shouldBeEqualWhenRegexAndReplacementAreEqual() {
        final String regex = "foo-";
        final String replacement = "";
        final Transformer a = replaceFirst(regex, replacement);

        assertSoftly(s -> {
            s.assertThat(a).as("Regex AND replacement equal").isEqualTo(replaceFirst(regex, replacement));
            s.assertThat(a).as("Regex equal").isNotEqualTo(replaceFirst(regex, null));
            s.assertThat(a).as("Replacement equal").isNotEqualTo(replaceFirst(null, replacement));
            s.assertThat(a).as("Neither equal").isNotEqualTo(replaceFirst(null, null));
        });
    }
}

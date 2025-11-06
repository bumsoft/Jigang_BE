package SDD.smash.OpenAI;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class OpenAiOutputSanitizerTest {

    @Test
    void sanitizeNullReturnsNull() {
        assertNull(OpenAiOutputSanitizer.sanitize(null));
    }

    @Test
    void sanitizeEscapesHtmlTagsAndQuotes() {
        String raw = "<script>alert('xss')</script> & \"text\"";
        String sanitized = OpenAiOutputSanitizer.sanitize(raw);

        assertEquals("&lt;script&gt;alert(&#39;xss&#39;)&lt;/script&gt; &amp; &quot;text&quot;", sanitized);
    }
}

package com.github.beresfordt.jsonpathmatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.jayway.jsonassert.JsonAssert.with;
import static java.util.Objects.requireNonNull;

/**
 * Matcher for asserting that given jsonPath passes a given Matcher
 * Example usage:
 * assertThat(jsonString, hasJsonPath("path.to.item", equalTo("itemValue")));
 *
 * @param <T> type of matcher used on the given json path
 */
public class JsonPathMatcher<T> extends TypeSafeMatcher<String> {
  private static final String NULLCHECK_TEMPLATE = "%s must not be null";

  private final String jsonPath;
  private final Matcher<T> matcher;
  private String failureDescription;

  /**
   * @param jsonPath as String
   * @param matcher  {@link Matcher} to match the value at the given jsonPath
   * @throws NullPointerException {@link NullPointerException} if arg is null
   */
  public JsonPathMatcher(String jsonPath, Matcher<T> matcher) {
    this.jsonPath = requireNonNull(jsonPath, () -> String.format(NULLCHECK_TEMPLATE, "jsonPath"));
    this.matcher = requireNonNull(matcher, () -> String.format(NULLCHECK_TEMPLATE, "matcher"));
  }

  @Override
  protected boolean matchesSafely(String json) {
    try {
      with(json).assertThat(jsonPath, matcher);
      return true;
    } catch (AssertionError | Exception e) {
      failureDescription = e.getMessage().replace('\n', ' ');
      return false;
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("JSON path [").appendText(jsonPath).appendText("] that is ").appendDescriptionOf(matcher);
  }

  @Override
  protected void describeMismatchSafely(String item, Description mismatchDescription) {
    mismatchDescription.appendText(failureDescription);
  }
}


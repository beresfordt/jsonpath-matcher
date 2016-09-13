package com.github.beresfordt.jsonpathmatcher;

import com.jayway.jsonassert.impl.JsonAsserterImpl;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static java.util.Objects.requireNonNull;

/**
 * Matcher for asserting that given jsonPath does not exist in a Json object
 * Example usage:
 * assertThat(jsonString, doesNotHaveJsonPath("path.to.item"));
 */
public class JsonPathDoesNotExistMatcher extends TypeSafeMatcher<String> {

  private static final String NULLCHECK_MESSAGE = "jsonPath must not be null";

  private final String jsonPath;

  private String failureDescription;

  /**
   * @param jsonPath as String
   * @throws NullPointerException {@link NullPointerException} if arg is null
   */
  public JsonPathDoesNotExistMatcher(String jsonPath) {
    this.jsonPath = requireNonNull(jsonPath, NULLCHECK_MESSAGE);
  }

  @Override
  protected boolean matchesSafely(String json) {
    try {
      new JsonAsserterImpl(JsonPath.parse(json).json()).assertNotDefined(jsonPath);
      return true;
    } catch (AssertionError | Exception e) {
      failureDescription = e.getMessage().replace('\n', ' ');
      return false;
    }
  }

  @Override
  public void describeTo(Description description) {
    description.appendText("JSON path [").appendText(jsonPath).appendText("] not to be present");
  }

  @Override
  protected void describeMismatchSafely(String item, Description mismatchDescription) {
    mismatchDescription.appendText(failureDescription);
  }
}

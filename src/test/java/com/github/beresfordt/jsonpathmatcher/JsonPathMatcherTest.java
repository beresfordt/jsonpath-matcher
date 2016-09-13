package com.github.beresfordt.jsonpathmatcher;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;

public class JsonPathMatcherTest extends AbstractMatcherTest {

  private static final String MATCHING_QUERY_DETAILS =
      "{\n" + "\t\"path\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"itemValue\",\n" + "\t\t\t\"notItem\": \"iCantBelieveItsNotItemValue\"\n" + "\t\t},\n" + "\t\t\"otherTo\": {\n"
          + "\t\t\t\"item\": \"alsoNotItemValue\"\n" + "\t\t}\n" + "\t},\n" + "\t\"otherPath\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"notItemValue\"\n" + "\t\t}\n" + "\t}\n" + '}';
  private static final String NON_MATCHING_QUERY_DETAILS =
      "{\n" + "\t\"path\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"badValue\",\n" + "\t\t\t\"notItem\": \"iCantBelieveItsNotItemValue\"\n" + "\t\t},\n" + "\t\t\"otherTo\": {\n"
          + "\t\t\t\"item\": \"alsoNotItemValue\"\n" + "\t\t}\n" + "\t},\n" + "\t\"otherPath\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"notItemValue\"\n" + "\t\t}\n" + "\t}\n" + '}';

  private final JsonPathMatcher<String> underTest = new JsonPathMatcher<>("path.to.item", equalTo("itemValue"));

  @Test
  public void matching() {
    assertMatches(underTest, MATCHING_QUERY_DETAILS);
    assertDescription("JSON path [path.to.item] that is \"itemValue\"", underTest);
  }

  @Test
  public void notMatching() {
    assertDoesNotMatch(underTest, NON_MATCHING_QUERY_DETAILS);
    assertMismatchDescription("JSON path [path.to.item] doesn't match. Expected: \"itemValue\" Actual: badValue", underTest, NON_MATCHING_QUERY_DETAILS);
  }

  @Test(expected = NullPointerException.class)
  public void requiresNonNullJsonPath() {
    new JsonPathMatcher<>(null, nullValue());
  }

  @Test(expected = NullPointerException.class)
  public void requiresNonNullMatcher() {
    new JsonPathMatcher<>("", null);
  }

  @Test
  public void canHandleNullObject() {
    assertDoesNotMatch(underTest, null);
  }
}
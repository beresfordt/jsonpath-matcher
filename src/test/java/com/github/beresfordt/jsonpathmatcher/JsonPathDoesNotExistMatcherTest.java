package com.github.beresfordt.jsonpathmatcher;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.nullValue;

public class JsonPathDoesNotExistMatcherTest extends AbstractMatcherTest {

  private static final String JSON_DOC_WITH_PATH_TO_ITEM =
      "{\n" + "\t\"path\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"itemValue\",\n" + "\t\t\t\"notItem\": \"iCantBelieveItsNotItemValue\"\n" + "\t\t},\n" + "\t\t\"otherTo\": {\n"
          + "\t\t\t\"item\": \"alsoNotItemValue\"\n" + "\t\t}\n" + "\t},\n" + "\t\"otherPath\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"notItemValue\"\n" + "\t\t}\n" + "\t}\n" + '}';

  private static final String JSON_DOC_WITHOUT_PATH_TO_ITEM =
      "{\n" + "\t\"notPath\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"itemValue\",\n" + "\t\t\t\"notItem\": \"iCantBelieveItsNotItemValue\"\n" + "\t\t},\n" + "\t\t\"otherTo\": {\n"
          + "\t\t\t\"item\": \"alsoNotItemValue\"\n" + "\t\t}\n" + "\t},\n" + "\t\"otherPath\": {\n" + "\t\t\"to\": {\n" + "\t\t\t\"item\": \"notItemValue\"\n" + "\t\t}\n" + "\t}\n" + '}';

  private final JsonPathDoesNotExistMatcher underTest = new JsonPathDoesNotExistMatcher("path.to.item");

  @Test
  public void matching() {
    assertMatches(underTest, JSON_DOC_WITHOUT_PATH_TO_ITEM);
    assertDescription("JSON path [path.to.item] not to be present", underTest);
  }

  @Test
  public void notMatching() {
    assertDoesNotMatch(underTest, JSON_DOC_WITH_PATH_TO_ITEM);
    assertMismatchDescription("Document contains the path <path.to.item> but was expected not to.", underTest, JSON_DOC_WITH_PATH_TO_ITEM);
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
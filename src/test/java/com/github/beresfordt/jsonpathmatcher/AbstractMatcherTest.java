package com.github.beresfordt.jsonpathmatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Assert;

public abstract class AbstractMatcherTest {

  protected static <T> void assertMatches(Matcher<T> matcher, T arg) {
    assertMatches("Expected match, but mismatched", matcher, arg);
  }

  private static <T> void assertMatches(String message, Matcher<T> matcher, T arg) {
    if (!matcher.matches(arg)) {
      Assert.fail(message + " because: '" + mismatchDescription(matcher, arg) + '\'');
    }
  }

  protected static <T> void assertDoesNotMatch(Matcher<? super T> c, T arg) {
    assertDoesNotMatch("Unexpected match", c, arg);
  }

  private static <T> void assertDoesNotMatch(String message, Matcher<? super T> c, T arg) {
    Assert.assertFalse(message, c.matches(arg));
  }

  protected static void assertDescription(String expected, Matcher<?> matcher) {
    Description description = new StringDescription();
    description.appendDescriptionOf(matcher);
    Assert.assertEquals("Expected description", expected, description.toString().trim());
  }

  protected static <T> void assertMismatchDescription(String expected, Matcher<? super T> matcher, T arg) {
    Assert.assertFalse("Precondition: Matcher should not match item.", matcher.matches(arg));
    Assert.assertEquals("Expected mismatch description", expected, mismatchDescription(matcher, arg));
  }

  private static <T> String mismatchDescription(Matcher<? super T> matcher, T arg) {
    Description description = new StringDescription();
    matcher.describeMismatch(arg, description);
    return description.toString().trim();
  }
}


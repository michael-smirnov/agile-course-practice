package ru.unn.NewtonMethod.viewModel;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class NewtonMethodRegexMatcher extends TypeSafeMatcher {
    private final String regex;

    public NewtonMethodRegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    protected boolean matchesSafely(final Object item) {
        return item.toString().matches(regex);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Matches regular expression=`" + regex + "`");
    }

    public static NewtonMethodRegexMatcher matches(final String regex) {
        return new NewtonMethodRegexMatcher(regex);
    }
}

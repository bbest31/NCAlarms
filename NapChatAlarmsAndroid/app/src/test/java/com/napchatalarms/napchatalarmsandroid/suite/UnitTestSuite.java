package com.napchatalarms.napchatalarmsandroid.suite;

import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilderTest;
import com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilderTest;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctionsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite class for all Unit Tests.
 * Created by bbest on 11/02/18.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({OneTimeBuilderTest.class,
        RepeatingBuilderTest.class,
        UtilityFunctionsTest.class})
public class UnitTestSuite {
}

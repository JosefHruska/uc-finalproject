package com.example.android.studyproject.data;

import android.test.AndroidTestCase;

/**
 * Created by Pepa on 17.03.2016.
 */
public class PracticeTest extends AndroidTestCase {

@Override
    protected void setUp() throws Exception {
    super.setUp();
}
    public void testThatMakesNoSense() throws  Throwable {
        int a = 5;
        int b = 5;

        assertEquals("It should be equal",a,b);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}

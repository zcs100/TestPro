package com.test;

import org.junit.Test;

/**
 * Created by szc on 17/12/28.
 */
public class TestClass {

    @Test
    public void testClassLoder(){
        Temp temp = new Temp();
        Temp temp1 = new Temp();
        System.out.println(temp1.temp == temp.temp);
    }
}

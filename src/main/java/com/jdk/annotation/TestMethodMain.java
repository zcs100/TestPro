package com.jdk.annotation;

/**
 * Created by szc on 17/10/11.
 */
public class TestMethodMain {

    @TestMethod("1221211")
    public void print(){
    }

    public static void main(String[] args) throws Exception {
        TestMethod testMethod = TestMethodMain.class.getDeclaredMethod("print",null).getAnnotation(TestMethod.class);
        System.out.println(testMethod.value());
    }
}

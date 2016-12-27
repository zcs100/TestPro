package com.jdk;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by szc on 16/11/29.
 */
public class UsedClassTest {

    @Test
    public void hashMapTest(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("1","first");
        map.put("2","two");
        System.out.println(map.get("1"));
    }

}

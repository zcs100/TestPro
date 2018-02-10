package com.algorithm.demo;

import java.util.Stack;

/**
 * Created by szc on 17/10/25.
 */
public class StackApply {

    public static void main(String[] args) {
        String symbols = "[(){{}]";
        System.out.println(StackApply.judgeSymbol(symbols));
    }

   public static boolean judgeSymbol(String symbols){
       Stack<String> stack = new Stack();
       char[] characters = symbols.toCharArray();
       for(char c : characters){
           if(c == '{' || c == '(' || c == '['){
               stack.push(String.valueOf(c));
           }else{
               if(stack.empty()){
                   return false;
               }
               String temp = stack.pop();
               if((temp.equals("(") && ")".equals(String.valueOf(c))) || (temp.equals("{") && "}".equals(String.valueOf(c)))
                       || (temp.equals("[") && "]".equals(String.valueOf(c)))){
                   continue;
               }
               return false;
           }
       }
       return true;
   }

    

}

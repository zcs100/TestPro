package com.algorithm.demo;

/**
 * Created by szc on 17/10/25.
 *
 */
public class SimpleAlgorithm {

    public static void main(String[] args) {
        //=====判断回文
        System.out.println(SimpleAlgorithm.isBackNum(761167));
        //=====字符串反转
        //System.out.println(SimpleAlgorithm.reverseStr("12"));
        //=====整数反转
        //System.out.println(SimpleAlgorithm.reverseInt(12));
        //=====左旋
        //System.out.println(SimpleAlgorithm.rotate("abcdefghi",3));
    }

    /**
     * 判断回文数
     * @param num
     * @return
     */
    public static boolean isBackNum(int num){
        if(num < 0){
            return false;
        }
        int numCount = 1;
        while(num/numCount >= 10){
            numCount = numCount*10;
        }
        while(num != 0){
            int reminder = num%10;  //余数
            int result = num/numCount; //商
            if(reminder == result){
                num = (num-result*numCount)/10;
                numCount = numCount/100;
            }else {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串反转
     * @param source
     * @return
     */
    public static String reverseStr(String source){
        char[] chars = source.toCharArray();
        int begin = 0;
        int end = chars.length-1;
        while(begin < end){
            char temp = chars[begin];
            chars[begin] = chars[end];
            chars[end] = temp;
            begin++;
            end--;
        }
        return new String(chars);
    }

    /**
     * 整数反转
     * @param source
     * @return
     */
    public static int reverseInt(int source){
        int result = 0;
        while (source != 0){
            int reminder = source%10;
            source = source/10;
            result = result*10+reminder;
        }
        return result;
    }

    /**
     * 左旋
     * 例如  abcdefghi   左旋后   defghiabc
     * 思路: 1. 把2个串分别反转,拼成一个字符后在进行反转
     *       2. 移位操作
     * @param str
     * @return     
     */
    public static String rotate(String str,int n){
        String leftRevert = reverseStr(str.substring(0,n));
        String rightRevert = reverseStr(str.substring(n,str.length()));
        return reverseStr(leftRevert+rightRevert);
    }



}

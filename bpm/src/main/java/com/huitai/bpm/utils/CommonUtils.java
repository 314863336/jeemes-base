package com.huitai.bpm.utils;

/**
 * @author PLF <br>
 * @version 1.0 <br>
 * @description: CommonUtils <br>
 * @date 2021-01-07 12:05 <br>
 */
public class CommonUtils {
    /**
     * @description: 将首字母小写 <br>
     * @param:  <br>
     * @return:  <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2021-01-07 12:06 <br>
     */
    public static String lowerFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
    /**
     * @description: 将首字母大写方法 <br>
     * @param:  <br>
     * @return:  <br>
     * @exception:  <br>
     * @author: PLF <br>
     * @date: 2021-01-07 12:06 <br>
     */
    public static String upperFirstChar(String str) {
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        if(chars[0]>97){

        }
        return String.valueOf(chars);
    }

}

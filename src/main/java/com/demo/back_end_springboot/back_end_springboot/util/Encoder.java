package com.demo.back_end_springboot.back_end_springboot.util;

import org.apache.commons.math3.random.RandomDataGenerator;

public class Encoder {

    private static final String CHARS_SOURCE = "qwq{Pd]qws!wqd[qw1213!qw132dqw35A44sq211p[2k@12{]as{ll21{}a1$#@";
    private static final char[] CHARS_SOURCE_ARRAY = CHARS_SOURCE.toCharArray();
    private static final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

    public static String encode(String str, String key) {
        char[] chars = str.toCharArray();
        char[] keys = key.toCharArray();
        chars = replaceChars(chars, keys);
        StringBuffer stringBuffer = new StringBuffer();
        for (char c : chars) {
            char cFromFinalChars = getRandomChar();
            stringBuffer.append(cFromFinalChars);
            String randomString = generateStringByLength(cFromFinalChars);
            stringBuffer.append(randomString);

            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public static String decode(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean flag = true;
        while (flag) {
            char[] chars = str.toCharArray();
            char realChar = chars[chars[0] + 1];
            stringBuffer.append(realChar);
            if (chars.length > chars[0] + 2) {
                str = str.substring(chars[0] + 2);
            } else {
                flag = false;
            }
        }

        return stringBuffer.toString();
    }

    private static String generateStringByLength(int length) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            char cFromFinalChars = getRandomChar();
            stringBuffer.append(cFromFinalChars);
        }
        return stringBuffer.toString();
    }

    private static char getRandomChar() {
        int index = randomDataGenerator.nextInt(0, CHARS_SOURCE_ARRAY.length - 1);
        return CHARS_SOURCE_ARRAY[index];
    }

    private static char[] replaceChars(char[] chars, char[] keys) {
        char[] rtn = new char[chars.length];
        for (int i = 0; i < rtn.length; i++) {
            int j = i;
            while (j > keys.length - 1) {
                j = j - keys.length;
            }
            rtn[i] = keys[j];
        }
        return rtn;
    }
}

package cn.chiu.haveatry.leetcode.longest.palindromic.substring;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by yeachiu
 * @date 2021/08/08 22:12
 * @desc 找到字符串 s 中最长的回文子串
 */
public class Solution {
    public String longestPalindrome(String s) {
        int size = s.length();
        int start = 0;
        int lastIdx = size, point = 0;
        char[] charArr = s.toCharArray();
        String nowStr = "", regStr = "";
        List<String> results = new ArrayList<>();

        while (start < lastIdx) {
            if(!match(nowStr, regStr, point, lastIdx, charArr, results)) {
                lastIdx = size;
                start ++;
            }
        }
        return results.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList()).get(0);
    }

    private boolean match(String nowStr, String regStr, int point, int lastIdx, char[] charArr, List<String> results) {
        if(lastIdx - point <= 0) {
            return false;
        }
        nowStr += String.valueOf(charArr[++point]);
        regStr += String.valueOf(charArr[--lastIdx]);
        if (nowStr.equals(regStr)) {
            // 部分匹配，先记录，再探寻下一个字符
            results.add(nowStr);
            if (point < lastIdx) {
                match(nowStr, regStr, point, lastIdx, charArr, results);
            } else {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        new Solution().longestPalindrome("babad");
    }
}

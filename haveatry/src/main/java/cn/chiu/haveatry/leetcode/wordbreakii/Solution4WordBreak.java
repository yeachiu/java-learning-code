package cn.chiu.haveatry.leetcode.wordbreakii;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by Raza
 * @classname Solution4WordBreak
 * @description 给定一个非空字符串 s 和一个包含非空单词列表的字典 wordDict，
 * 在字符串中增加空格来构建一个句子，使得句子中所有的单词都在词典中。
 * 返回所有这些可能的句子。
 * <p>
 * 说明：
 * <p>
 * 分隔时可以重复使用字典中的单词。
 * 你可以假设字典中没有重复的单词。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/word-break-ii
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @date 2020/11/8 0008 16:35
 */
public class Solution4WordBreak {

    private List<String> wordDict;

    private List<String> sentences;

    public List<String> wordBreak(String stringToBuild, List<String> wordDict) {
        sentences = new ArrayList<>();
        this.wordDict = wordDict;
        if (!preHandle(stringToBuild)) {
            return Collections.EMPTY_LIST;
        }
        buildSentencePass(stringToBuild, null);
        return sentences;
    }

    private boolean preHandle(String stringToBuild) {
        List<String> distinctChar = wordDict.stream().map((word) -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        System.out.println();
        for (char c : stringToBuild.toCharArray()) {
            if (!distinctChar.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        Solution4WordBreak wordBreak = new Solution4WordBreak();
        List<String> dict;
        /*
            用例1
         */
        dict = Arrays.asList(new String[]{"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"});
        wordBreak.wordBreak(
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                dict)
                .forEach(sen -> System.out.println(sen));
        System.out.println("=======>finish");

        /*
            用例2
         */
        dict = Arrays.asList(new String[]{"cat", "cats", "and", "sand", "dog"});
        wordBreak.wordBreak(
                "catsanddog",
                dict)
                .forEach(sen -> System.out.println(sen));
        System.out.println("=======>finish");

        /*
            用例3
         */
        dict = Arrays.asList(new String[]{"cats", "dog", "sand", "and", "cat"});
        wordBreak.wordBreak(
                "catsandog",
                dict)
                .forEach(sen -> System.out.println(sen));
        System.out.println("=======>finish");


        /**
         * 用例4：内存溢出
         */
//        dict = Arrays.asList(new String[]{"a", "aa", "aaa", "aaaa", "aaaaa", "aaaaaa", "aaaaaaa", "aaaaaaaa", "aaaaaaaaa", "aaaaaaaaaa"});
//        wordBreak.wordBreak(
//                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
//                dict)
//                .forEach(sen -> System.out.println(sen));
//        System.out.println("=======>finish");

        System.out.println("end");

    }


    /**
     * [回溯匹配|DFS] 单向匹配
     *
     * @param stringToBuild string to build
     * @param sentence sentence
     */
    private void buildSentencePass(String stringToBuild, StringBuilder sentence) {
        String matchWord;
        String letter;
        String newBuildStr;
        StringBuilder newSentence;

        for (String s : wordDict) {
            matchWord = s;
            if (stringToBuild.length() < matchWord.length()) {
                continue;
            }
            letter = stringToBuild.substring(0, matchWord.length());
            if (matchWord.equals(letter)) {
                if (null == sentence) {
                    sentence = new StringBuilder();
                }
                newSentence = new StringBuilder(sentence);
                newSentence.append(letter);
                newBuildStr = stringToBuild.substring(matchWord.length());
                if (newBuildStr.length() == 0) {
                    sentences.add(newSentence.toString());
                } else {
                    newSentence.append(" ");
                    buildSentencePass(newBuildStr, newSentence);
                }
            }
        }
    }

    /**
     * 我以为我赢了，结果超时了……
     *
     * @param stringToBuild
     * @param sentence
     */
    private void buildSentenceFirst(String stringToBuild, StringBuilder sentence) {
        for (int i = 1; i < stringToBuild.length() + 1; i++) {
            String letter = stringToBuild.substring(0, i);
            if (wordDict.contains(letter)) {
                if (null == sentence) {
                    sentence = new StringBuilder();
                }
                StringBuilder newSentence = new StringBuilder(sentence);
                newSentence.append(letter);
                String newBuildStr = stringToBuild.substring(i, stringToBuild.length());
                if (i == stringToBuild.length()) {
                    sentences.add(newSentence.toString());
                } else {
                    newSentence.append(" ");
                    buildSentenceFirst(newBuildStr, newSentence);
                }
            }
        }
    }
}

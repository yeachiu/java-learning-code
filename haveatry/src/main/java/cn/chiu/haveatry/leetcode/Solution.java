package cn.chiu.haveatry.leetcode;

/**
 * @author by yeachiu
 * @classname Solution
 * @description 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 *
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii
 * @date 2020/11/8 0008 14:58
 */
public class Solution {
    int maxProfit = 0;
    public static int getMaxProfit(int[] prices) {
        int endIndex = prices.length - 1;
        int chae = prices[endIndex] - prices[0];
        for(int i = 0; i < endIndex; i++) {

        }
        return 0;
    }

    //交易方案
    //得出最优交易方案
    public static void main(String[] args) {

    }

    /**
     * 动态规划
     * 不能同时参与多笔交易，所以每天交易结束只有一种状态：持有和没有持有股票
     * 我们使用dp[i][0], 表示第i天交易结束有没有持有股票的最大利润，dp[i][1], 表示第i天交易结束持有股票的最大利润；
     * 第一种dp[i][0],说明第i天没有持股，此时有两种可能，第i-1没有持股，然后今天也没有交易，此时利润为dp[i-1][0]
     * 或者第i-1天持有股票，在今天卖出，此时利润为dp[i-1][1] +
     * @param prices
     * @return
     */
    public int solution1(int[] prices) {
        return 0;
    }
}

package cn.chiu.haveatry.javaCore.java8.stream;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 一句话功能描述<br>
 *
 * @author yeachiu
 * @version 2020/09/02
 */
public class TestGroupAndCollection {
    public static void main(String[] args) {
        testGroupByCollection();
    }

    /**
     * 对一个交易列表按货币分组，获得该货币的所有交易额总和（返回一个Map<Currency,
     * Integer>）
     */
    public static void testGroupByCollection() {
        Order order1 = new Order("CNY", 123);
        Order order2 = new Order("USD", 234);
        Order order3 = new Order("CNY", 456);
        Order order4 = new Order("USD", 327);
        Order order5 = new Order("CNY", 37);
        List<Order> orderList = Lists.newArrayList(order1, order2, order3, order4, order5);

        Map<String, List<Order>> orderGroupByCurrency = orderList.stream().collect(Collectors.groupingBy(Order::getCurrency));
        Map<String, Integer> amountGroupByCurrency = new HashMap<>();
        orderGroupByCurrency.forEach((currency, list) ->
                amountGroupByCurrency.put(currency, list.stream().mapToInt(Order::getAmount).sum())
        );
        System.out.println("对一个交易列表按货币分组，获得该货币的所有交易额总和结果：");
        System.out.println(amountGroupByCurrency.toString());

        /*

         */
    }

    static class Order {
        private String currency;
        private int amount;

        public Order(String currency, int amount) {
            this.currency = currency;
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
    }
}

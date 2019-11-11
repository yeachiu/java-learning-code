package cn.chiu.UtilsTest;

import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jlqiu
 * @since 2019/9/25 11:19
 */
public class TestArray2Str {

    public static void main(String[] args) {

//        String[] strArr = {"id1","id2","id3"};
        List<String> atrArr = new ArrayList<>();
        atrArr.add("id1");
        atrArr.add("id2");
        atrArr.add("id3");

        String customerIdsStr = "|" + atrArr.stream()
                .collect(Collectors.joining("|")) + "|";

        System.out.println(customerIdsStr);

    }
}

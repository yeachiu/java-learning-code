package cn.chiu.haveatry.javaUtil;

/**
 * <p>
 * 路径通配符匹配,支持前后、中间匹配
 * </p>
 *
 * @author Jolene
 * @date 2023/11/23
 */
public class PathMatcher {

    public static boolean isPathMatch(String pattern, String path) {
        // 如果两个路径都为空，返回true
        if (pattern == null && path == null) {
            return true;
        }
        // 如果两个路径有一个为空，返回false
        if (pattern == null || path == null) {
            return false;
        }
        // 将两个路径按/分割成字符串数组
        String[] patternSegments = pattern.split("/");
        String[] pathSegments = path.split("/");
        // 定义两个指针，分别指向两个数组的当前位置
        int i = 0;
        int j = 0;
        // 循环遍历两个数组，直到有一个到达末尾
        while (i < patternSegments.length && j < pathSegments.length) {
            // 跳过空路径
            if (patternSegments[i].isEmpty()) {
                i++;
            }
            if (pathSegments[j].isEmpty()) {
                j++;
            }
            // 如果两个数组的当前元素相等，或者有一个是*，则继续比较下一个元素
            if (patternSegments[i].equals(pathSegments[j]) || "*".equals(patternSegments[i])) {
                i++;
                j++;
            } else {
                // 如果忽略路径的中间部分，尝试跳过不匹配的部分
                // 如果路径1的当前元素是*，则跳过路径2的所有元素，直到找到一个与路径1的下一个元素相等的元素
                if ("*".equals(patternSegments[i])) {
                    i++;
                    while (j < pathSegments.length && !pathSegments[j].equals(pathSegments[i])) {
                        j++;
                    }
                }
                // 否则，返回false
                else {
                    return false;
                }
            }
        }
        // 如果两个数组都到达末尾，返回true
        return true;
    }

    public static void main(String[] args) {
        boolean leftMatch = isPathMatch("*/portal", "/ddAuth/portal");
        boolean middleMatch = isPathMatch("/ddAuth/*/test", "/ddAuth/portal/test");
        boolean rightMatch = isPathMatch("/portal/*", "/portal/login");
        System.out.println(leftMatch);
        System.out.println(middleMatch);
        System.out.println(rightMatch);
    }
}

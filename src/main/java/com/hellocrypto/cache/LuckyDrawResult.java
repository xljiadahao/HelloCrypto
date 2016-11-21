package com.hellocrypto.cache;

import java.util.List;

/**
 *
 * @author leixu2
 */
public class LuckyDrawResult {

    private static List<String> luckDrawResults = null;

    public static List<String> getLuckDrawResults() {
        return luckDrawResults;
    }

    public static void setLuckDrawResults(List<String> luckDrawResults) {
        LuckyDrawResult.luckDrawResults = luckDrawResults;
    }

}

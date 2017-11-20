package com.hellocrypto.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author xulei
 */
public class LuckyDrawResult {

    private static final Logger logger = Logger.getLogger(LuckyDrawResult.class);
    
    private static Map<String, List<String>> luckDrawResults = new HashMap<String, List<String>>();

    public static List<String> getLuckDrawResults(String groupIdentifier) {
        return luckDrawResults.get(groupIdentifier);
    }
    
    public static void setDrawResult(String groupIdentifier, List<String> list) {
        luckDrawResults.put(groupIdentifier, list);
    }

    public static boolean cleanLuckDrawResults(String groupIdentifier) {
        List<String> cleanList = luckDrawResults.remove(groupIdentifier);
        if (cleanList == null) {
            logger.warn("cannot find the result for group identifier: " + groupIdentifier);
            return false;
        } else {
            logger.info("cleanLuckDrawResults, group id: " + groupIdentifier + ", result size: " + cleanList.size());
            return true;
        }
    }

}

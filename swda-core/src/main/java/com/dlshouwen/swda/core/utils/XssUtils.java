package com.dlshouwen.swda.core.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

/**
 * xss utils
 * @author liujingcheng@live.cn
 * @since 1.0.0
 */
public class XssUtils {

    /** output settings */
    private final static Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);

    /**
     * filter
     * @param content
     * @return cleaned content
     */
    public static String filter(String content) {
//    	clean content
        return Jsoup.clean(content, "", Safelist.relaxed(), outputSettings);
    }

}
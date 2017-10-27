package com.yys.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xyr on 2017/10/24.
 */
public class RichTextImgUtil {

    /**
     * 将text文本中的img标签的src属性值进行替换
     * @param text
     * @param uri
     * @return
     */
    public static String imgConvert(String text, String uri) {
        Pattern pattern = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        String quote = null;
        String src = null;
        while (matcher.find()) {
            quote = matcher.group(1);

            src = (quote == null || quote.trim().length() == 0) ? matcher.group(2).split("\\s+")[0] : matcher.group(2);
            //System.out.println(src);
            if (src != null && src.startsWith("../")) {
                src = src.substring(src.lastIndexOf("../") + 3, src.length());
                src = src.substring(src.indexOf("images/") + 7, src.length());
                //替换后的属性值
                src = uri + src;
                Pattern srcPattern = Pattern.compile("src\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)", Pattern.CASE_INSENSITIVE);
                Matcher srcMatch = srcPattern.matcher(matcher.group());
                while (srcMatch.find()) {
                    StringBuilder replace = new StringBuilder("src=\"").append(src);
                    String imgTag = srcMatch.replaceAll(replace.toString());
                    String result = matcher.replaceAll(imgTag);
                    return result;
                }
            }
        }

        return text;
    }

    public static String getImgPath(String text) {
        Pattern pattern = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        String quote = null;
        String src = null;
        while (matcher.find()) {
            quote = matcher.group(1);

            src = (quote == null || quote.trim().length() == 0) ? matcher.group(2).split("\\s+")[0] : matcher.group(2);
            //System.out.println(src);
            if (src != null) {
                src = src.substring(src.indexOf("images/") + 7, src.length());
                return src;
            }
        }

        return null;
    }

}

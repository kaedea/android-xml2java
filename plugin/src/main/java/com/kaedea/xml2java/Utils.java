package com.kaedea.xml2java;

import org.jetbrains.annotations.NotNull;

/**
 * @author Kaede.
 * @version 2018/8/28.
 */
class Utils {

    public static String convert(@NotNull String xml) {
        final String[] java = {null};
        try {
            Xml2Java.INSTANCE.convert(xml, codes -> {
                java[0] = codes;
                return null;
            });
        } catch (Exception e) {
            java[0] = e.toString();
        } finally {
            Utils.class.notifyAll();
        }
        synchronized (Utils.class) {
            try {
                while (java[0] == null) {
                    Utils.class.wait();
                }
            } catch (InterruptedException ignored) {
            }
        }
        return java[0];
    }
}

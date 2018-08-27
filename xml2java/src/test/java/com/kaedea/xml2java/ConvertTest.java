package com.kaedea.xml2java;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

/**
 * @author Kaede.
 * @version 2018/8/27.
 */
@RunWith(JUnit4.class)
public class ConvertTest {

    @Test
    public void xmlWithFile() {
        File xmlFile = new File(ConvertTest.class.getClassLoader().getResource("file/activity_main.xml").getFile());
        final String[] java = {null};

        Xml2Java.convert(xmlFile, codes -> {
            java[0] = codes;
        });

        Assert.assertNotNull(java);
    }

    @Test
    public void xmlWithString() {
        final String[] java = {null};

        Xml2Java.convert(
                "<ImageView\n" +
                "android:id=\"@+id/iv\"\n" +
                "android:layout_width=\"0dp\"\n" +
                "android:layout_height=\"0dp\"\n" +
                "android:scaleType=\"centerCrop\"\n" +
                "android:src=\"@drawable/head\"\n" +
                "app:layout_constraintDimensionRatio=\"H,1024:539\"\n" +
                "app:layout_constraintLeft_toLeftOf=\"parent\"\n" +
                "app:layout_constraintRight_toRightOf=\"parent\"\n" +
                "app:layout_constraintTop_toTopOf=\"parent\" />", codes -> {
            java[0] = codes;
        });

        Assert.assertNotNull(java);
    }
}

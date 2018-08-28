package com.kaedea.xml2java

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import java.io.File

/**
 * @author Kaede.
 * *
 * @version 2018/8/27.
 */
@RunWith(JUnit4::class)
class ConvertTest {

    @Test
    fun xmlWithFile() {
        val xmlFile = File(ConvertTest::class.java.classLoader.getResource("file/activity_main.xml")!!.file)
        var java: String? = null
        Xml2Java.convert(xmlFile) { codes ->
            java = codes
        }
        Assert.assertNotNull(java)
    }

    @Test
    fun xmlWithString() {
        var java: String? = null
        Xml2Java.convert(
                """
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraintLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/iv"
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:scaleType="centerCrop"
        android:src="@drawable/head"
        app:layout_constraintDimensionRatio="H,1024:539"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/xml"
        android:text="LEFT"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintTop_toBottomOf="@id/iv" />

    <TextView
        android:id="@+id/x2c"
        android:background="@color/colorPrimary"
        android:text="RIGHT"
        app:layout_constraintLeft_toRightOf="@id/xml"
        app:layout_constraintTop_toTopOf="@id/xml" />

</android.support.constraint.ConstraintLayout>""") { codes ->
            java = codes
        }
        Assert.assertNotNull(java)
    }
}

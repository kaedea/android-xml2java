package com.kaedea.xml2java

import com.zhangyue.we.view.View
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.ByteArrayInputStream
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*
import javax.xml.parsers.SAXParserFactory

/**
 * @author Kaede.
 * *
 * @version 2018/8/27.
 */


object Xml2Java {
    fun convert(xmlFile: File, callback: (String) -> Unit) {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(xmlFile, XmlHandler(callback))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun convert(xml: String, callback: (String) -> Unit) {
        try {
            val inputStream = ByteArrayInputStream(xml.toByteArray(StandardCharsets.UTF_8))
            SAXParserFactory.newInstance().newSAXParser().parse(inputStream, XmlHandler(callback))
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }

    private class XmlHandler constructor(private val callback: (String) -> Unit) : DefaultHandler() {
        private lateinit var mStack: Stack<View>
        private var mRootView: View? = null

        @Throws(SAXException::class)
        override fun startDocument() {
            super.startDocument()
            mStack = Stack<View>()
        }

        @Throws(SAXException::class)
        override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
            val view = createView(qName, attributes)

            if (mStack.size > 0) {
                view.setParent(mStack[mStack.size - 1])
            } else {
                view.setParent(null)
            }
            mStack.push(view)
            super.startElement(uri, localName, qName, attributes)
        }

        @Throws(SAXException::class)
        override fun endElement(uri: String?, localName: String?, qName: String?) {
            super.endElement(uri, localName, qName)

            val view = mStack.pop()
            if (mStack.size == 0) {
                mRootView = view
                val stringBuffer = StringBuffer()
                mRootView!!.translate(stringBuffer)
                stringBuffer.append("return ").append(mRootView!!.objName)

                callback.invoke(stringBuffer.toString())
                return
            }
            callback.invoke("""n\a""")
        }

        @Throws(SAXException::class)
        override fun endDocument() {
            super.endDocument()
        }
    }

    private fun createView(name: String?, attributes: Attributes?): View {
        return View("", name, attributes)
    }
}

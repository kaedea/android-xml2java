package com.kaedea.xml2java;

import com.zhangyue.we.view.View;
import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Stack;

/**
 * @author Kaede.
 * @version 2018/8/27.
 */
public class Xml2Java {
    public static void convert(@NotNull File xmlFile, Callback callback) {
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(xmlFile, new XmlHandler(callback));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void convert(@NotNull String xml, Callback callback) {
        try {
            InputStream is = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            SAXParserFactory.newInstance().newSAXParser().parse(is, new XmlHandler(callback));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Callback {
        void onFinished(String codes);
    }

    private static class XmlHandler extends DefaultHandler {
        private final Callback mCallback;
        private Stack<View> mStack;
        private View mRootView;

        private XmlHandler(Callback callback) {
            mCallback = callback;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            mStack = new Stack<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            View view = createView(qName, attributes);
            if (mStack.size() > 0) {
                view.setParent(mStack.get(mStack.size() - 1));
            } else {
                view.setParent(null);
            }
            mStack.push(view);
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);

            View view = mStack.pop();
            if (mStack.size() == 0) {
                mRootView = view;
                StringBuffer stringBuffer = new StringBuffer();
                mRootView.translate(stringBuffer);
                stringBuffer.append("return ").append(mRootView.getObjName());
                mCallback.onFinished(stringBuffer.toString());
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }

    private static View createView(String name, Attributes attributes) {
        return new View("", name, attributes);
    }
}

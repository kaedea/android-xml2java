package com.kaedea.xml2java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @author Kaede
 * @version 16/12/11
 */
public class Xml2JavaAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        alert("Hello, xml2java");
    }

    private void alert(String msg) {
        Messages.showMessageDialog(
                msg,
                "XML2JAVA",
                Messages.getInformationIcon()
        );
    }
}

package com.kaedea.xml2java;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.apache.http.util.TextUtils;

/**
 * @author Kaede
 * @version 16/12/11
 */
public class Xml2JavaAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // Get all the required data from data keys
        final Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        final Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        // Access document, caret, and selection
        final Document document = editor.getDocument();
        final SelectionModel model = editor.getSelectionModel();

        String text = model.getSelectedText();
        if (TextUtils.isEmpty(text)) {
            alert("Empty selected text.");
            return;
        }

        showTextForm(text);
    }

    private void showTextForm(String text) {
        new TextForm(text).show();
    }

    private void alert(String msg) {
        Messages.showMessageDialog(
                msg,
                "XML2JAVA",
                Messages.getInformationIcon()
        );
    }
}

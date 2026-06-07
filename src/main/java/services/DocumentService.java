package services;

import org.bson.Document;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class DocumentService {
    public String convertToRTF(JTextPane textPane) throws Exception{
        RTFEditorKit kit=new RTFEditorKit();
        StyledDocument doc=textPane.getStyledDocument();

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        kit.write(out,doc,0,doc.getLength());

        return out.toString("UTF-8");
    }
    public void loadFromRTF(JTextPane textPane,String rtf) throws Exception{
        RTFEditorKit kit=new RTFEditorKit();
        StyledDocument doc=textPane.getStyledDocument();
        doc.remove(0,doc.getLength());
        ByteArrayInputStream in = new ByteArrayInputStream(rtf.getBytes("UTF-8"));
        kit.read(in,doc,0);
    }
    public void downloadFile(Document document) throws Exception {
        UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
        );
        String rtfContent = document.getString("Content");
        String title = document.getString("Title");
        String dateAndTime = document.getString("Created At");

        dateAndTime = dateAndTime.replace("/", "-")
                .replace(":", "-")
                .replace(" ", "_");

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save File");
        chooser.setSelectedFile(
                new File(title + "_" + dateAndTime + ".doc")
        );
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();
            if (!file.getName().endsWith(".doc")) {
                file = new File(file.getAbsolutePath() + ".doc");
            }
            FileOutputStream out = new FileOutputStream(file);
            out.write(rtfContent.getBytes("UTF-8"));
            out.close();
        }
    }
}

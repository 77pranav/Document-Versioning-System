package formatting;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import ui_main.TextSection;

public class TextFormat {
    private TextSection textSection;
    public TextFormat(TextSection textSection){
        this.textSection=textSection;
    }
    public void setFontFamilyAtCaret(String fontFamily){
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs,fontFamily);
        textSection.getTextArea().setCharacterAttributes(attrs,false);
    }
    public void setFontFamilyForSelection(String fontFamily){
        int start=textSection.getTextArea().getSelectionStart();
        int end=textSection.getTextArea().getSelectionEnd();
        if(start==end) return;
       
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setFontFamily(attrs,fontFamily);
       
        StyledDocument doc=textSection.getTextArea().getStyledDocument();
        doc.setCharacterAttributes(start,end-start,attrs,false);
    }
    public void setFontSizeAtCaret(int fontSize){
        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs,fontSize);
        textSection.getTextArea().setCharacterAttributes(attrs,false);
    }
    public void setFontSizeForSelection(int fontSize){
        int start=textSection.getTextArea().getSelectionStart();
        int end=textSection.getTextArea().getSelectionEnd();
        if(start==end) return;

        SimpleAttributeSet attrs=new SimpleAttributeSet();
        StyleConstants.setFontSize(attrs,fontSize);

        StyledDocument doc=textSection.getTextArea().getStyledDocument();
        doc.setCharacterAttributes(start,end-start,attrs,false);
    }
}

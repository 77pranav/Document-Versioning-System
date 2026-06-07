package ui_main;

import javax.swing.*;
import java.awt.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextSection extends JPanel{
    private JTextPane textArea;
    private JScrollPane scrollPane;
    public TextSection(){
        setPreferredSize(new Dimension(400, 0));
        setOpaque(true);
        setBackground(new Color(20,80,100));
        initializeAndModify();
        addComponents();
    }
    public void initializeAndModify(){
        textArea = new JTextPane();
        SimpleAttributeSet attrs=new SimpleAttributeSet();
//        new TextFormat().getInitFontAndSize();
        StyleConstants.setFontFamily(attrs,"Arial");
        StyleConstants.setFontSize(attrs,12);
        textArea.setCharacterAttributes(attrs,false);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }
    public void addComponents(){
        setLayout(new BorderLayout());
        add(scrollPane,BorderLayout.CENTER);
    }
    public JTextPane getTextArea(){ return textArea; }
}

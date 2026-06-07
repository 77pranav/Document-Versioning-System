package ui_main;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

import database.Mdb;
import formatting.TextFormat;
import ui_dropdowns.SaveDropDown;
import ui_dropdowns.OpenDropDown;
import services.NewService;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


public class HomeRibbon extends JPanel {

    private JButton btnNew;
    private JButton btnOpen;
    private JButton btnSave;
    private Color ribbonColor;
    private Color sepColor;
    private Color eleBgColor;
    private Color textColor;
    private JPanel sep1;
    private JPanel sep2;
    private JPanel sep3;
    private JPanel sep4;
    private JPanel sep5;
    private JComboBox<String> fontFamilies;
    private String[] fonts;
    private JPanel sizePanel;
    private JButton btnIncFont;
    private JButton btnDecFont;
    private JTextField fontSizeField;
    private Mdb mdb;
    private TextFormat textFormat;
    private TextSection textSection;
    private FileNameRibbon fileNameRibbon;
    private VersionSection versionSection;
    private NewService newService;
    private OpenDropDown openDropDown;
    private SaveDropDown saveDropDown;

    public HomeRibbon(TextFormat textFormat,TextSection textSection,FileNameRibbon fileNameRibbon,VersionSection versionSection,Mdb mdb) {
        this.mdb=mdb;
        this.textFormat=textFormat;
        this.textSection=textSection;
        this.fileNameRibbon=fileNameRibbon;
        this.versionSection=versionSection;
        initializeComponents();
        setLayout();
        setOpaque(true);
        setBackground(ribbonColor);
        setPreferredSize(new Dimension(0, 40));
    }

    public void initializeComponents() {
        ribbonColor = new Color(170, 179, 220);
        sepColor = new Color(35, 97, 159);
        eleBgColor = new Color(67, 90, 189);
        textColor = Color.WHITE;
        fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        newService=new NewService();
        openDropDown=new OpenDropDown(textSection,fileNameRibbon,versionSection,mdb);
        saveDropDown=new SaveDropDown(fileNameRibbon,textSection,versionSection,mdb);
        btnNew = getButtons("New",e->newService.getNewPage(textSection,fileNameRibbon,versionSection));
        btnOpen = getButtons("Open",e->openDropDown.show(btnOpen,0,btnOpen.getHeight()));
        btnSave = getButtons("Save",e->saveDropDown.show(btnSave,0,btnSave.getHeight()));
        sep1 = getSeparator();
        sep2 = getSeparator();
        sep3 = getSeparator();
        sep4 = getSeparator();
        sep5 = getSeparator();
        fontFamilies = getComboBox();
        btnIncFont = new JButton("+");
        fontSizeField = new JTextField("12");
        btnDecFont = new JButton("-");
        sizePanel = getSizePanel();
    }

    public JPanel getSeparator() {
        JPanel sep = new JPanel();
        sep.setPreferredSize(new Dimension(1, 30));
        sep.setBackground(sepColor);
        return sep;
    }

    public JPanel getSizePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setPreferredSize(new Dimension(80, 20));
        panel.setBorder(BorderFactory.createLineBorder(sepColor, 1));
        panel.setBackground(Color.WHITE);

        fontSizeField.setPreferredSize(new Dimension(38, 18));
        fontSizeField.setHorizontalAlignment(JTextField.CENTER);
        fontSizeField.setBorder(BorderFactory.createEmptyBorder());
        fontSizeField.setEditable(false);
        fontSizeField.getDocument().addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent e){
                update();
            }
            public void removeUpdate(DocumentEvent e){
                update();
            }
            public void changedUpdate(DocumentEvent e){
                update();
            }
            public void update(){
                try{
                    int size=Integer.parseInt(fontSizeField.getText());
                    if(textSection.getTextArea().getSelectionStart() != textSection.getTextArea().getSelectionEnd()){
                        textFormat.setFontSizeForSelection(size);
                    }else{
                        textFormat.setFontSizeAtCaret(size);
                    }
                }catch(NumberFormatException e){}
            }
        });

        btnDecFont.setPreferredSize(new Dimension(20, 18));
        btnDecFont.setMargin(new Insets(0, 0, 0, 0));
        btnDecFont.setFocusPainted(false);
        btnDecFont.setFocusable(false);
        btnDecFont.setContentAreaFilled(false);
        btnDecFont.setOpaque(true);
        btnDecFont.setBackground(eleBgColor);
        btnDecFont.setBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, sepColor));
        btnDecFont.setForeground(textColor);
        btnDecFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int currentSize = Integer.parseInt(fontSizeField.getText());
                    if (currentSize < 1) {
                        return;
                    }
                    currentSize -= 1;
                    fontSizeField.setText(String.valueOf(currentSize));
                    if(textSection.getTextArea().getSelectionStart() != textSection.getTextArea().getSelectionEnd()){
                        textFormat.setFontSizeForSelection(currentSize);
                    }else{
                        textFormat.setFontSizeAtCaret(currentSize);
                    }
                }catch(NumberFormatException ie){}
            }
        });

        btnIncFont.setPreferredSize(new Dimension(20, 18));
        btnIncFont.setMargin(new Insets(0, 0, 0, 0));
        btnIncFont.setFocusPainted(false);
        btnIncFont.setFocusable(false);
        btnIncFont.setContentAreaFilled(false);
        btnIncFont.setOpaque(true);
        btnIncFont.setBackground(eleBgColor);
        btnIncFont.setForeground(textColor);
        btnIncFont.setBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, sepColor));
        btnIncFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    int currentSize = Integer.parseInt(fontSizeField.getText());
                    if (currentSize >= 72) {
                       return;
                    }
                    currentSize += 1;
                    fontSizeField.setText(String.valueOf(currentSize));
                    if(textSection.getTextArea().getSelectionStart() != textSection.getTextArea().getSelectionEnd()){
                        textFormat.setFontSizeForSelection(currentSize);
                    }else{
                        textFormat.setFontSizeAtCaret(currentSize);
                    }
                }catch(NumberFormatException de){}
            }
        });

        panel.add(btnDecFont);
        panel.add(fontSizeField);
        panel.add(btnIncFont);

        return panel;
    }

    public JComboBox<String> getComboBox() {
        JComboBox<String> comboBox = new JComboBox<>(fonts);
        comboBox.setSelectedItem("Arial");
        comboBox.setPreferredSize(new Dimension(160, 20));
        comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(sepColor, 1));
        comboBox.setEditable(true);
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setFont(new Font("Arial", Font.PLAIN, 10));
                button.setPreferredSize(new Dimension(20, 0));
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setForeground(Color.GRAY);
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                return button;
            }
        });
        comboBox.addActionListener(e ->{
            String selectedFont = (String) comboBox.getSelectedItem();
            if(textSection.getTextArea().getSelectionStart() != textSection.getTextArea().getSelectionEnd()){
                textFormat.setFontFamilyForSelection(selectedFont);
            }else{
                textFormat.setFontFamilyAtCaret(selectedFont);
            }
        });
        return comboBox;
    }

    public void setLayout() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(btnNew);
        add(sep1);
        add(btnOpen);
        add(sep2);
        add(btnSave);
        add(sep3);
        add(fontFamilies);
        add(sep4);
        add(sizePanel);
        add(sep5);
    }

    public JButton getButtons(String text,ActionListener action) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(50, 30));
        btn.setOpaque(true);
        btn.setBackground(eleBgColor);
        btn.setForeground(textColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        Color hoverColor = new Color(136, 175, 194);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(eleBgColor);
            }
        });
        btn.addActionListener(action);
        return btn;
    }
}
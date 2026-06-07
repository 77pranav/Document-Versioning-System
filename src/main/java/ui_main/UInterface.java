package ui_main;
import javax.swing.*;

import database.Mdb;
import formatting.TextFormat;

import java.awt.*;

public class UInterface extends JFrame{

    private Mdb mdb;
    private FileNameRibbon fileNameRibbon;
    private HomeRibbon homeRibbon;
    private TextSection textSection;
    private VersionSection versionSection;
    private TextFormat textFormat;
    private JPanel upperPanel;
    private Color bgColor;
    public UInterface(){
        setSize(1000,700);
        setTitle("Document Versioning System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeComponents();
        setBackground(bgColor);
        setLayout();
        setVisible(true);
    }
    public void initializeComponents(){
        bgColor=new Color(128, 144, 220);
        upperPanel=new JPanel(new BorderLayout());
        mdb=new Mdb();
        textSection = new TextSection();
        fileNameRibbon = new FileNameRibbon();
        versionSection = new VersionSection(textSection,fileNameRibbon,mdb);
        textFormat=new TextFormat(textSection);
        homeRibbon = new HomeRibbon(textFormat,textSection,fileNameRibbon,versionSection,mdb);
    }
    public void setLayout(){
        setLayout(new BorderLayout(5,5));
        upperPanel.add(fileNameRibbon,BorderLayout.NORTH);
        upperPanel.add(homeRibbon,BorderLayout.CENTER);
        add(upperPanel,BorderLayout.NORTH);
        add(textSection,BorderLayout.CENTER);
        add(versionSection,BorderLayout.EAST);
    }
    
}
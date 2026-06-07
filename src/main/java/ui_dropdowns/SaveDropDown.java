package ui_dropdowns;
import javax.swing.*;
import java.awt.event.*;

import database.Mdb;
import services.SaveService;
import ui_main.FileNameRibbon;
import ui_main.TextSection;
import ui_main.VersionSection;

public class SaveDropDown extends JPopupMenu{
    private JMenuItem saveAs;
    private JMenuItem saveVersion;
    private SaveService saveService;
    private FileNameRibbon fileNameRibbon;
    private TextSection textSection;
    private VersionSection versionSection;
    public SaveDropDown(FileNameRibbon fileNameRibbon, TextSection textSection, VersionSection versionSection, Mdb mdb){
        saveService=new SaveService(mdb);
        this.fileNameRibbon=fileNameRibbon;
        this.textSection=textSection;
        this.versionSection=versionSection;
        setSaveAsItem();
        setSaveAsVersion();
        add(saveAs);
        add(saveVersion);
    }
    public void setSaveAsItem(){
        saveAs=new JMenuItem("Save As");
        saveAs.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(! fileNameRibbon.getFileName().equalsIgnoreCase("untitled")) return;
                String fileName = JOptionPane.showInputDialog("Enter the file name:");
                saveService.saveAsService(fileName,fileNameRibbon,textSection,versionSection);
            }
        });
    }
    public void setSaveAsVersion(){
        saveVersion=new JMenuItem("Save As Version");
        saveVersion.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                saveService.saveAsVersion(fileNameRibbon,textSection,versionSection);
            }
        });
    }

}

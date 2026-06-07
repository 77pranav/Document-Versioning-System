package ui_dropdowns;
import database.Mdb;
import services.OpenService;
import ui_main.FileNameRibbon;
import ui_main.TextSection;
import ui_main.VersionSection;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
public class OpenDropDown extends JPopupMenu {
    private JMenu openFiles;
    private JMenu openRecentFile;
    private OpenService openService;
    private TextSection textSection;
    private FileNameRibbon fileNameRibbon;
    private VersionSection versionSection;
    public OpenDropDown(TextSection textSection, FileNameRibbon fileNameRibbon, VersionSection versionSection, Mdb mdb){
        openService=new OpenService(mdb);
        this.textSection=textSection;
        this.fileNameRibbon=fileNameRibbon;
        this.versionSection=versionSection;
        setOpenFile();
        setOpenRecentFile();
        add(openFiles);
        add(openRecentFile);
    }
    public void setOpenFile(){
        openFiles = new JMenu("Open File");
        openFiles.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e){
                openService.openAllInitFiles(openFiles,textSection,fileNameRibbon,versionSection);
            }
            @Override
            public void menuDeselected(MenuEvent e){
                openFiles.removeAll();
            }
            @Override
            public void menuCanceled(MenuEvent e){

            }
        });
    }
    public void setOpenRecentFile(){
        openRecentFile=new JMenu("Open Recent File");
        openRecentFile.addMenuListener(new MenuListener(){
            @Override
            public void menuSelected(MenuEvent e){
                openService.openRecentFiles(openRecentFile,textSection,fileNameRibbon,versionSection);
            }
            @Override
            public void menuDeselected(MenuEvent e){
                openRecentFile.removeAll();
            }
            @Override
            public void menuCanceled(MenuEvent e){

            }
        });
    }
}

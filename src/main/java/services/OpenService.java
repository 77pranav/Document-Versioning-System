package services;

import javax.swing.*;

import database.Mdb;
import database.VersionFileOperations;
import org.bson.Document;

import database.InitialFileOperations;
import ui_main.FileNameRibbon;
import ui_main.TextSection;
import ui_main.VersionSection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OpenService {
    private InitialFileOperations initialFileOperations;
    private VersionFileOperations versionFileOperations;
    private JMenuItem fileNames;
    private ArrayList<Document> initialFiles;
    private ArrayList<Document> versionFiles;
    public OpenService(Mdb mdb){
        initialFileOperations=new InitialFileOperations(mdb);
        versionFileOperations=new VersionFileOperations(mdb);
    }
    public void openAllInitFiles(JMenu openFiles, TextSection textSection, FileNameRibbon fileNameRibbon, VersionSection versionSection){
        initialFiles=initialFileOperations.getAllInitialFiles();
        for (int i = 0; i < initialFiles.size(); i++) {
            String title=initialFiles.get(i).getString("title");
            fileNames =new JMenuItem(title);
            openFiles.add(fileNames);
            fileNames.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    initialFileOperations.setVersionFileOperations(versionFileOperations);
                    initialFileOperations.openNamedFile(title,textSection,fileNameRibbon);
                    versionFiles=versionFileOperations.getAllVersionFiles(title);
                    versionSection.giveVersionListPanel().removeAll();
                    versionSection.giveVersionListPanel().revalidate();
                    versionSection.giveVersionListPanel().repaint();
                    for(int i=0;i<versionFiles.size();i++){
                        Object id=versionFiles.get(i).getObjectId("_id");
                        String title=versionFiles.get(i).getString("Title");
                        String createdAt=versionFiles.get(i).getString("Created At");
                        versionSection.giveVersionListPanel().add(versionSection.showVersionDocument(id,title,createdAt));
                    }
                }
            });
        }
    }
    public void openRecentFiles(JMenu openRecentFiles,TextSection textSection,FileNameRibbon fileNameRibbon,VersionSection versionSection){
        versionFiles=versionFileOperations.getRecentlyOpenedFiles();
        for(int i=0;i<versionFiles.size();i++){
            Document selectedFile=versionFiles.get(i);
            String title=selectedFile.getString("Title");
            int versionNo=selectedFile.getInteger("Version");
            String dateAndTime=selectedFile.getString("Last Access");
            fileNames = new JMenuItem("%s %s : %s".formatted(title, String.valueOf(versionNo),dateAndTime));
            openRecentFiles.add(fileNames);
            fileNames.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    versionFileOperations.openVersionFile(selectedFile,textSection,fileNameRibbon);
                    ArrayList<Document> allVersionOfFile=versionFileOperations.getAllVersionFiles(title);
                    versionSection.giveVersionListPanel().removeAll();
                    versionSection.giveVersionListPanel().revalidate();
                    versionSection.giveVersionListPanel().repaint();
                    for(int i=0;i<allVersionOfFile.size();i++){
                        Object id=allVersionOfFile.get(i).getObjectId("_id");
                        String title=allVersionOfFile.get(i).getString("Title");
                        String createdAt=allVersionOfFile.get(i).getString("Created At");
                        versionSection.giveVersionListPanel().add(versionSection.showVersionDocument(id,title,createdAt));
                    }
                }
            });
        }
    }
}

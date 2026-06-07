package services;

import database.InitialFileOperations;
import database.Mdb;
import database.VersionFileOperations;
import org.bson.Document;
import ui_main.FileNameRibbon;
import ui_main.TextSection;
import ui_main.VersionSection;

import java.util.ArrayList;

public class VersionServices {
    private VersionFileOperations versionFileOperations;
    private InitialFileOperations initialFileOperations;
    private DocumentService documentService;
    private NewService newService;
    private ArrayList<Document> versionDocs;
    public VersionServices(Mdb mdb){
        versionFileOperations=new VersionFileOperations(mdb);
        initialFileOperations=new InitialFileOperations(mdb);
        documentService=new DocumentService();
        newService=new NewService();
    }
    public void openVersionDoc(Object id, TextSection textSection) {
        try {
            Document doc = versionFileOperations.searchVersionFile(id);
            versionFileOperations.updateLastAccess(doc);
            documentService.loadFromRTF(textSection.getTextArea(),doc.getString("Content"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void downloadVersionDoc(Object id){
        try{
            Document document=versionFileOperations.getVersionFile(id);
            documentService.downloadFile(document);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteVersionDoc(Object id, TextSection textSection, VersionSection versionSection, FileNameRibbon fileNameRibbon){
        try{
            versionFileOperations.setInitialFileOperations(initialFileOperations);
            Document prevDoc = versionFileOperations.deleteVersionFile(id);
            if(prevDoc==null){
                newService.getNewPage(textSection,fileNameRibbon,versionSection);
                return;
            }
            documentService.loadFromRTF(textSection.getTextArea(),prevDoc.getString("Content"));
            versionDocs=versionFileOperations.getAllVersionFiles(prevDoc.getString("Title"));
            versionSection.giveVersionListPanel().removeAll();
            versionSection.giveVersionListPanel().revalidate();
            versionSection.giveVersionListPanel().repaint();
            for(int i=0;i<versionDocs.size();i++){
                Object Id=versionDocs.get(i).getObjectId("_id");
                String title=versionDocs.get(i).getString("Title");
                String createdAt=versionDocs.get(i).getString("Created At");
                versionSection.giveVersionListPanel().add(versionSection.showVersionDocument(Id,title,createdAt));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

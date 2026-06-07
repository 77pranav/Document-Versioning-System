package services;
import database.Mdb;
import database.VersionFileOperations;
import org.bson.Document;
import ui_main.FileNameRibbon;
import database.InitialFileOperations;
import ui_main.TextSection;
import ui_main.VersionSection;

public class SaveService {
    private String content;
    private DocumentService documentService;
    private InitialFileOperations initialFileOperations;
    private VersionFileOperations versionFileOperations;
    public SaveService(Mdb mdb){
        documentService=new DocumentService();
        initialFileOperations=new InitialFileOperations(mdb);
        versionFileOperations=new VersionFileOperations(mdb);
    }
    public void saveAsService(String fileName, FileNameRibbon fileNameRibbon, TextSection textSection, VersionSection versionSection){
        try {
            if (fileName.isEmpty()
            || textSection.getTextArea().getText().isEmpty()
            || fileName.equalsIgnoreCase("untitled")
            || initialFileOperations.searchInitialFile(fileName) != null) return; //to check by running
            fileNameRibbon.setFileName(fileName);
            content = documentService.convertToRTF(textSection.getTextArea());
            initialFileOperations.setVersionFileOperations(versionFileOperations);
            Document doc=initialFileOperations.insertInitialFile(fileName, content);
            Object id=doc.getObjectId("_id");
            String createdAt=doc.getString("Created At");
            versionSection.giveVersionListPanel().add(versionSection.showVersionDocument(id,fileName,createdAt));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void saveAsVersion(FileNameRibbon fileNameRibbon,TextSection textSection,VersionSection versionSection){
        try{
            if(textSection.getTextArea().getText().isEmpty()) return;
            Document doc=initialFileOperations.searchInitialFile(fileNameRibbon.getFileName());
            content=documentService.convertToRTF(textSection.getTextArea());
            if( versionFileOperations.matchesPreviousVersion(content,doc.getObjectId("_id"),doc.getInteger("latest version")) ) {
                return;
            }
            int versionNum=doc.getInteger("latest version")+1;
            String fileName=doc.getString("title");
            Document verDoc=versionFileOperations.insertVersionFile(doc.getObjectId("_id"),fileName,content,versionNum);
            initialFileOperations.updateLatestVersion(doc.getObjectId("_id"),versionNum);
            Object id=verDoc.getObjectId("_id");
            String createdAt=verDoc.getString("Created At");
            versionSection.giveVersionListPanel().add(versionSection.showVersionDocument(id,fileName,createdAt));
            versionSection.giveVersionListPanel().revalidate();
            versionSection.giveVersionListPanel().repaint();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

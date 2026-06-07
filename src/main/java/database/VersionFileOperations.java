package database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import services.DocumentService;
import ui_main.FileNameRibbon;
import ui_main.TextSection;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class VersionFileOperations {
    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String localDateTime;
    private InitialFileOperations initialFileOperations;
    private MongoCollection<Document> versionCollection;
    private ArrayList<Document> versionFiles;
    private DocumentService documentService;
    public VersionFileOperations(Mdb mdb){
        versionCollection=mdb.getVersionDocsCollection();
    }

    public void setInitialFileOperations(InitialFileOperations initialFileOperations){
        this.initialFileOperations=initialFileOperations;
    }

    public Document insertVersionFile(Object id,String fileName,String content,int versionNum){
        localDateTime=LocalDateTime.now().format(formatter);
        Document doc=new Document();
        versionCollection.insertOne(doc.append("Document Id",id).append("Title",fileName).append("Version",versionNum).append("Content",content).append("Created At",localDateTime).append("Last Access",localDateTime));
        return doc;
    }

    public boolean matchesPreviousVersion(String content,Object id,int versionNum){
        boolean match=false;
        Document doc=versionCollection.find(and(eq("Document Id",id),eq("Version",versionNum))).first();
        String versionContent=doc.getString("Content");
        if(content.equalsIgnoreCase(versionContent)) match=true;
        return match;
    }

    public Document searchVersionFile(String fileName,int latestVersion){
        Document doc=versionCollection.find(and(eq("Title",fileName),eq("Version",latestVersion))).first();
        return doc;
    }
    public Document searchVersionFile(Object id){
        Document doc=versionCollection.find(eq("_id",id)).first();
        return doc;
    }

    public Document deleteVersionFile(Object id){
        Document doc=versionCollection.find(eq("_id",id)).first();
        Document initDoc=initialFileOperations.searchInitialFile(doc.getString("Title"));
        versionCollection.deleteOne(Filters.eq("_id",id));
        Document newDoc=versionCollection.find(eq("Title",initDoc.getString("title")))
                .sort(Sorts.descending("Version"))
                .first();
        if(newDoc == null) {
            initialFileOperations.deleteFile(initDoc.getObjectId("_id"));
            return null;
        }
        else if(doc.getInteger("Version") == initDoc.getInteger("latest version")){
            initialFileOperations.updateLatestVersion(initDoc.getObjectId("_id"),newDoc.getInteger("Version"));
        }
        return newDoc;
    }

    public void updateLastAccess(Document doc){
        localDateTime=LocalDateTime.now().format(formatter);
        versionCollection.updateOne(Filters.eq("_id",doc.getObjectId("_id")), Updates.set("Last Access",localDateTime));
    }

    public ArrayList<Document> getAllVersionFiles(String title){
        versionFiles=new ArrayList<>();
        MongoCursor<Document> cursor=versionCollection.find(eq("Title",title)).iterator();
        try{
            while(cursor.hasNext()){
                versionFiles.add(cursor.next());
            }
        }finally{
            cursor.close();
        }
        return versionFiles;
    }

    public ArrayList<Document> getRecentlyOpenedFiles(){
        versionFiles=new ArrayList<>();
        MongoCursor<Document> cursor = versionCollection
                .find()
                .sort(new Document("Last Access", -1))
                .limit(8)
                .iterator();
        try{
            while(cursor.hasNext()){
                versionFiles.add(cursor.next());
            }
        }finally {
            cursor.close();
        }
        return versionFiles;
    }

    public void openVersionFile(Document selectedFile, TextSection textSection, FileNameRibbon fileNameRibbon){
        try {
            updateLastAccess(selectedFile);
            documentService = new DocumentService();
            documentService.loadFromRTF(textSection.getTextArea(), selectedFile.getString("Content"));
            fileNameRibbon.setFileName(selectedFile.getString("Title"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Document getVersionFile(Object id){
        Document document=versionCollection.find(eq("_id",id)).first();
        return document;
    }
}

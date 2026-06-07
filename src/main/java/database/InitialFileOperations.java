package database;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import services.DocumentService;
import ui_main.FileNameRibbon;
import ui_main.TextSection;

import static com.mongodb.client.model.Filters.eq;

public class InitialFileOperations {
    private int latestVersion=1;
    private DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private String localDateTime;
    private MongoCollection<Document> initialCollection;
    private VersionFileOperations versionFileOperations;
    private Object id;
    private ArrayList<Document> initialFiles;
    private DocumentService docService;
    public InitialFileOperations(Mdb mdb){
        initialCollection=mdb.getInitialDocsCollection();
        docService=new DocumentService();
    }
    public void setVersionFileOperations(VersionFileOperations versionFileOperations){
        this.versionFileOperations=versionFileOperations;
    }
    //SAVE SECTION
    public Document insertInitialFile(String fileName,String content){
        localDateTime=LocalDateTime.now().format(formatter);
        Document doc=new Document();
        initialCollection.insertOne(doc.append("title",fileName).append("date and time",localDateTime).append("latest version",latestVersion));
        id=doc.getObjectId("_id");
        Document verDoc=versionFileOperations.insertVersionFile(id,fileName,content,latestVersion);
        return verDoc;
    }
    public Document searchInitialFile(String fileName){
        Document doc=initialCollection.find(eq("title",fileName)).first();
        return doc;
    }
    public void updateLatestVersion(Object id,int latestVersion){
        initialCollection.updateOne(Filters.eq("_id",id), Updates.set("latest version",latestVersion));
    }

    //OPEN SECTION
    public ArrayList<Document> getAllInitialFiles(){
        initialFiles=new ArrayList<>();
        MongoCursor<Document> cursor=initialCollection.find().iterator();
        try{
            while(cursor.hasNext()){
                initialFiles.add(cursor.next());
            }
        }finally{
            cursor.close();
        }
        return initialFiles;
    }

    public void openNamedFile(String title, TextSection textSection, FileNameRibbon fileNameRibbon){
        try {
            int latestVersion = initialCollection.find(eq("title", title)).first().getInteger("latest version");
            Document doc = versionFileOperations.searchVersionFile(title, latestVersion);
            versionFileOperations.updateLastAccess(doc);
            docService.loadFromRTF(textSection.getTextArea(), doc.getString("Content"));
            fileNameRibbon.setFileName(title);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFile(Object id){
        initialCollection.deleteOne(Filters.eq("_id",id));
    }

}

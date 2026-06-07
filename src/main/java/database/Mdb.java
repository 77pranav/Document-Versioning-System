package database;

 import com.mongodb.client.MongoCollection;
 import com.mongodb.client.MongoDatabase;
 import com.mongodb.client.MongoClient;
 import com.mongodb.client.MongoClients;
 import org.bson.Document;

public class Mdb {
    private static MongoClient mongoClient;
    private String connectionString;
    private static MongoDatabase database;

    public Mdb(){
        connectionString = "mongodb://localhost:27017";
        if(mongoClient == null){
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase("Doc_versioning_system");
        }
    }
    public MongoDatabase getDatabase(){
        return database;
    }
    public MongoCollection<Document> getInitialDocsCollection(){
        return database.getCollection("Initial Documents");
    }
    public MongoCollection<Document> getVersionDocsCollection(){
        return database.getCollection("Version Documents");
    }
    public void breakConnection(){
        if(mongoClient!=null) mongoClient.close();
    }
}

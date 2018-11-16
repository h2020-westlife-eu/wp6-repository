package org.cirmmp.spring.metadata;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataDB {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataDB.class);

    private static DBCollection coll;
    //initialize mongodb connection
    static {
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("repositorydb");
            coll = db.getCollection("metadata");
            LOG.debug("mongodb connection initialized");
        } catch (Exception e)
        {
            coll=null;
            System.err.println("Error during initialization connection to MONGODB");
            e.printStackTrace();
        }
    }

    public static String insertMetadata(Long id,String md){
        if (coll!=null){
            //LOG.debug("inserting for id:"+id+" metadata:"+md);
            BasicDBObject obj = (BasicDBObject) com.mongodb.util.JSON.parse(md);

            BasicDBObject q = new BasicDBObject();
            q.put("id",id);
            obj.put("id",id);
            //will insert if it does not exist or update/replace it if it exist.
            coll.update(q,obj,true,false);
            LOG.debug("inserted for id:"+id);
            return obj.toString();
        } else{
            LOG.error("error, not inserted for id:"+id);
            return ""; //some error?
        }
    }

    //get metadata from mongodb - object with "id" == id
    public static String getMetadata(Long id) {
        LOG.debug("get metadata for id:"+id);
        if (coll!=null){
            //DBObject obj = (DBObject) com.mongodb.util.JSON.parse(md);
            DBObject obj = null;
            BasicDBObject query = new BasicDBObject("id", id);
            DBCursor cursor = coll.find(query);

            try {
                if(cursor.hasNext()) {
                    obj =cursor.next();
                }
            } finally {
                cursor.close();
            }
            LOG.debug("returning metadata:");


            if (obj!=null) {
                LOG.debug("m:"+obj.toString());
                return obj.toString();
            }
            else return "";
        } else{
            return ""; //some error?
        }
    }

}

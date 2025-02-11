package vttp.batch5.groupb.day27_workshop.repositories;



import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp.batch5.groupb.day27_workshop.models.Comment;

import static vttp.batch5.groupb.day27_workshop.repositories.Constants.*;

import java.util.List;

@Repository
public class CommentsRepository {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.comments.insert({
            "user": "Bobby Ong",
            "rating": NumberInt(5),
            "c_text": "Just testing out",
            "gid" : NumberInt(12005),
            "gname": "Testing",
            "posted": DateTime
        })
     * 
     */
    public void postNewComment(Comment comment) throws Exception{
        
        String gameName = gameIdExists(comment.getGid());
        // If game does not exist
        if (gameName == null) {
            throw new Exception("Game Id does not exist.");
        }

        comment.setGameName(gameName);

        Document d = new Document();
        d.put(C_USER, comment.getUser());
        d.put(C_RATING, comment.getRating());
        d.put(C_C_TEXT, comment.getcText());
        d.put(C_POSTED, comment.getPosted());
        d.put(C_GNAME, comment.getGameName());
        d.put(C_GID, comment.getGid());

        Document docInserted = mongoTemplate.insert(d, COMMENTS_COLLECTION);

        if (docInserted.isEmpty()) {
            throw new Exception("Creating comment was unsuccessful");
        }
    }

    /*
     * db.games.find({
            gid : NumberInt(12005)
        })
     */
    private String gameIdExists(int gid) {
        Query query = new Query();

        Criteria criteriaByGid = Criteria.where("gid").is(gid);
        
        query.addCriteria(criteriaByGid);

        List<Document> results = mongoTemplate.find(query, Document.class, GAMES_COLLECTION);

        // If its empty, return null
        if (results.isEmpty()) {
            return null;
        }
        // If exist return name
        return results.get(0).getString(G_NAME);
    }
}

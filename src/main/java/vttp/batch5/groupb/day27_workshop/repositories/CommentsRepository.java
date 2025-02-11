package vttp.batch5.groupb.day27_workshop.repositories;



import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

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

    /*
     * db.comments.updateOne(
            { _id : ObjectId("67ab0dae3fbd6b69411967fb")},
            {
            $set : {
                c_text: "Updated rating to 10", 
                rating: NumberInt(8), 
                posted: ISODate("2025-02-11T08:43:26.546+0000")},
            $push : {edited : {
                c_text: "Updated rating to 10", 
                rating: NumberInt(8), 
                posted: ISODate("2025-02-11T08:43:26.546+0000")
            }}
            } 
        )
     * 
     */

     public void updateComment(Comment comment) throws Exception {
        Query query = new Query();

        Criteria criteriaByCommentId = Criteria.where(C_CID).is(comment.getCid());

        query.addCriteria(criteriaByCommentId);

        Document editRecord = new Document();
        editRecord.put(C_C_TEXT, comment.getcText());
        editRecord.put(C_RATING, comment.getRating());
        editRecord.put(C_POSTED, comment.getPosted());

        Update updateOps = new Update()
            .set(C_C_TEXT, comment.getcText())
            .set(C_RATING, comment.getRating())
            .set(C_POSTED, comment.getPosted())
            .push("edited", editRecord);

        UpdateResult updateResult = mongoTemplate.updateMulti(query, updateOps, COMMENTS_COLLECTION);

        if (updateResult.getMatchedCount() == 0) {
            throw new Exception("Comment Id does not exist");
        }
        if (updateResult.getModifiedCount() == 0) {
            throw new Exception("Update was unsuccessful");
        }
     }
}

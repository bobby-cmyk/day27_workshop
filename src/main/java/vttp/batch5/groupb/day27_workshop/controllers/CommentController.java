package vttp.batch5.groupb.day27_workshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.batch5.groupb.day27_workshop.models.Comment;
import vttp.batch5.groupb.day27_workshop.services.CommentService;

@RestController
@RequestMapping
public class CommentController {
    
    @Autowired
    private CommentService commentSvc;

    @PostMapping(
        path="/review", 
        consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE, 
        produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postNewComment(
        @ModelAttribute Comment comment
    ) {
        try {
            commentSvc.postNewComment(comment);

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("message", "Succesfully added comment");
            JsonObject respObj = builder.build();

            return ResponseEntity.ok().body(respObj.toString());
        }

        catch (Exception e) {

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("message", e.getMessage());
            JsonObject respObj = builder.build();

            return ResponseEntity.badRequest().body(respObj.toString());
        }
    }

    @PutMapping(
        path="/review/{review_id}",
        consumes=MediaType.APPLICATION_JSON_VALUE,
        produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> updateComment(
        @PathVariable(name="review_id") String cid,
        @RequestBody Comment comment) 
    {  
        comment.setCid(cid);

        System.out.println(comment);

        try {
            commentSvc.updateComment(comment);

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("message", "Succesfully updated comment");

            return ResponseEntity.ok().body(builder.build().toString());
        }

        catch (Exception e) {
            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder.add("message", e.getMessage());

            return ResponseEntity.badRequest().body(builder.build().toString());
        }
    }
}

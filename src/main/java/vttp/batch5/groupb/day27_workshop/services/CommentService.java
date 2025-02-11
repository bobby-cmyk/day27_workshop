package vttp.batch5.groupb.day27_workshop.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp.batch5.groupb.day27_workshop.models.Comment;
import vttp.batch5.groupb.day27_workshop.repositories.CommentsRepository;

@Service
public class CommentService {
    
    @Autowired
    private CommentsRepository commentRepo;

    public void postNewComment(Comment comment) throws Exception{

        LocalDateTime posted = LocalDateTime.now(); 
        comment.setPosted(posted);

        commentRepo.postNewComment(comment);
    }

    public void updateComment(Comment comment) throws Exception {

        LocalDateTime posted = LocalDateTime.now(); 
        comment.setPosted(posted);

        commentRepo.updateComment(comment);
    }
}

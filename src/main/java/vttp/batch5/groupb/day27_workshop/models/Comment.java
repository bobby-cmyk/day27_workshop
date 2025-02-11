package vttp.batch5.groupb.day27_workshop.models;

import java.time.LocalDateTime;

public class Comment {
    
    private String user;
    private int rating;
    private String cText;
    private int gid;
    private LocalDateTime posted;
    private String gameName;
    private String cid;

    @Override
    public String toString() {
        return "Comment [user=" + user + ", rating=" + rating + ", cText=" + cText + ", gid=" + gid + ", posted="
                + posted + ", gameName=" + gameName + ", cid=" + cid + "]";
    }
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getcText() {
        return cText;
    }
    public void setcText(String cText) {
        this.cText = cText;
    }
    public int getGid() {
        return gid;
    }
    public void setGid(int gid) {
        this.gid = gid;
    }
    public LocalDateTime getPosted() {
        return posted;
    }
    public void setPosted(LocalDateTime posted) {
        this.posted = posted;
    }
    public String getGameName() {
        return gameName;
    }
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}

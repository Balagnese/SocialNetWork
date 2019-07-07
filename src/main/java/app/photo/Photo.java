package app.photo;

import app.profile.Profile;
import lombok.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Photo {

    public static enum Action{
        LIKE,
        DISLIKE,
        CLEAR
    }

    private Profile person;
    String url;
    String id;

    Map<Profile, Action> likers = new HashMap<>();

    public Photo(String url, Profile person) {
        this.url = url;
        this.person = person;
        this.id = UUID.randomUUID().toString();
    }

    public String getUrl(){
        return this.url;
    }

    public int getLikesCount(){
        return Collections.frequency(likers.values(), Action.LIKE);
    }

    public int getDislikesCount(){
        return Collections.frequency(likers.values(), Action.DISLIKE);
    }

    public Profile getProfile(){
        return person;
    }

    public String getId(){
        return id;
    }

    public Action getActionByPerson(Profile p){
        if (likers.get(p) != null)
            return likers.get(p);
        return Action.CLEAR;
    }

    public void performAction(Profile liker, Action a){
        switch (a){
            case LIKE:
            case DISLIKE:
                likers.put(liker, a);
                break;
                default:
                    likers.remove(liker);

        }
    }
}



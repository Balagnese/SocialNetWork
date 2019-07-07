package app.profile;

import app.photo.Photo;
import app.user.User;
import lombok.Value;

import javax.management.relation.Relation;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.Application.photoDao;

public class Profile {

    public static enum RELATION{
        BLOCK,
        SUBSCRIPTION,
        NEUTRAL
    }

    private User user;
    private String name;
    private String surname;
    private String zodiac;
    private String isbn;
    private Photo mainPhoto;
    Map<Profile, Profile.RELATION> relations = new HashMap<>();


    public Profile(User user, String name, String surname, String zodiac, String isbn) {
        this.user = user;
        this.name = name;
        this.surname = surname;
        this.zodiac = zodiac;
        this.isbn = isbn;
    }

    public void changeData(String name, String surname, String zodiac){
        this.name = name;
        this.surname = surname;
        this.zodiac = zodiac;
    }

    public String getIsbn(){
        return isbn;
    }

    public User getUser(){
        return user;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getZodiac(){
        return zodiac;
    }

    public String getMainPhoto(){
        if (mainPhoto != null)
            return mainPhoto.getUrl();
        return "data:image/jpeg;base64,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";
    }

    public void addPhoto(String url){
        photoDao.addPhoto(new Photo(url, this));
    }

    public void chooseMainPhoto(Photo p){
        mainPhoto = p;
    }

    public void performRelation(Profile person, RELATION r){ relations.put(person, r); }

    public RELATION getRelationWithPerson(Profile p){
        if (relations.get(p) == null)
            return RELATION.NEUTRAL;
        return relations.get(p);
    }

}

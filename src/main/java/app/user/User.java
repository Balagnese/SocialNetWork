package app.user;

import app.profile.Profile;
import lombok.*;

import static app.Application.profileDao;

@Value // All fields are private and final. Getters (but not setters) are generated (https://projectlombok.org/features/Value.html)
public class User {
    String username;
    String salt;
    String hashedPassword;

    public User(String username, String newSalt, String newHashedPassword) {
        this.username = username;
        salt = newSalt;
        hashedPassword = newHashedPassword;
    }

    public String getUsername(){
        return username;
    }

    public boolean isUserProfiled(){
        return profileDao.getProfileByUsername(username) != null;
    }

    public Profile getProfile(){
        return profileDao.getProfileByUsername(username);
    }
}

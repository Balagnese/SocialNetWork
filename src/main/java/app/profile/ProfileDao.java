package app.profile;

import app.profile.Profile;
import app.user.User;
import app.util.RequestUtil;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

public class ProfileDao {
    private final List<Profile> profiles = new ArrayList<>();



    public List<Profile> getAllProfiles() {
        return profiles;
    }

    public Profile getProfileByIsbn(String isbn) {
        return profiles.stream().filter(b -> b.getIsbn().equals(isbn)).findFirst().orElse(null);
    }

    public Profile getProfileByUsername(String username) {
        return profiles.stream().filter(b -> b.getUser().getUsername().equals(username)).findFirst().orElse(null);
    }

    public void addProfile(Profile profile){
        profiles.add(profile);
    }

}

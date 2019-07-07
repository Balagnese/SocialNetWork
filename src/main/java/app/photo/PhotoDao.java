package app.photo;

import app.profile.Profile;
import com.google.common.collect.ImmutableList;

import java.util.LinkedList;
import java.util.List;

public class PhotoDao {
    static List<Photo> photos = new LinkedList<>();

    public List<Photo> getProfilePhotos(String isbn) {
        List<Photo> outPhotos = new LinkedList<>();
        for (Photo photo : photos)
        {
            if (photo.getProfile().getIsbn().equals(isbn))
                outPhotos.add(photo);
        }
        return outPhotos;
    }
    public void addPhoto(Photo photo){
        photos.add(photo);
    }

    public Photo getPhotoById(String id){
        for (Photo photo : photos)
        {
            if (photo.getId().equals(id))
                return photo;
        }
        return null;
    }
}


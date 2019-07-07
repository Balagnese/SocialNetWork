package app.photo;

import app.login.LoginController;
import app.profile.Profile;
import app.user.User;
import app.util.Path;
import app.util.RequestUtil;
import app.util.ViewUtil;
import org.eclipse.jetty.server.Server;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import static app.Application.*;
import static app.util.JsonUtil.dataToJson;
import static app.util.RequestUtil.*;

public class PhotoController {
    public static Route fetchProfilePhotos = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            List<Photo> photos = photoDao.getProfilePhotos(getParamIsbn(request));
            model.put("photos", photos);
            Profile profile = profileDao.getProfileByIsbn(getParamIsbn(request));
            model.put("profile", profile);
            return ViewUtil.render(request, model, Path.Template.PROFILE_PHOTOS);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(photoDao.getProfilePhotos(getParamIsbn(request)));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route fetchLikingProfilePhotos = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            List<Photo> photos = photoDao.getProfilePhotos(getParamIsbn(request));
            model.put("photos", photos);
            Profile profile = profileDao.getProfileByIsbn(getParamIsbn(request));
            model.put("profile", profile);
            return ViewUtil.render(request, model, Path.Template.LIKING_PROFILE_PHOTOS);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(photoDao.getProfilePhotos(getParamIsbn(request)));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route fetchMyProfilePhotos = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        User u = RequestUtil.getCurrentUserFromSession(request);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            List<Photo> photos = photoDao.getProfilePhotos(u.getProfile().getIsbn());
            model.put("photos", photos);
            Profile profile = profileDao.getProfileByIsbn(u.getProfile().getIsbn());
            model.put("profile", profile);
            return ViewUtil.render(request, model, Path.Template.MY_PROFILE_PHOTOS);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(photoDao.getProfilePhotos(u.getProfile().getIsbn()));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route fetchLikePhoto = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        User u = RequestUtil.getCurrentUserFromSession(request);
        if (u.isUserProfiled()) {
            Photo photo = photoDao.getPhotoById(request.params("id"));

            switch (request.params("action")) {
                case ("like"):
                    photo.performAction(photo.getProfile(), Photo.Action.LIKE);
                    break;
                case ("dislike"):
                    photo.performAction(photo.getProfile(), Photo.Action.DISLIKE);
                    break;
                case ("clear"):
                    photo.performAction(photo.getProfile(), Photo.Action.CLEAR);
                    break;
                default:
                    return ViewUtil.notAcceptable.handle(request, response);
            }
            response.redirect("/profiles/" + photo.getProfile().getIsbn());
            return null;
        }
        response.redirect(Path.Web.CREATE_PROFILE);
        return null;
    };

    public static Route uploadImage = (Request request, Response response) -> {
        String url = request.queryParams("url");
        User u = RequestUtil.getCurrentUserFromSession(request);
        Photo p = new Photo(url, u.getProfile());
        photoDao.addPhoto(p);
        response.redirect(Path.Web.PROFILES);
        return null;
    };

    public static Route getUplaodImagePage = (Request request, Response response) -> {
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.UPLOAD_IMAGE);
    };
}

package app.profile;

import app.login.*;
import app.photo.Photo;
import app.user.User;
import app.util.*;
import com.sun.xml.internal.bind.v2.TODO;
import spark.*;
import java.util.*;

import static app.Application.photoDao;
import static app.Application.profileDao;
import static app.util.JsonUtil.*;
import static app.util.RequestUtil.*;

public class ProfileController {
    public static Route fetchAllProfiles = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            HashMap<String, Object> model = new HashMap<>();
            model.put("profiles", profileDao.getAllProfiles());
            return ViewUtil.render(request, model, Path.Template.PROFILES);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(profileDao.getAllProfiles());
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route fetchOneProfile = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        User u = RequestUtil.getCurrentUserFromSession(request);
        if (u.isUserProfiled()) {
            if (clientAcceptsHtml(request)) {
                Profile profile = profileDao.getProfileByIsbn(getParamIsbn(request));
                HashMap<String, Object> model = new HashMap<>();
                model.put("profile", profile);

                Profile.RELATION relationCurToPerson = u.getProfile().getRelationWithPerson(profile);
                Profile.RELATION relationPersonToCur = profile.getRelationWithPerson(u.getProfile());

                model.put("relationToUser", relationPersonToCur);
                model.put("relationToPerson", relationCurToPerson);
                if (profile.getIsbn().equals(u.getProfile().getIsbn())){
                    return ViewUtil.render(request, model, Path.Template.MY_PROFILE);
                }
                else
                    return ViewUtil.render(request, model, Path.Template.PROFILE);
            }
            if (clientAcceptsJson(request)) {
                return dataToJson(profileDao.getProfileByIsbn(getParamIsbn(request)));
            }
            return ViewUtil.notAcceptable.handle(request, response);
        }
        response.redirect(Path.Web.CREATE_PROFILE);
        return null;

    };

    public static Route fetchMyProfile = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        if (clientAcceptsHtml(request)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            HashMap<String, Object> model = new HashMap<>();
            model.put("profile", u.getProfile());
//
//            Profile.RELATION relationCurToPerson = u.getProfile().getRelationWithPerson(profile);
//            Profile.RELATION relationPersonToCur = profile.getRelationWithPerson(u.getProfile());
//
//            model.put("relationToUser", relationPersonToCur);
//            model.put("relationToPerson", relationCurToPerson);

            return ViewUtil.render(request, model, Path.Template.MY_PROFILE);
        }
        if (clientAcceptsJson(request)) {
            return dataToJson(profileDao.getProfileByIsbn(getParamIsbn(request)));
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route getCreateProfilePage = (Request request, Response response) -> {
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.CREATE_PROFILE);
    };

    public static Route createProfile = (Request request, Response response) -> {
        if (LoginController.isUserLoggedIn(request, response)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            if (!u.isUserProfiled()) {
                Profile p = new Profile(u, request.queryParams("name"), request.queryParams("surname"), request.queryParams("zodiac"),
                        UUID.randomUUID().toString());
                profileDao.addProfile(p);
            }
            response.redirect(Path.Web.PROFILES);
            return null;
        }
        return ViewUtil.notAcceptable.handle(request, response);


    };

    public static Route getChangeProfilePage = (Request request, Response response) -> {
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.CHANGE_PROFILE);
    };

    public static Route changeProfile = (Request request, Response response) -> {
        if (LoginController.isUserLoggedIn(request, response)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            if (u.isUserProfiled()) {
                u.getProfile().changeData(request.queryParams("name"), request.queryParams("surname"), request.queryParams("zodiac"));
            }
            response.redirect(Path.Web.PROFILES);
            return null;
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route manageProfile = (Request request, Response response) -> {
        if (LoginController.isUserLoggedIn(request, response)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            if (u.isUserProfiled()) {
                response.redirect(Path.Web.MY_PROFILE);
            }
            else {
                response.redirect(Path.Web.CREATE_PROFILE);
                return null;
            }
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route chooseMainPhoto = (Request request, Response response) -> {
        if (LoginController.isUserLoggedIn(request, response)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            Photo photo = photoDao.getPhotoById(request.params("id"));
            u.getProfile().chooseMainPhoto(photo);
            response.redirect("/profiles/" + photo.getProfile().getIsbn());
            return null;
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route fetchRelation = (Request request, Response response) -> {
        if (LoginController.isUserLoggedIn(request, response)) {
            User u = RequestUtil.getCurrentUserFromSession(request);
            Profile profile = profileDao.getProfileByIsbn(getParamIsbn(request));
            switch (request.params("relation")){
                case("block"):
                    u.getProfile().performRelation(profile, Profile.RELATION.BLOCK);
                    break;
                case("subscription"):
                    u.getProfile().performRelation(profile, Profile.RELATION.SUBSCRIPTION);
                    break;
                    default:
                        u.getProfile().performRelation(profile, Profile.RELATION.NEUTRAL);
            }
//            Profile.RELATION relationCurToPerson = u.getProfile().getRelationWithPerson(profile);
//            Profile.RELATION relationPersonToCur = profile.getRelationWithPerson(u.getProfile());
            response.redirect("/profiles/" + profile.getIsbn());
            return null;

        }
        return ViewUtil.notAcceptable.handle(request, response);
    };

}

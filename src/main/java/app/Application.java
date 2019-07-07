package app;

import app.book.*;
import app.index.*;
import app.login.*;
import app.photo.PhotoController;
import app.photo.PhotoDao;
import app.profile.Profile;
import app.profile.ProfileController;
import app.profile.ProfileDao;
import app.registration.RegistrationController;
import app.user.*;
import app.util.*;
import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Application {

    // Declare dependencies
    public static BookDao bookDao;
    public static ProfileDao profileDao;
    public static UserDao userDao;
    public static PhotoDao photoDao;

    public static void main(String[] args) {

        // Instantiate your dependencies
        bookDao = new BookDao();
        profileDao = new ProfileDao();
        userDao = new UserDao();
        photoDao = new PhotoDao();

        // Configure Spark
        port(4567);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        // Set up before-filters (called before each get/post)
        before("*", Filters.addTrailingSlashes);
        before("*", Filters.handleLocaleChange);

//        // Set up routes
        get(Path.Web.PROFILE_PHOTOS, PhotoController.fetchProfilePhotos);
        get(Path.Web.MY_PROFILE_PHOTOS, PhotoController.fetchMyProfilePhotos);
        get(Path.Web.LIKING_PROFILE_PHOTOS, PhotoController.fetchLikingProfilePhotos);
        get(Path.Web.MY_PROFILE, ProfileController.fetchMyProfile);
        get(Path.Web.PROFILES, ProfileController.fetchAllProfiles);
        get(Path.Web.PROFILE, ProfileController.fetchOneProfile);
        get(Path.Web.INDEX, IndexController.serveIndexPage);
        get(Path.Web.BOOKS, BookController.fetchAllBooks);
        get(Path.Web.ONE_BOOK, BookController.fetchOneBook);
        get(Path.Web.LOGIN, LoginController.serveLoginPage);
        post(Path.Web.LOGIN, LoginController.handleLoginPost);
        post(Path.Web.LOGOUT, LoginController.handleLogoutPost);
        post(Path.Web.LIKE_PHOTO, PhotoController.fetchLikePhoto);
        post(Path.Web.CHOOSE_MAIN_PHOTO, ProfileController.chooseMainPhoto);
        get(Path.Web.REGISTRATION, RegistrationController.getRegistrationPage);
        post(Path.Web.REGISTRATION, RegistrationController.register);
        get(Path.Web.CREATE_PROFILE, ProfileController.getCreateProfilePage);
        post(Path.Web.CREATE_PROFILE, ProfileController.createProfile);
        get(Path.Web.CHANGE_PROFILE, ProfileController.getChangeProfilePage);
        post(Path.Web.CHANGE_PROFILE, ProfileController.changeProfile);
        get(Path.Web.MY_PROFILE, ProfileController.fetchOneProfile);
        get(Path.Web.MANAGE_PROFILE, ProfileController.manageProfile);
        get(Path.Web.UPLOAD_IMAGE, PhotoController.getUplaodImagePage);
        post(Path.Web.UPLOAD_IMAGE, PhotoController.uploadImage);
        post(Path.Web.PERFORM_RELATION, ProfileController.fetchRelation);

        get("*", ViewUtil.notFound);


        //Set up after-filters (called after each get/post)
        after("*", Filters.addGzipHeader);


        profileDao.addProfile(new Profile(new User("federico", "$2a$10$E3DgchtVry3qlYlzJCsyxe", "$2a$10$E3DgchtVry3qlYlzJCsyxeSK0fftK4v0ynetVCuDdxGVl1obL.ln2"),
                "Max", "Potter", "Lion", "9789583001215"));
        profileDao.getAllProfiles().get(0).addPhoto("https://vignette.wikia.nocookie.net/harrypotter/images/4/4f/Harry_Potter_-_GoF_Promo.jpg/revision/latest?cb=20061109083433&path-prefix=ru");


    }

}

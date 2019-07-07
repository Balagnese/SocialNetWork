package app.util;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter
        public static final String INDEX = "/index/";
        @Getter
        public static final String LOGIN = "/login/";
        @Getter
        public static final String LOGOUT = "/logout/";
        @Getter
        public static final String BOOKS = "/books/";
        @Getter
        public static final String ONE_BOOK = "/books/:isbn/";
        @Getter
        public static final String PROFILES = "/profiles/";
        @Getter
        public static final String PROFILE = "/profiles/:isbn/";
        @Getter
        public static final String LIKE_PHOTO = "/photos/:id/:action/";
        @Getter
        public static final String REGISTRATION = "/register/";
        @Getter
        public static final String CREATE_PROFILE = "/create_profile/";
        @Getter
        public static final String CHANGE_PROFILE = "/change_profile/";
        @Getter
        public static final String MY_PROFILE = "/my_profile/";
        @Getter
        public static final String MANAGE_PROFILE = "/manage_profile/";
        @Getter
        public static final String UPLOAD_IMAGE = "/upload_image/";
        @Getter
        public static final String CHOOSE_MAIN_PHOTO = "/choose_main_photo/:id/";
        @Getter
        public static final String PERFORM_RELATION = "/perform_relation/:isbn/:relation/";
        @Getter
        public static final String PROFILE_PHOTOS = "/profiles/:isbn/photos/";
        @Getter
        public static final String MY_PROFILE_PHOTOS = "/my_profile/photos/";
        @Getter
        public static final String LIKING_PROFILE_PHOTOS = "/profiles/:isbn/liking_photos/";
    }

    public static class Template {
        public final static String INDEX = "/velocity/index/index.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public final static String BOOKS_ALL = "/velocity/book/all.vm";
        public static final String BOOKS_ONE = "/velocity/book/one.vm";
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String PROFILES = "/velocity/profile/all.vm";
        public static final String PROFILE = "/velocity/profile/one.vm";
        public static final String REGISTRATION = "/velocity/registration/register.vm";
        public static final String CREATE_PROFILE = "/velocity/profile/createProfile.vm";
        public static final String MY_PROFILE = "/velocity/profile/myProfile.vm";
        public static final String CHANGE_PROFILE = "/velocity/profile/createProfile.vm";
        public static final String UPLOAD_IMAGE = "/velocity/photo/uploadPhoto.vm";
        public static final String PROFILE_PHOTOS = "/velocity/photo/photo.vm";
        public static final String MY_PROFILE_PHOTOS = "/velocity/photo/photo.vm";
        public static final String LIKING_PROFILE_PHOTOS = "/velocity/photo/likingPhoto.vm";
    }
}
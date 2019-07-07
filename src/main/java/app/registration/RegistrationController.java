package app.registration;

import app.login.LoginController;
import app.user.User;
import app.user.UserController;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class RegistrationController {

    public static Route getRegistrationPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Path.Template.REGISTRATION);
    };

    public static Route register = (Request request, Response response) -> {
        if (!LoginController.isUserLoggedIn(request, response)){
            User u = UserController.registerUser(request.queryParams("username"), request.queryParams("password"));

            response.redirect(Path.Web.LOGIN);
            return null;
        }
        return ViewUtil.notAcceptable.handle(request, response);
    };
}

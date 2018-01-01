package controllers;

import annotations.Authenticated;
import annotations.NotAuthenticated;
import dto.LoginDTO;
import dto.UserDTO;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import views.html.login;
import views.html.register;

import javax.inject.Inject;

public class UserController extends Controller {

    private final UserService userService;
    private final Form<LoginDTO> loginForm;
    private final Form<UserDTO> registrationForm;

    @Inject
    public UserController(UserService userService, FormFactory formFactory) {
        this.userService = userService;
        this.loginForm = formFactory.form(LoginDTO.class);
        this.registrationForm = formFactory.form(UserDTO.class);
    }

    public Result getLoginForm() {
        return ok(login.render(loginForm));
    }

    public Result login() {
        Form<LoginDTO> loginDTOForm = loginForm.bindFromRequest();
        if (loginDTOForm.hasErrors()) {
            return badRequest(login.render(loginDTOForm));
        } else {
            return userService.authenticateUser(loginDTOForm.get())
                    .map(loginDTO -> {
                        session("username", loginDTO.username);
                        return redirect(routes.BlogController.blog(1));
                    })
                    .orElse(badRequest(login.render(loginDTOForm.withGlobalError(
                            "Your username and password didn't match. Please try again."))));
        }
    }

    @Authenticated
    public Result logout() {
        session().clear();
        return redirect(routes.BlogController.blog(1));
    }

    @NotAuthenticated
    public Result getRegistrationForm() {
        return ok(register.render(registrationForm));
    }

    @NotAuthenticated
    public Result register() {
        Form<UserDTO> registrationForm = this.registrationForm.bindFromRequest();
        if (registrationForm.hasErrors()) {
            return badRequest(register.render(registrationForm));
        } else {
            return userService.saveUser(registrationForm.get())
                    .map(userDTO -> redirect(routes.UserController.login()))
                    .orElse(badRequest(register.render(registrationForm.withGlobalError(
                            "Username with those credentials already exists. Please try again"))));
        }
    }
}

package controllers;

import models.User;
import play.api.i18n.MessagesApi;
import play.data.Form;
import play.data.FormFactory;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.Objects;

public class Application extends Controller {

    private final FormFactory formFactory;
    private final JPAApi jpaApi;
    private final MessagesApi messagesApi;
    private final Form<User> registerForm;

    @Inject
    public Application(FormFactory formFactory, JPAApi jpaApi, MessagesApi messagesApi) {
        this.formFactory = formFactory;
        this.jpaApi = jpaApi;
        this.messagesApi = messagesApi;
        this.registerForm = formFactory.form(User.class);
    }

    public Result index() {
        String user = session("user");
        if (user == null || user.trim().isEmpty()) {
            return redirect(routes.Application.register());
        } else {
            return ok(views.html.index.render("Hello " + session("username")));
        }
    }

    public Result register() {
        return ok(views.html.register.render(registerForm, ""));
    }

    @Transactional
    public Result registerPost() {
        Form<User> userForm = registerForm.bindFromRequest("username", "password");
        if (userForm.hasErrors()) {
            return badRequest(views.html.register.render(userForm, null));
        } else {
            User user = userForm.get();
            User dbUser = jpaApi.em().createQuery("select u from m_user u where u.username = ? and u.password = ?", User.class)
                    .setParameter(0, user.getUsername())
                    .setParameter(1, user.getPassword())
                    .getSingleResult();
            if (Objects.nonNull(dbUser)) {
                session("user", String.valueOf(dbUser.getId()));
                session("username", dbUser.getUsername());
                return redirect(routes.Application.index());
            } else {
                return ok(views.html.register.render(registerForm, "mismatch username and password"));
            }
        }
    }

}
package controllers

//import models.User
//import play.api.mvc._
//import play.api.data._
//import play.api.data.Forms._
//import javax.inject.Inject
//import play.api.i18n.I18nSupport
//import play.api.i18n.MessagesApi

//class ApplicationS @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
//
//  val registerForm = Form(
/*    tuple(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
            "repeat" -> number(min = 1, max = 100),
            "color" -> optional(text)
    )*/
//    mapping(
//      "username" -> nonEmptyText,
//      "password" -> nonEmptyText
//    )(User.apply)(User.unapply)
//  )
//
//  def index = Action { implicit request =>
//    val user = request.session.get("user")
//    if (!user.isDefined) {
//      Redirect(routes.Application.register)
//    } else {
//      Ok(views.html.index("Title of Main page1"))
//    }
//  }
//
//  def register = Action {
//    Ok(views.html.register("Title of Register page", registerForm))
//  }
//
//  def registerPost = Action { implicit request =>
//    registerForm.bindFromRequest.fold(
//      formWithErrors => {
//        BadRequest(views.html.register("Title of Register page: Bad", formWithErrors))
//      },
//      user => {
//        if (user == User("root", "root")) {
//          Redirect(routes.Application.index).withSession(("user" -> user.id))
//        } else {
//          Ok(views.html.register("Title of Register page", registerForm, "mismatch username and password"))
//        }
//      }
//    )
//  }
//
//
//
//}
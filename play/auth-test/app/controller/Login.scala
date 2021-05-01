package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import models.{ ViewValueLogin, User }

@Singleton
class LoginController @Inject()(
  cc: ControllerComponents
) extends AbstractController(cc) with I18nSupport {

  val userForm = Form(
    mapping(
      "name"     -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText,
    )(User.apply)(User.unapply)
  )

  def show() = Action { implicit req =>
    val vv = ViewValueLogin(
      userForm = userForm
    )
    Ok(views.html.Login(vv))
  }

  def login() = Action { implicit req =>
    userForm.bindFromRequest().fold(
      formWithErrors => {
        val vv = ViewValueLogin(
          userForm = formWithErrors
        )
        BadRequest(views.html.Login(vv))
      },
      formData => {
        Redirect(routes.TopController.show())
      }
    )
  }
}

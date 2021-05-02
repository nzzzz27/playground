package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }
import scala.concurrent.{ Future, ExecutionContext }

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import models.{ ViewValueLogin, ViewValueUser }

import lib.model.User
import lib.persistence.UserRepository

@Singleton
class LoginController @Inject()(
  cc:              ControllerComponents,
  userRepository:  UserRepository,
)(implicit ex: ExecutionContext)
extends AbstractController(cc) with I18nSupport {

  val userForm = Form(
    mapping(
      "id"       -> optional(longNumber),
      "name"     -> nonEmptyText,
      "email"    -> email,
    )(ViewValueUser.apply)(ViewValueUser.unapply)
  )

  def show() = Action { implicit req =>
    val vv = ViewValueLogin(
      userForm = userForm
    )
    println(req.session)
    Ok(views.html.Login(vv))
  }

  def login() = Action async { implicit req =>
    for {
      user  <- userRepository.getById(Some(1L))
    } yield {
      println(user)
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
}

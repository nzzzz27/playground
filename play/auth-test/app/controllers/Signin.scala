package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }
import scala.concurrent.{ Future, ExecutionContext }

import models.{ ViewValueSignin }
import models.form.FormSignin

import lib.model.User
import lib.persistence.{ UserRepository, PasswordRepository }

@Singleton
class SigninController @Inject()(
  cc:              ControllerComponents,
  userRepository:  UserRepository,
  passwordRepository:  PasswordRepository,
)(implicit ex: ExecutionContext)
extends AbstractController(cc) with I18nSupport {

  val form = FormSignin.signinForm

  def index() = Action { implicit req =>
    val vv = ViewValueSignin(
      form = form
    )
    println(req.session)
    Ok(views.html.Signin(vv))
  }

  def signin() = Action async { implicit req =>
    form.bindFromRequest().fold(
      formWithErrors => {
        val vv = ViewValueSignin(
          form = formWithErrors
        )
        Future.successful(BadRequest(views.html.Signin(vv)))
      },
      formData => {
        val user = User(
          formData.firstName,
          formData.lastName,
          formData.email
        )
        for {
          userOpt        <- userRepository.getByData(user)
          passwordOpt    <- passwordRepository.getByUserId(userOpt)
        } yield {
          passwordOpt match {
            case Some(password) => {
              Redirect(routes.TopController.show())
            }
            case _          => {
              val vv = ViewValueSignin(
                form = form,
                errorMessage = "何かが一致しません"
              )
              BadRequest(views.html.Signin(vv))
            }
          }
        }
      }
    )
  }
}

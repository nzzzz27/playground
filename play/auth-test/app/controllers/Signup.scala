package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }
import scala.concurrent.{ Future, ExecutionContext }
import play.api.Logger
import play.api.data.Form

import models.{ ViewValueSignup, FormSignup }

import lib.model.{ User, Password }
import lib.persistence.{ UserRepository, PasswordRepository }

@Singleton
class SignupController @Inject()(
  cc:                  ControllerComponents,
  userRepository:      UserRepository,
  passwordRepository:  PasswordRepository,
)(implicit ex: ExecutionContext)
extends AbstractController(cc) with I18nSupport {

  val form = FormSignup.signupForm

  def index() = Action { implicit req =>
    val vv = ViewValueSignup(
      form = form
    )
    println(req.session)
    Ok(views.html.Signup(vv))
  }

  def signup() = Action async { implicit req =>
    form.bindFromRequest().fold(
      formWithErrors => {
        val vv = ViewValueSignup(
          form = formWithErrors
        )
        Future.successful(BadRequest(views.html.Signup(vv)))
      },
      formData => {
        val user: User = User(
          formData.firstName,
          formData.lastName,
          formData.email
        )
        for {
          resUser <- userRepository.post(user)
        } yield {
          resUser match {
            case Some(userId) => {
              val password = Password(
                Some(userId),
                formData.password
              )
              passwordRepository.post(password)
              Redirect(routes.TopController.show())
            }
            case _            => {
              val vv = ViewValueSignup(
                form = form,
                errorMessage = "失敗しました"
              )
              BadRequest(views.html.Signup(vv))
            }
          }
        }
      }
    )
  }
}

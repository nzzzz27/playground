package controllers

import javax.inject._
import java.util.UUID
import play.api.i18n.I18nSupport
import play.api.mvc.{ Cookie, ControllerComponents, AbstractController }
import scala.concurrent.{ Future, ExecutionContext }

import models.{ ViewValueSignin }
import models.form.FormSignin

import lib.model.{ User, AuthToken }
import lib.persistence.{ UserRepository, PasswordRepository, AuthTokenRepository }

@Singleton
class SigninController @Inject()(
  cc:                  ControllerComponents,
  userRepository:      UserRepository,
  passwordRepository:  PasswordRepository,
  authTokenRepository: AuthTokenRepository,
)(implicit ex: ExecutionContext)
extends AbstractController(cc) with I18nSupport {

  val form = FormSignin.signinForm

  def index() = Action { implicit req =>
    val vv = ViewValueSignin(
      form = form
    )
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
          // authToken = AuthToken(
          //   userId = userOpt.map(_.id).get,
          //   token  = token
          // )
          // _              <- authTokenRepository.post(authToken)
        } yield {
          (userOpt.isDefined, passwordOpt.isDefined) match {
            case (true, true) => {
              val token    = UUID.randomUUID()
              Redirect(routes.TopController.show()).withSession(req.session + ("login_id" -> token.toString()))
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

  def getSession() = Action { implicit req =>
     req.session.get("user_id").map { data =>
         Ok("save session page access time:" + data)
     }.getOrElse {
         Ok("you have never access in save session page.")
     }
  }
}

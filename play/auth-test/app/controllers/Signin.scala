package controllers

import javax.inject._
import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }
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
          // authToken = AuthToken(
          //   userId = userOpt.map(_.id).get,
          //   token  = req.session.toString
          // )
          // _              <- authTokenRepository.post(authToken)
        } yield {
          (userOpt.isDefined, passwordOpt.isDefined) match {
            case (true, true) => {
              println{
                Redirect(routes.TopController.show()).withSession(req.session + ("connected" -> "testtest"))
              }
              Redirect(routes.TopController.show()).withSession(req.session + ("connected" -> "testtest"))
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
     req.session.get("connected").map { data =>
         Ok("save session page access time:" + data)
     }.getOrElse {
         Ok("you have never access in save session page.")
     }
  }
}

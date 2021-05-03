package controllers

import javax.inject._

import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }

import models.ViewValueTop

@Singleton
class TopController @Inject() (
  cc: ControllerComponents,
) extends AbstractController(cc) with I18nSupport {

  def show() = Action { implicit req =>

    val vv = ViewValueTop(
      content = "Welcome to Top Page",
      session = req.session.get("connected").getOrElse("")
    )

    Ok(views.html.Top(vv))
  }

}

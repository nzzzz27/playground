package controllers

import javax.inject._

// import play.api.i18n.I18nSupport
import play.api.mvc.{ ControllerComponents, AbstractController }

import models.ViewValueTop

@Singleton
class TopController @Inject() (
  cc:                  ControllerComponents,
  authenticatedAction: AuthenticatedAction,
) extends AbstractController(cc) {

  def show() = authenticatedAction { implicit req =>

    val vv = ViewValueTop(
      content = "Welcome to Top Page",
      session = req.session.get("connected").getOrElse("")
    )

    Ok(views.html.Top(vv))
  }

}

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
      content = "会員専用コンテンツ",
    )

    Ok(views.html.Top(vv))
  }

}

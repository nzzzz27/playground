package models

import play.api.data._
import play.api.data.Forms._

import models.{ ViewValueSiteLayout, User }

case class ViewValueLogin(
  userForm: Form[User],
) extends ViewValueSiteLayout

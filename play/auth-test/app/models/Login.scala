package models

import play.api.data._
import play.api.data.Forms._

import models.{ ViewValueSiteLayout, ViewValueUser }

case class ViewValueLogin(
  userForm: Form[ViewValueUser],
) extends ViewValueSiteLayout

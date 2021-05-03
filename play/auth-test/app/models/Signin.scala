package models

import play.api.data._
import play.api.data.Forms._

import models.form.FormSignin

case class ViewValueSignin(
  form:          Form[FormSignin],
  errorMessage:  String = "",
) extends ViewValueSiteLayout

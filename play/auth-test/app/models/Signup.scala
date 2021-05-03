package models

import java.time.LocalDateTime
import play.api.data.Form

import models.{ ViewValueSiteLayout, FormSignup }

case class ViewValueSignup(
  form:  Form[FormSignup]
) extends ViewValueSiteLayout

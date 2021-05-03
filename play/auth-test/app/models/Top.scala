package models

import play.api.mvc.Session

import models.ViewValueSiteLayout

case class ViewValueTop(
  content:  String,
  session:  String,
) extends ViewValueSiteLayout

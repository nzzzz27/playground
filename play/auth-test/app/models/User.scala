package models

import models.ViewValueSiteLayout

case class User(
  name:     String,
  email:    String,
  passeord: String
) extends ViewValueSiteLayout

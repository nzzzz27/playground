package models

import lib.model.User
import lib.model.Password

case class ViewValuePassword(
  id:           Password.Id,
  userId:       User.Id,
  password:     String,
)

object ViewValuePassword {
  import play.api.data._
  import play.api.data.Forms._
  import play.api.data.validation.Constraints._

  val passwordForm = Form(
    mapping(
      "id"           -> optional(longNumber),
      "userId"       -> optional(longNumber),
      "password"     -> nonEmptyText,
    )(ViewValuePassword.apply)(ViewValuePassword.unapply)
  )
}

package models

import java.time.LocalDateTime

case class FormSignup(
  firstName:  String,
  lastName:   String,
  email:      String,
  password:   String,
)

object FormSignup {
  import play.api.data.Form
  import play.api.data.Forms._

  val signupForm: Form[FormSignup] = Form(
    mapping(
      "firstName"    -> nonEmptyText,
      "lastName"     -> nonEmptyText,
      "email"        -> email,
      "password"     -> nonEmptyText,
    )(FormSignup.apply)(FormSignup.unapply)
  )
}

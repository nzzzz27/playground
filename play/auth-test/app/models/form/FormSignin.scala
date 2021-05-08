package models.form

import java.time.LocalDateTime
import java.util.UUID

case class FormSignin(
  firstName:  String,
  lastName:   String,
  email:      String,
  password:   String,
  token:      Option[UUID],
) {
  def create(firstName: String, lastName: String, email: String, password: String, token: Option[UUID]): FormSignin = {
    new FormSignin(
      firstName,
      lastName,
      email,
      password,
      token,
    )
  }
}

object FormSignin {
  import play.api.data.Form
  import play.api.data.Forms._

  val signinForm: Form[FormSignin] = Form(
    mapping(
      "firstName"    -> nonEmptyText,
      "lastName"     -> nonEmptyText,
      "email"        -> email,
      "password"     -> nonEmptyText,
      "token"        -> optional(uuid),
    )(FormSignin.apply)(FormSignin.unapply)
  )
}

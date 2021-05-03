package models.form

import java.time.LocalDateTime

case class FormSignin(
  firstName:  String,
  lastName:   String,
  email:      String,
  password:   String,
  token:      Option[String],
) {
  def create(firstName: String, lastName: String, email: String, password: String, token: Option[String]): FormSignin = {
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
      "token"        -> optional(text),
    )(FormSignin.apply)(FormSignin.unapply)
  )
}

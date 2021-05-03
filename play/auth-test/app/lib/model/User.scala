package lib.model

import java.time.LocalDateTime

import User._
case class User(
  id:         Id,
  firstName:  String,
  lastName:   String,
  email:      String,
)

object User {
  type Id = Option[Long]

  def apply(firstName: String, lastName: String, email: String) = {
    new User(
      id = None,
      firstName,
      lastName,
      email,
    )
  }
}

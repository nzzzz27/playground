package lib.model

import java.time.LocalDateTime
import lib.model.User

import Password._
case class Password(
  id:         Id,
  userId:     User.Id,
  password:   String,
  createdAt:  LocalDateTime = null,
  updatedAt:  LocalDateTime = null
)

object Password {
  type Id = Option[Long]

  def apply(userId: User.Id, password: String) = {
    new Password(
      id = None,
      userId,
      password,
    )
  }
}

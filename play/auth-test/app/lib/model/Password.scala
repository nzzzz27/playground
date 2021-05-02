package lib.model

import java.time.LocalDateTime
import lib.model.User

import Password._
case class Password(
  id:         Id,
  userId:     User.Id,
  password:   String,
  createdAt:  LocalDateTime,
  updatedAt:  LocalDateTime
)

object Password {
  type Id = Option[Long]
}

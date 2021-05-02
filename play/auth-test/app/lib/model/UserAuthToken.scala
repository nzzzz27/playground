package lib.model

import java.time.LocalDateTime
import lib.model.User

import UserAuthToken._
case class UserAuthToken(
  id:         Id,
  userId:     User.Id,
  tolen:      String,
  createdAt:  LocalDateTime,
  updatedAt:  LocalDateTime
)

object UserAuthToken {
  type Id = Option[Long]
}

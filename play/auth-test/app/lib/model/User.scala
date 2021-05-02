package lib.model

import java.time.LocalDateTime

import User._
case class User(
  id:         Id,
  firstName:  String,
  lastName:   String,
  email:      String,
  createdAt:  LocalDateTime,
  updatedAt:  LocalDateTime
)

object User {
  type Id = Option[Long]
}

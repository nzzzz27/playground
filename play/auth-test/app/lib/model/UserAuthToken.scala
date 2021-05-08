package lib.model

import java.util.UUID
import play.api.mvc.Session
import lib.model.User

import AuthToken._
case class AuthToken(
  id:         Id,
  userId:     User.Id,
  token:      String,
)

object AuthToken {
  type Id = Option[Long]

  def apply(userId: User.Id, token: UUID) = {
    new AuthToken(
      id = None,
      userId,
      token.toString(),
    )
  }
}

package lib.persistence

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Future, ExecutionContext }

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import lib.persistence.db.UserAuthTokenTable
import lib.model.UserAuthToken

class UserAuthTokenRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  table:                          UserAuthTokenTable
)(implicit ec: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {

  def getById(id: UserAuthToken.Id): Future[Option[UserAuthToken]] = {
    db.run {
      table.query.filter(_.id === id).result.headOption
    }
  }

}

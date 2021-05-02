package lib.persistence

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Future, ExecutionContext }

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import lib.persistence.db.UserTable
import lib.model.User

class UserRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  table:                          UserTable
)(implicit ec: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {

  def getById(id: User.Id): Future[Option[User]] = {
    db.run {
      table.query.filter(_.id === id).result.headOption
    }
  }

}

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

  private val query = table.query

  def getById(id: User.Id): Future[Option[User]] = {
    db.run {
      query.filter(_.id === id).result.headOption
    }
  }

  def getByData(user: User): Future[Option[User]] = {
    db.run {
      query.filter(u => (u.firstName === user.firstName) && (u.lastName === user.lastName) && (u.email === user.email))
        .result.headOption
    }
  }

  def post(data: User): Future[User.Id] = {
    db.run {
      query returning query.map(_.id) += data
    }
  }

}

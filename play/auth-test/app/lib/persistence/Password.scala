package lib.persistence

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Future, ExecutionContext }

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import lib.persistence.db.PasswordTable
import lib.model.Password

class PasswordRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  table:                          PasswordTable
)(implicit ec: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {

  private val query = table.query

  def getById(id: Password.Id): Future[Option[Password]] = {
    db.run {
      query.filter(_.id === id).result.headOption
    }
  }

  def post(data: Password): Future[Password.Id] = {
    db.run {
      query returning query.map(_.id) += data
    }
  }
}

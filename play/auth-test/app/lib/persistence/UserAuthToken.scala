package lib.persistence

import javax.inject.{ Inject, Singleton }
import scala.concurrent.{ Future, ExecutionContext }

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.MySQLProfile.api._
import slick.jdbc.JdbcProfile

import lib.persistence.db.AuthTokenTable
import lib.model.AuthToken

class AuthTokenRepository @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider,
  table:                          AuthTokenTable
)(implicit ec: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {

  private val query = table.query

  def getById(id: AuthToken.Id): Future[Option[AuthToken]] = {
    db.run {
      query.filter(_.id === id).result.headOption
    }
  }

  def post(data: AuthToken): Future[AuthToken.Id] = {
    db.run {
      query returning query.map(_.id) += data
    }
  }

}

package lib.persistence.db

import javax.inject.Inject
import java.util.UUID
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile
import play.api.mvc.Session

import lib.model.{ AuthToken, User }

class AuthTokenTable @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  type TableElementTuple = (
    AuthToken.Id,
    User.Id,
    String,
  )

  protected class TableColumn(tag: Tag) extends Table[AuthToken](tag, "UserAuthToken") {
    def id         = column[AuthToken.Id]("id", O.PrimaryKey, O.AutoInc)
    def userId     = column[User.Id]     ("user_id")
    def token      = column[String]      ("token", O.Unique)

    def userIdForeignKey = foreignKey("user_id", id, query)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, userId, token).<>(
      // Tuple(table) => Model
      (t: TableElementTuple) => AuthToken(
        t._1, t._2, t._3
      ),
      // Model => Tuple(table)
      (v: AuthToken) => AuthToken.unapply(v).map { t => (
        t._1, t._2, t._3
      )}
    )
  }
  val query = TableQuery[TableColumn]

}

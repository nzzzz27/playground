package lib.persistence.db

import javax.inject.Inject
import java.time.LocalDateTime
import java.sql.Timestamp
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile

import lib.model.{ UserAuthToken, User }

class UserAuthTokenTable @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  type TableElementTuple = (
    UserAuthToken.Id,
    User.Id,
    String,
    LocalDateTime,
    LocalDateTime,
  )

  protected class TableColumn(tag: Tag) extends Table[UserAuthToken](tag, "UserAuthToken") {
    def id         = column[UserAuthToken.Id]("id", O.PrimaryKey, O.AutoInc)
    def userId     = column[User.Id]         ("user_id")
    def token      = column[String]          ("token", O.Unique)
    def createdAt  = column[LocalDateTime]   ("created_at")
    def updatedAt  = column[LocalDateTime]   ("updated_at")

    def userIdForeignKey = foreignKey("user_id", id, query)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, userId, token, createdAt, updatedAt).<>(
      // Tuple(table) => Model
      (t: TableElementTuple) => UserAuthToken(
        t._1, t._2, t._3, t._4, t._5
      ),
      // Model => Tuple(table)
      (v: UserAuthToken) => UserAuthToken.unapply(v).map { t => (
        t._1, t._2, t._3, t._4, t._5
      )}
    )
  }
  val query = TableQuery[TableColumn]

}

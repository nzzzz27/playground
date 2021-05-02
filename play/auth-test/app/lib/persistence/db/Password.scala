package lib.persistence.db

import javax.inject.Inject
import java.time.LocalDateTime
import java.sql.Timestamp
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile

import lib.model.{ Password, User }

class PasswordTable @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  type TableElementTuple = (
    Password.Id,
    User.Id,
    String,
    LocalDateTime,
    LocalDateTime,
  )

  protected class TableColumn(tag: Tag) extends Table[Password](tag, "Password") {
    def id         = column[Password.Id]  ("id", O.PrimaryKey, O.AutoInc)
    def userId     = column[User.Id]      ("user_id")
    def password   = column[String]       ("password")
    def createdAt  = column[LocalDateTime]("created_at")
    def updatedAt  = column[LocalDateTime]("updated_at")

    def userIdForeignKey = foreignKey("user_id", id, query)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, userId, password, createdAt, updatedAt).<>(
      // Tuple(table) => Model
      (t: TableElementTuple) => Password(
        t._1, t._2, t._3, t._4, t._5
      ),
      // Model => Tuple(table)
      (v: Password) => Password.unapply(v).map { t => (
        t._1, t._2, t._3, t._4, t._5
      )}
    )
  }
  val query = TableQuery[TableColumn]

}

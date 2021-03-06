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
  )

  protected class TableColumn(tag: Tag) extends Table[Password](tag, "Password") {
    def id         = column[Password.Id]  ("id", O.PrimaryKey, O.AutoInc)
    def userId     = column[User.Id]  ("user_id")
    def password   = column[String]       ("password")

    def useraa = foreignKey("user_id", id, query)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, userId, password).<>(
      // Tuple(table) => Model
      (t: TableElementTuple) => Password(
        t._1, t._2, t._3
      ),
      // Model => Tuple(table)
      (v: Password) => Password.unapply(v).map { t => (
        t._1, t._2, t._3
      )}
    )
  }
  val query = TableQuery[TableColumn]

}

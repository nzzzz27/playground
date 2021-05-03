package lib.persistence.db

import javax.inject.Inject
import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.jdbc.JdbcProfile

import lib.model.User

class UserTable @Inject()(
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  type TableElementTuple = (
    User.Id,
    String,
    String,
    String,
  )

  protected class TableColumn(tag: Tag) extends Table[User](tag, "User") {
    def id         = column[User.Id]      ("id", O.PrimaryKey, O.AutoInc)
    def firstName  = column[String]       ("first_name")
    def lastName   = column[String]       ("last_name")
    def email      = column[String]       ("email")

    def * = (id, firstName, lastName, email).<>(
      // Tuple(table) => Model
      (t: TableElementTuple) => User(
        t._1, t._2, t._3, t._4
      ),
      // Model => Tuple(table)
      (v: User) => User.unapply(v).map { t => (
        t._1, t._2, t._3, t._4
      )}
    )
  }
  val query = TableQuery[TableColumn]

}

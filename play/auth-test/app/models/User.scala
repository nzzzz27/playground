package models

import lib.model.User

case class ViewValueUser(
  id:       User.Id,
  name:     String,
  email:    String,
)

package models

import lib.model.User
import lib.model.Password

case class ViewValueUser(
  id:           User.Id,
  firstName:    String,
  lastName:     String,
  email:        String,
) {}

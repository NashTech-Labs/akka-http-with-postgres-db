package models

case class User(
                 userId: Int,
                 firstName: String,
                 lastName: String,
                 email: String,
                 phoneNumber: Long,
                 address: String
               )
package dao

import models.User

trait DAO {

  def addUser(user: User): String

  def getUserById(userId: Int): Option[User]

  def getAllUsers: List[User]

  def updateFirstNameById(userId: Int, valueToUpdate: String): String

  def deleteUserById(userID: Int): String

}
package db

import dao.DAO
import models.User

import scala.util.{Failure, Success, Try}
import java.sql.{ResultSet, Statement}
import scala.annotation.tailrec

class UserDB extends DAO {

  private val statement: Statement = Connection.connection().createStatement()

  def addUser(user: User): String = {
    getUserById(user.userId) match {
      case Some(value) => s"User ${value.userId} is Already exists!!!"
      case None =>
        val query =
          s"""
             |INSERT INTO
             |userInfo (userId, firstName, lastName, email, phoneNumber, address)
             |VALUES (${user.userId},'${user.firstName}', '${user.lastName}', '${user.email}', ${user.phoneNumber}, '${user.address}');
             |"""
            .stripMargin
        Try(statement.executeQuery(query))
        s"User ${user.userId} is Inserted Successfully!!!"
    }
  }

  def getUserById(userId: Int): Option[User] = {
    val query =
      s"""
         |SELECT *
         |FROM userInfo
         |WHERE userId=$userId;
         |"""
        .stripMargin
    queryExecution(query) match {
      case value: List[User] if value.length == 1 => Some(value.head)
      case _ => None
    }
  }

  def getAllUsers: List[User] = {
    val query =
      """
        |SELECT *
        |FROM userInfo
        |ORDER BY userId;
        |"""
        .stripMargin
    queryExecution(query)
  }

  private def queryExecution(query: String): List[User] = {
    Try(statement.executeQuery(query)) match {
      case Success(resultSet) => resultSetToList(resultSet, List())
      case Failure(_) => List()
    }
  }

  @tailrec
  private def resultSetToList(resultSet: ResultSet, userList: List[User]): List[User] = {
    if (resultSet.next()) {
      val userId = resultSet.getInt("userId")
      val firstName = resultSet.getString("firstName")
      val lastName = resultSet.getString("lastName")
      val email = resultSet.getString("email")
      val phoneNumber = resultSet.getLong("phoneNumber")
      val address = resultSet.getString("address")

      val updatedUserList = userList ::: List(User(userId, firstName, lastName, email, phoneNumber, address))
      resultSetToList(resultSet, updatedUserList)
    } else userList
  }

  def updateFirstNameById(userId: Int, valueToUpdate: String): String = {
    getUserById(userId) match {
      case Some(value) =>
        val query =
          s"""
             |UPDATE userInfo
             |SET firstName = '$valueToUpdate'
             |WHERE userId = ${value.userId};
             |"""
            .stripMargin
        Try(statement.executeQuery(query))
        s"User $userId is Updated Successfully!!!"
      case None => s"User Doesn't exists."
    }
  }

  def deleteUserById(userId: Int): String = {
    getUserById(userId) match {
      case Some(value) =>
        val query =
          s"""
             |DELETE
             |FROM userInfo
             |WHERE userId = ${value.userId};
             |"""
            .stripMargin
        Try(statement.executeQuery(query))
        s"User $userId is Deleted Successfully!!!"
      case None => s"User Doesn't exists."
    }
  }
}
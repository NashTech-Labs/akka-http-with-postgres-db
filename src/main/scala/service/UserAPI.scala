package service

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import models.User
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import db.UserDB

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object UserAPI extends App {


  private val userRepo = new UserDB

  private val host = "localhost"
  private val port = 8081
  implicit val system: ActorSystem = ActorSystem("HTTP_SERVER")

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  implicit val reads: RootJsonFormat[User] = jsonFormat6(User)

  private val route = {
    pathPrefix("api") {
      /* API to create new user */
      post {
        path("create-user") {
          entity(as[User]) { user =>
            val result = userRepo.addUser(user)
            complete(StatusCodes.OK, s"$result")
          }
        }
      } ~
        /* API to get all users */
        get {
          path("get-all-users") {
            val user = userRepo.getAllUsers
            complete(StatusCodes.OK, s"User Details: \n$user")
          }
        } ~
        /* API to get user by the user id */
        get {
          path("get-user-by-id" / IntNumber) { id =>
            val userData = userRepo.getUserById(id)
            complete(StatusCodes.OK, s"User Details: \n$userData")
          }
        } ~
        /* API to delete user by the user id */
        delete {
          path("delete-user-by-id" / IntNumber) { id =>
            val result = userRepo.deleteUserById(id)
            complete(StatusCodes.OK, s"$result")
          }
        } ~
        /* to update username by the user id */
        patch {
          path("update-user-name" / IntNumber / Segment) { (id, valueToUpdate) =>
            val result = userRepo.updateFirstNameById(id, valueToUpdate)
            complete(StatusCodes.OK, s"$result")
          }
        }
    }
  }

  private val bindingFuture = Http().newServerAt(host, port).bindFlow(route)

  bindingFuture.onComplete {
    case Success(_) =>
      println(s"Server is listening on http://$host:$port/api/")
    case Failure(exception) =>
      println(s"Failure :$exception")
      system.terminate()
  }
}
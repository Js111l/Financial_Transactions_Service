package tasks

import akka.actor.Actor

class TaskActor extends Actor {
  def receive: Receive = {
    case "productAlertTask" => {
     // val userIds = userRepository.fetchUsersWithStockAlertSubscription();


      println("Task executed at: " + System.currentTimeMillis())
    }

    case _ => {

    }
  }
}

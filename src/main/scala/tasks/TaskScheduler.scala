package tasks

import akka.actor.{ActorSystem, Props}

import scala.concurrent.duration.DurationInt

class TaskScheduler() {

  def startScheduler(system: ActorSystem): Unit = {
    val scheduledActor = system.actorOf(Props[TaskActor], "myScheduledActor")

//    system.scheduler.scheduleWithFixedDelay(
//      initialDelay = 0.millisecond,
//      delay = 100.millisecond,
//      receiver = scheduledActor,
//      message = "productAlertTask"
//    )

  }
}

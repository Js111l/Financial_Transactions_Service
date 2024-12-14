package ecom.service

import com.google.inject.Singleton
import ecom.Main.system
import ecom.actors.model.{OrderDetailsModel, UserOrderListModel, UserOrdersModel}
import ecom.dao.entities.Order
import ecom.dao.repository.UserOrderRepository
import jakarta.inject.Inject

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

@Singleton
class OrderService @Inject()(orderRepository: UserOrderRepository) {

  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  def getOrderListForUser(userId: Long): Future[List[UserOrdersModel]] = { //to raczej ma byc lista zamowien a nie jeden model
    orderRepository.getUserOrdersList(userId)
  }

  def getOrderDetails(orderId: Long): Future[OrderDetailsModel] = {
    orderRepository.getOrderDetails(orderId)
  }

}

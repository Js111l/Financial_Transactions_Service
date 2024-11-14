package ecom.service

import com.google.inject.Singleton
import ecom.actors.model.{UserOrderListModel, UserOrdersModel}
import ecom.dao.repository.UserOrderRepository
import jakarta.inject.Inject

import scala.concurrent.Future

@Singleton
class OrderService @Inject()(orderRepository: UserOrderRepository) {
  def getOrderListForUser(userId: Long): Future[List[UserOrdersModel]] = { //to raczej ma byc lista zamowien a nie jeden model
    orderRepository.getUserOrdersList(userId)
  }

}

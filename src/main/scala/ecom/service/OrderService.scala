package ecom.service

import com.google.inject.Singleton
import ecom.actors.model.UserOrderListModel

@Singleton
class OrderService {
  def getOrderListForUser(userId: Long): List[UserOrderListModel] = {
    List(new UserOrderListModel("iojjoi"))
  }

}

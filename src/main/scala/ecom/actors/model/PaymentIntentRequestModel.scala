package ecom.actors.model

case class PaymentIntentRequestModel(client: CustomerData,
                                     amount: BigDecimal,
                                     localCurrency: String, //todo to enum ?
                                     products: List[ProductModel],
                                     uuid: String
                                    ) {


}

case class ProductModel(id: Long, quantity: Int)

case class CustomerData(id: Long, name: String, email: String, phone: String) {}

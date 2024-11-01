package ecom.actors.model

case class PaymentIntentRequestModel(//paymentUuid: String,
                                     client: CustomerData,
                                     amount: BigDecimal,
                                     localCurrency: String, //todo to enum ?
                                     products: List[ProductModel]
                                    ) {


}

case class ProductModel(id: Long, quantity: Int)

case class CustomerData(id: Long, name: String, email: String, phone: String) {}

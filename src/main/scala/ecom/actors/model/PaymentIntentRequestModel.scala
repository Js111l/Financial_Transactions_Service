package ecom.actors.model

case class PaymentIntentRequestModel(amount: BigDecimal,
                                     localCurrency: String, //todo to enum ?
                                     products: List[ProductModel],
                                     uuid: String,
                                     email: String
                                    ) {


}

case class ProductModel(id: Long, quantity: Int, name: String, price: BigDecimal, imageUrl: String)
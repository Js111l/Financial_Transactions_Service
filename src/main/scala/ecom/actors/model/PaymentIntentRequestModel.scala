package ecom.actors.model

case class PaymentIntentRequestModel(amount: BigDecimal,
                                     localCurrency: String, //todo to enum ?
                                     products: List[ProductModel],
                                     uuid: String,
                                     email: String,
                                     paymentMethod: String,
                                     shippingAddressId: Long,
                                     billingAddressId: Long
                                    ) {


}

case class ProductModel(id: Long, quantity: Int, name: String, price: BigDecimal, imageUrl: String)

case class AddressModel(street: String,
                        streetNumber: Int,
                        apartmentNumber: Option[String] = None,
                        city: String,
                        postalCode: String)
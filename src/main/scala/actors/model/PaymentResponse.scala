package actors.model
case class PaymentResponse(amount: Long,
                           currency: String,
                           status: String,
                           client: Client){

}
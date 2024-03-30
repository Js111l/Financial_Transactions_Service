package actors.model

case class Client(id: Long,
                  firstName: String,
                  lastName: String,
                  email: String,
                  phoneNumber: Long,
                  address: Address
                 ) {

}

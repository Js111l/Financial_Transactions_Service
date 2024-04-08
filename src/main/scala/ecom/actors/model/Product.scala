package ecom.actors.model

import ecom.enums.VatRate.VatRate

case class Product(id: Long,
                   grossAmount: Long,
                   netAmount: Long,
                   vatAmount: Long,
                   vatRate: VatRate,
                   description: String,
                   category: String,

                  ) {

}

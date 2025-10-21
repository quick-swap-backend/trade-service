package quickswap.tradeservice.domain.item

import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Money

data class ItemCreateRequest(
  val id: ProductId,
  val title: String,
  val price: Money,
  val sellerId: String,
  val sellerEmail: Email,
)
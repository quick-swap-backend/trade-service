package quickswap.tradeservice.domain.trade

data class TradeCreateRequest(
  val buyerId: String,
  val sellerId: String,
  val itemId: String,
)
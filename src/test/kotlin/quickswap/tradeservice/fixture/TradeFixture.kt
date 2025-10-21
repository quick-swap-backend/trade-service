package quickswap.tradeservice.fixture

import quickswap.tradeservice.domain.trade.TradeCreateRequest
import java.util.UUID

class TradeFixture {
  companion object {
    fun getTradeCreateRequest(
      buyerId: String = UUID.randomUUID().toString(),
      sellerId: String = UUID.randomUUID().toString(),
      itemId: String = UUID.randomUUID().toString(),
    ): TradeCreateRequest {
      return TradeCreateRequest(
        buyerId = buyerId,
        sellerId = sellerId,
        itemId = itemId
      )
    }
  }
}
package quickswap.tradeservice.application.trade.`in`

import quickswap.tradeservice.domain.trade.Trade

interface TradeCreator {
  fun createTrade(itemId: String): Trade
}
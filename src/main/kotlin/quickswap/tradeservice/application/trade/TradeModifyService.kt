package quickswap.tradeservice.application.trade

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import quickswap.commons.application.shared.ports.out.AuthenticationContext
import quickswap.commons.domain.shared.IdProvider
import quickswap.commons.domain.shared.id.ProductId
import quickswap.tradeservice.application.item.out.ItemRepository
import quickswap.tradeservice.application.trade.`in`.TradeCreator
import quickswap.tradeservice.application.trade.out.TradeRepository
import quickswap.tradeservice.domain.trade.Trade
import quickswap.tradeservice.domain.trade.TradeCreateRequest

@Transactional
@Service
class TradeModifyService(

  private val idProvider: IdProvider,

  private val repository: TradeRepository,

  private val itemRepository: ItemRepository,

  private val authContext: AuthenticationContext,

): TradeCreator {

  override fun createTrade(itemId: String): Trade {

    val foundItem = itemRepository.findById(ProductId(itemId))
      .orElseThrow { IllegalArgumentException("Item 을 찾을 수 없습니다. itemId: $itemId") }

    val trade = Trade.of(idProvider,
      TradeCreateRequest(
        buyerId = authContext.getCurrentUserId().value,
        sellerId = foundItem.sellerId,
        itemId = itemId
      )
    )

    val savedTrade = repository.save(trade)

    foundItem.toUnavailable()

    return savedTrade
  }
}
package quickswap.tradeservice.application.item.`in`

import quickswap.tradeservice.domain.item.Item
import quickswap.tradeservice.domain.item.ItemCreateRequest

interface ItemCreator {
  fun createItem(request: ItemCreateRequest): Item
}
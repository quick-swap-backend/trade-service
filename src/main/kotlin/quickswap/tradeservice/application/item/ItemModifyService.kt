package quickswap.tradeservice.application.item

import org.springframework.stereotype.Service
import quickswap.tradeservice.application.item.`in`.ItemCreator
import quickswap.tradeservice.application.item.out.ItemRepository
import quickswap.tradeservice.domain.item.Item
import quickswap.tradeservice.domain.item.ItemCreateRequest

@Service
class ItemModifyService(
  private val repository: ItemRepository
): ItemCreator {

  override fun createItem(request: ItemCreateRequest): Item {
    val item = Item.of(request)
    return repository.save(item)
  }

}
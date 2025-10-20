package quickswap.tradeservice.application.item.out

import org.springframework.data.jpa.repository.JpaRepository
import quickswap.commons.domain.shared.id.ProductId
import quickswap.tradeservice.domain.item.Item

interface ItemRepository: JpaRepository<Item, ProductId> {
}
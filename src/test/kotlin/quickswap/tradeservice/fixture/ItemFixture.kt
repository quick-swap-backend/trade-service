package quickswap.tradeservice.fixture

import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Money
import quickswap.tradeservice.domain.item.ItemCreateRequest
import java.util.*

class ItemFixture {
  companion object {
    fun getItemCreateRequest(
      id: ProductId = ProductId(UUID.randomUUID().toString()),
      title: String = "갤럭시 s25 팝니다",
      price: Money = Money.of(123456L),
      sellerId: String = UUID.randomUUID().toString(),
      sellerEmail: Email = Email("test@test.com"),
    ): ItemCreateRequest {
      return ItemCreateRequest(
        id = id,
        title = title,
        price = price,
        sellerId = sellerId,
        sellerEmail = sellerEmail
      )
    }
  }
}
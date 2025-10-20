package quickswap.tradeservice.application.trade

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.commons.application.shared.ports.out.AuthenticationContext
import quickswap.commons.domain.shared.IdProvider
import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.id.UserId
import quickswap.tradeservice.application.item.out.ItemRepository
import quickswap.tradeservice.application.trade.out.TradeRepository
import quickswap.tradeservice.domain.item.Item
import quickswap.tradeservice.domain.item.ItemStatus
import quickswap.tradeservice.domain.trade.Trade
import java.util.*
import kotlin.test.assertEquals

class TradeModifyServiceTest() {
  val idProvider = IdProvider { UUID.randomUUID().toString() }
  val repository: TradeRepository = mockk()
  val itemRepository: ItemRepository = mockk()
  val authContext: AuthenticationContext = mockk()
  val creator = TradeModifyService(idProvider, repository, itemRepository, authContext)

  @Test
  fun `존재하는 item 에 거래신청 시 성공해야 한다`() {
    val itemId = UUID.randomUUID().toString()
    val sellerId = UUID.randomUUID().toString()
    val buyerId = UUID.randomUUID().toString()
    val mockItem = mockk<Item> {
      every { this@mockk.sellerId } returns sellerId
      every { this@mockk.toUnavailable() } returns Unit
    }
    val mockTrade = mockk<Trade>()

    every { itemRepository.findById(ProductId(itemId)) } returns Optional.of(mockItem)
    every { authContext.getCurrentUserId() } returns UserId(buyerId)
    every { repository.save(any()) } returns mockTrade

    val result = creator.createTrade(itemId)

    verify { itemRepository.findById(ProductId(itemId)) }
    verify { mockItem.toUnavailable() }
    verify { repository.save(any()) }
    assertEquals(mockTrade, result)
  }

  @Test
  fun `존재하지 않는 item 에 거래신청 할 경우 예외가 발생해야 한다`() {
    every { itemRepository.findById(any<ProductId>()) } returns Optional.empty()

    assertThrows<IllegalArgumentException> {
      creator.createTrade(UUID.randomUUID().toString())
    }
  }

  @Test
  fun `거래생성후 item 의 상태변경`() {
    val itemId = UUID.randomUUID().toString()
    val sellerId = UUID.randomUUID().toString()
    val buyerId = UUID.randomUUID().toString()

    var itemStatus = ItemStatus.AVAILABLE

    val mockItem = mockk<Item> {
      every { this@mockk.sellerId } returns sellerId
      every { this@mockk.status } answers { itemStatus }
      every { this@mockk.toUnavailable() } answers {
        itemStatus = ItemStatus.UNAVAILABLE
      }
    }

    val mockTrade = mockk<Trade>()

    every { itemRepository.findById(ProductId(itemId)) } returns Optional.of(mockItem)
    every { authContext.getCurrentUserId() } returns UserId(buyerId)
    every { repository.save(any()) } returns mockTrade

    val result = creator.createTrade(itemId)

    verify { mockItem.toUnavailable() }
    assertEquals(ItemStatus.UNAVAILABLE, mockItem.status)
  }
}






















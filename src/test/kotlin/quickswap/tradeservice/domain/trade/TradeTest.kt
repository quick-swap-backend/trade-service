package quickswap.tradeservice.domain.trade

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.commons.domain.shared.IdProvider
import quickswap.tradeservice.fixture.TradeFixture.Companion.getTradeCreateRequest
import java.util.UUID

class TradeTest {

  val idProvider: IdProvider = IdProvider { UUID.randomUUID().toString() }

  @Test
  fun `Trade 판매자와 구매자가 동일한 경우 예외발생`() {
    val uuid = UUID.randomUUID().toString()
    val request = getTradeCreateRequest(buyerId = uuid, sellerId = uuid)

    assertThrows<IllegalArgumentException> {
      Trade.of(idProvider, request)
    }
  }

  @Test
  fun `거래 진행중 상태가 아닐때 거래완료처리 하면 예외발생`() {
    val trade = Trade.of(idProvider, getTradeCreateRequest())
    trade.completed()
    assertThrows<IllegalArgumentException> { trade.completed() }

    val trade2 = Trade.of(idProvider, getTradeCreateRequest())
    trade2.canceled()
    assertThrows<IllegalArgumentException> { trade2.completed() }
  }

  @Test
  fun `거래 진행중 상태가 아닐때 거래취소처리 하면 예외발생`() {
    val trade = Trade.of(idProvider, getTradeCreateRequest())
    trade.completed()
    assertThrows<IllegalArgumentException> { trade.canceled() }

    val trade2 = Trade.of(idProvider, getTradeCreateRequest())
    trade2.canceled()
    assertThrows<IllegalArgumentException> { trade2.canceled() }
  }
}
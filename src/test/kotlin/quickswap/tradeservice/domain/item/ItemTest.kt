package quickswap.tradeservice.domain.item

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import quickswap.tradeservice.fixture.ItemFixture.Companion.getItemCreateRequest

class ItemTest {
  @Test
  fun `이미 거래가능한 물건은 Item 가능한 상태로 만들 수 없다`() {
    val item = Item.of(getItemCreateRequest())

    assertThrows<IllegalArgumentException> { item.toAvailable() }
  }

  @Test
  fun `이미 거래불가능한 Item 은 거래불가능한 상태로 만들 수 없다`() {
    val item = Item.of(getItemCreateRequest())
    item.toUnavailable()

    assertThrows<IllegalArgumentException> { item.toUnavailable() }
  }

  @Test
  fun `Item 상태변경 성공 테스트`() {
    val item = Item.of(getItemCreateRequest())
    item.toUnavailable()
    item.toAvailable()
  }

  @Test
  fun `Item 이 생성되면 처음에는 거래가능한 상태이다`() {
    val item = Item.of(getItemCreateRequest())
    assert(item.status == ItemStatus.AVAILABLE)
  }
}
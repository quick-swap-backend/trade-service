package quickswap.tradeservice.domain.item

import jakarta.persistence.*
import quickswap.commons.adapter.shared.persistence.converter.EmailConverter
import quickswap.commons.adapter.shared.persistence.converter.MoneyConverter
import quickswap.commons.domain.shared.LogActions.FAIL
import quickswap.commons.domain.shared.LogHelper.format
import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Money

@Table(name = "items")
@Entity
class Item private constructor(
  @EmbeddedId
  val productId: ProductId,

  @Column(name = "title", nullable = false, length = 255)
  val title: String,

  @Column(name = "price", nullable = false)
  @Convert(converter = MoneyConverter::class)
  val price: Money,

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private var _status: ItemStatus = ItemStatus.AVAILABLE,

  @Column(name = "sellerId", nullable = false)
  val sellerId: String,

  @Column(name = "email", nullable = false)
  @Convert(converter = EmailConverter::class)
  val sellerEmail: Email,
) {
  val status: ItemStatus
    get() = _status

  fun toAvailable() {
    require(_status == ItemStatus.UNAVAILABLE) {
      format(FAIL, "funCall", "Item.toAvailable", ("Item.status" to _status))
    }
    _status = ItemStatus.AVAILABLE
  }

  fun toUnavailable() {
    require(_status == ItemStatus.AVAILABLE) {
      format(FAIL, "funCall", "Item.toUnavailable", ("Item.status" to _status))
    }
    _status = ItemStatus.UNAVAILABLE
  }

  companion object {
    fun of(request: ItemCreateRequest): Item {
      return Item(
        productId = request.id,
        title = request.title,
        price = request.price,
        sellerId = request.sellerId,
        sellerEmail = request.sellerEmail,
      )
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Item

    return productId == other.productId
  }

  override fun hashCode(): Int {
    return productId.hashCode()
  }
}
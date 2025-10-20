package quickswap.tradeservice.domain.trade

import jakarta.persistence.*
import quickswap.commons.domain.shared.IdProvider
import quickswap.commons.domain.shared.LogActions
import quickswap.commons.domain.shared.LogHelper.format
import quickswap.commons.domain.shared.id.TradeId
import quickswap.tradeservice.domain.trade.TradeStatus.PROCEED
import java.time.LocalDateTime

/*
* Item 은 product.created 메세지에 의해 생성 되므로
* Trade 와 life cycle 이 다름.
* 이에 따라 하위 도메인이 아닌 별도의 도메인으로 분리 하는 것이 옳다고 판단 하였고,
* FK 연관 관계 없이 별도의 컬럼에 PK만 보관 하도록 설계 하였음.
* */
@Table(name = "trades")
@Entity
class Trade private constructor(

  @EmbeddedId
  val id: TradeId,

  @Column(name = "buyerId", nullable = false)
  val buyerId: String,

  @Column(name = "sellerId", nullable = false)
  val sellerId: String,

  @Column(name = "itemId", nullable = false)
  val itemId: String,

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private var _status: TradeStatus = PROCEED,

  @Column(name = "createdAt", nullable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "updatedAt", nullable = false)
  val updatedAt: LocalDateTime = LocalDateTime.now(),

  ) {

  val status: TradeStatus
    get() = _status

  fun completed() {
    require(_status == PROCEED) { format(LogActions.FAIL, "funCall", "completed()", "tradeId" to id.value) }
    _status = TradeStatus.COMPLETED
  }

  fun canceled() {
    require(_status == PROCEED) { format(LogActions.FAIL, "funCall", "canceled()", "tradeId" to id.value) }
    _status = TradeStatus.CANCELED
  }

  companion object {
    fun of(idProvider: IdProvider, request: TradeCreateRequest): Trade {

      if (request.buyerId == request.sellerId) {
        throw IllegalArgumentException("판매자와 구매자가 동일할 수 없습니다. id:${request.buyerId}")
      }

      return Trade(
        id = TradeId(idProvider.provide()),
        buyerId = request.buyerId,
        sellerId = request.sellerId,
        itemId = request.itemId,
      )
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Trade

    return id == other.id
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}
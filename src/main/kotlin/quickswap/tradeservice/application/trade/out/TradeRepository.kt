package quickswap.tradeservice.application.trade.out

import org.springframework.data.jpa.repository.JpaRepository
import quickswap.commons.domain.shared.id.TradeId
import quickswap.tradeservice.domain.trade.Trade

interface TradeRepository: JpaRepository<Trade, TradeId> {
}
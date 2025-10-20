package quickswap.tradeservice.adapter.webapi

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import quickswap.tradeservice.application.trade.`in`.TradeCreator

@RequestMapping("/api/v1")
@RestController
class TradeController(

  private val creator: TradeCreator

) {

  @PostMapping("/trade/{productId}")
  fun createTrade(@PathVariable(name = "productId") productId: String): ResponseEntity<String> {
    val trade = creator.createTrade(productId)
    return ResponseEntity.ok(trade.id.value)
  }

}
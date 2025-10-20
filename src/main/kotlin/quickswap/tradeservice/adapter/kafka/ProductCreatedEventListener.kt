package quickswap.tradeservice.adapter.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import quickswap.commons.adapter.shared.kafka.KafkaEventHandler
import quickswap.commons.adapter.shared.kafka.KafkaTopics
import quickswap.commons.domain.shared.id.ProductId
import quickswap.commons.domain.shared.vo.Email
import quickswap.commons.domain.shared.vo.Money
import quickswap.tradeservice.application.item.`in`.ItemCreator
import quickswap.tradeservice.domain.item.ItemCreateRequest

@Component
class ProductCreatedEventListener(
  private val itemCreator: ItemCreator,
  private val kafkaEventHandler: KafkaEventHandler
) {

  @KafkaListener(
    topics = [KafkaTopics.PRODUCT_CREATED],
    groupId = KafkaGroupIds.ITEM_UPDATER,
    containerFactory = "kafkaListenerContainerFactory"
  )
  fun productCreated(message: String) {
    kafkaEventHandler.handle<ProductCreatedEvent>(message) { event ->
      itemCreator.createItem(
        ItemCreateRequest(
          id = ProductId(event.id),
          title = event.title,
          price = Money.of(event.price.toString()),
          sellerId = event.sellerId,
          sellerEmail = Email(event.sellerEmail)
        )
      )
    }
  }
}

data class ProductCreatedEvent(
  val id: String,
  val title: String,
  val price: Int,
  val category: String,
  val sellerId: String,
  val sellerEmail: String
)
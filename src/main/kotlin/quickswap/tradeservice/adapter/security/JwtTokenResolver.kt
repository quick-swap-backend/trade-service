package quickswap.tradeservice.adapter.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import quickswap.commons.adapter.shared.security.AbstractJwtTokenResolver

@Configuration
class JwtTokenResolver(
  @Value("\${jwt.secret}") private val secretKey: String,
): AbstractJwtTokenResolver() {

  override fun getSecretKey(): String {
    return secretKey
  }
}
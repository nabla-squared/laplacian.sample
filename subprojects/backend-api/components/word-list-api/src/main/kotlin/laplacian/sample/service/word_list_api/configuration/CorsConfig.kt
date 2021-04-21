package laplacian.sample.service.word_list_api.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.cors.reactive.CorsWebFilter

@Configuration
class CorsConfig {
    @Value("\${cors.allowed-origins:*}")
    lateinit var allowedOrigins: String

    @Bean
    fun corsWebFilter(): CorsWebFilter {
        val corsConfig = CorsConfiguration().apply {
            applyPermitDefaultValues()
            this.allowCredentials = true
            this.allowedOrigins = allowedOrigins
        }
        val source = UrlBasedCorsConfigurationSource().apply {
             registerCorsConfiguration("/**", corsConfig)
        }
        return CorsWebFilter(source);
    }
}

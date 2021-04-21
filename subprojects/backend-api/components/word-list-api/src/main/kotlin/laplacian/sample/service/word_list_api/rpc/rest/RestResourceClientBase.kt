package laplacian.sample.service.word_list_api.rpc.rest

import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.util.UriBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

import reactor.core.publisher.Signal
import reactor.core.publisher.Mono
import reactor.util.context.Context
import graphql.GraphQLContext
import com.fasterxml.jackson.module.kotlin.*
import java.nio.charset.Charset
import org.slf4j.*

open class RestResourceClientBase {

    fun queryParam(uri: UriBuilder, key: String, value: String) =
        if (value.isEmpty()) uri else uri.queryParam(key, value)

    fun queryParams(uri: UriBuilder, key: String, valuesJson: String): UriBuilder {
        val values = jacksonObjectMapper().readValue<List<String>>(valuesJson)
        return if (values.isEmpty()) uri else values.fold(uri) { acc, each ->
            acc.queryParam(key, each)
        }
    }

    fun JSON(obj:Any?) = jacksonObjectMapper().writeValueAsString(obj)

    fun client(baseUrl: String): WebClient {
        return WebClient
        .builder()
        .exchangeStrategies(
            ExchangeStrategies
            .builder()
            .codecs { it.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) }
            .build()
        )
        .baseUrl(baseUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()
    }


    fun commonRequestSettings(request: WebClient.RequestBodySpec): WebClient.RequestBodySpec =
        request
        .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
        .acceptCharset(Charset.forName("UTF-8"))
        .cookies { cookies ->
            Mono.subscriberContext().map { context ->
                val clientCookie: Map<String, List<String>>? =  context.getOrDefault(HTTP_COOKIES, null)
                clientCookie?.forEach { cookies[it.key] = it.value }
            }
        }

    fun handleResponseCookies(response: ClientResponse, context: Context): Context {
        val responseCookies = response.cookies().map{ (key, values) -> key to values.map{ it.value } }.toMap()
        if (responseCookies.isEmpty()) return context
        val clientCookies: Map<String, List<String>>? = context.getOrDefault(HTTP_COOKIES, null)
        return clientCookies?.let { context.put(HTTP_COOKIES, clientCookies + responseCookies) } ?: context
    }

    protected val log: Logger = LoggerFactory.getLogger(this.javaClass)

    companion object {
        const val HTTP_COOKIES = "HTTP_COOKIES"
    }
}
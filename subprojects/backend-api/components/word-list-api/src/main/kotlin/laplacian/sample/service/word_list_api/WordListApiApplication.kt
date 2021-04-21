package laplacian.sample.service.word_list_api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration



/**
 * The launcher class for the word_list_api service.
 */
@SpringBootApplication(
    exclude=[DataSourceAutoConfiguration::class]
)

class WordListApiApplication

/**
 * The entry point of this service.
 */
fun main(args: Array<String>) {
    SpringApplication.run(WordListApiApplication::class.java, *args)
}

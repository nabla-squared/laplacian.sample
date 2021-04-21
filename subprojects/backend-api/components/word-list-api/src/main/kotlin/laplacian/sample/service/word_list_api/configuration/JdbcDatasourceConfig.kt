package laplacian.sample.service.word_list_api.configuration

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import java.util.concurrent.TimeUnit
import javax.sql.DataSource

/**
 * Datasource configurations.
 */
@Configuration
class JdbcDatasourceConfig {
    @Bean
    @ConfigurationProperties(prefix="datasource.word-list-main-db")
    fun wordListMainDbNativeDatasource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun wordListMainDbDatasource(
        @Qualifier("wordListMainDbNativeDatasource") datasource: DataSource,
        @Value("\${datasource.word-list-main-db.logging.slow-query-threshold-mills:5000}") slowQueryThresholdMills: Long,
    ): DataSource = ProxyDataSourceBuilder
        .create(datasource)
        .logQueryBySlf4j(SLF4JLogLevel.INFO)
        .countQuery()
        .logSlowQueryBySlf4j(slowQueryThresholdMills, TimeUnit.MILLISECONDS, SLF4JLogLevel.WARN)
        .build()
    @Bean
    @ConfigurationProperties(prefix="datasource.word-list-sub-db")
    fun wordListSubDbNativeDatasource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun wordListSubDbDatasource(
        @Qualifier("wordListSubDbNativeDatasource") datasource: DataSource,
        @Value("\${datasource.word-list-sub-db.logging.slow-query-threshold-mills:5000}") slowQueryThresholdMills: Long,
    ): DataSource = ProxyDataSourceBuilder
        .create(datasource)
        .logQueryBySlf4j(SLF4JLogLevel.INFO)
        .countQuery()
        .logSlowQueryBySlf4j(slowQueryThresholdMills, TimeUnit.MILLISECONDS, SLF4JLogLevel.WARN)
        .build()
}
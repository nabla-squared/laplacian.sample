package laplacian.sample.service.word_list_api.configuration

import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

@Configuration
class MybatisConfig {
    @Bean
    fun wordListMainDbSqlSessionFactory(
        @Qualifier("wordListMainDbDatasource") datasource: DataSource
    ): SqlSessionFactory = SqlSessionFactoryBean().also {
        it.setDataSource(datasource)
        it.setMapperLocations(
            *PathMatchingResourcePatternResolver().getResources("classpath*:**/*Repository.xml")
        )
    }.getObject()!!

    @Bean
    fun wordListMainDbSqlSession(
        @Qualifier("wordListMainDbSqlSessionFactory") sqlSessionFactory: SqlSessionFactory
    ): SqlSession = SqlSessionTemplate(sqlSessionFactory)
    @Bean
    fun wordListSubDbSqlSessionFactory(
        @Qualifier("wordListSubDbDatasource") datasource: DataSource
    ): SqlSessionFactory = SqlSessionFactoryBean().also {
        it.setDataSource(datasource)
        it.setMapperLocations(
            *PathMatchingResourcePatternResolver().getResources("classpath*:**/*Repository.xml")
        )
    }.getObject()!!

    @Bean
    fun wordListSubDbSqlSession(
        @Qualifier("wordListSubDbSqlSessionFactory") sqlSessionFactory: SqlSessionFactory
    ): SqlSession = SqlSessionTemplate(sqlSessionFactory)
}
package org.write_day.config;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.LoggingType;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.write_day.components.utils.CustomLogFormatter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:data.properties"})
public class DataConfig {

    @Autowired
    private Environment env;

    private static final String DIALECT = "hibernate.dialect";
    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String POOL_SIZE = "connection.pool_size";
    private static final String USE_REFLECTION_OPTIMIZER = "hibernate.bytecode.use_reflection_optimizer";
    private static final String USE_SECOND_LEVEL_CACHE = "hibernate.cache.use_second_level_cache";
    private static final String HIBERNATE_CACHE_REGION_FACTORY_CLASS = "hibernate.cache.region.factory_class";
    private static final String EHCACHE_CONFIG = "net.sf.ehcache.configurationResourceName";
    private static final String USE_QUERY_CACHE = "hibernate.cache.use_query_cache";
    private static final String CURRENT_SESSION_CONTEXT_CLASS = "hibernate.current_session_context_class";
    private static final String BATCH_SIZE = "hibernate.jdbc.batch_size";
    private static final String DEFAULT_INDEX_BASE = "hibernate.search.default.indexBase";
    private static final String DEFAULT_DIRECTORY_PROVIDER = "hibernate.search.default.directory_provider";
    private static final String STRUCTURED_ENTRIES = "hibernate.cache.use_structured_entries";
    private static final String MINIMAL_PUTS = "hibernate.cache.use_minimal_puts";
    private static final String FETCH_DEPTH = "hibernate.max_fetch_depth";
    private static final String AUTO_RECONNECT = "hibernate.connection.autoReconnect";
    private static final String AUTO_RECONNECT_FOR_POOL = "hibernate.connection.autoReconnectForPools";
    private static final String CACHE_PROVIDER_CLASS = "hibernate.cache.provider_class";

    private static final String ENTITIES = "entities";

    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setHibernateProperties(properties());
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan(env.getProperty(ENTITIES));
        sessionFactoryBean.setDataSource(dataSource());
        return sessionFactoryBean;
    }


    @Bean(name = "dataSourceSpied")
    public DataSource dataSourceSpied() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        dataSource.setUrl(env.getRequiredProperty("sql.server.host"));
        dataSource.setUsername(env.getRequiredProperty("sql.server.username"));
        dataSource.setPassword(env.getRequiredProperty("sql.server.password"));
        return dataSource;
    }

    @Bean
    public DataSource dataSource() {
        Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(dataSourceSpied());
        dataSource.setLogFormatter(logFormatter());
        return dataSource;
    }

    @Bean
    public CustomLogFormatter logFormatter() {
        CustomLogFormatter customLogFormatter = new CustomLogFormatter();
        customLogFormatter.setLoggingType(LoggingType.SINGLE_LINE);
        customLogFormatter.setMargin(19);
        customLogFormatter.setSqlPrefix("SQL:::");
        return customLogFormatter;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put(DIALECT, env.getRequiredProperty(DIALECT));
        properties.put(SHOW_SQL, env.getRequiredProperty(SHOW_SQL));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty(HBM2DDL_AUTO));
        properties.put(POOL_SIZE, env.getRequiredProperty(POOL_SIZE));
        properties.put(USE_REFLECTION_OPTIMIZER, env.getRequiredProperty(USE_REFLECTION_OPTIMIZER));
        properties.put(HIBERNATE_CACHE_REGION_FACTORY_CLASS, env.getRequiredProperty(HIBERNATE_CACHE_REGION_FACTORY_CLASS));
        properties.put(EHCACHE_CONFIG, env.getRequiredProperty(EHCACHE_CONFIG));
        properties.put(CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty(CURRENT_SESSION_CONTEXT_CLASS));
        properties.put(DEFAULT_INDEX_BASE, env.getRequiredProperty(DEFAULT_INDEX_BASE));
        properties.put(DEFAULT_DIRECTORY_PROVIDER, env.getRequiredProperty(DEFAULT_DIRECTORY_PROVIDER));
        properties.put(BATCH_SIZE, env.getRequiredProperty(BATCH_SIZE));
        properties.put(STRUCTURED_ENTRIES, env.getRequiredProperty(STRUCTURED_ENTRIES));
        properties.put(MINIMAL_PUTS, env.getRequiredProperty(MINIMAL_PUTS));
        properties.put(FETCH_DEPTH, env.getRequiredProperty(FETCH_DEPTH));
        properties.put(AUTO_RECONNECT, env.getRequiredProperty(AUTO_RECONNECT));
        properties.put(AUTO_RECONNECT_FOR_POOL, env.getRequiredProperty(AUTO_RECONNECT_FOR_POOL));
        properties.put(CACHE_PROVIDER_CLASS, env.getRequiredProperty(CACHE_PROVIDER_CLASS));
        //properties.put(USE_QUERY_CACHE, env.getRequiredProperty(USE_QUERY_CACHE));
        //properties.put(USE_SECOND_LEVEL_CACHE, env.getRequiredProperty(USE_SECOND_LEVEL_CACHE));
        return properties;
    }
}

/**
 *
 */
package com.haozileung.infra.dao;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.haozileung.infra.dal.*;
import com.haozileung.infra.dal.handler.AnnotationNameHandler;
import com.haozileung.infra.dal.handler.NameHandler;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liang
 */
public class DaoModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(DaoModule.class);

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("app.properties");
            PoolProperties p = new PoolProperties();
            p.setUrl(config.getString("db.url"));
            p.setUsername(config.getString("db.username"));
            p.setPassword(config.getString("db.password"));
            p.setDriverClassName("com.mysql.jdbc.Driver");
            p.setJmxEnabled(false);
            p.setTestWhileIdle(true);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(false);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            DataSource ds = new DataSource();
            ds.setPoolProperties(p);
            logger.debug("Binding DataSouce to {}", ds);
            bind(DataSource.class).annotatedWith(Names.named("default")).toInstance(ds);
        } catch (ConfigurationException e) {
            logger.error("Config File Load Error: {}", e.getMessage());
        }
        bind(JdbcDao.class).to(JdbcDaoDbUtilsImpl.class);
        bind(NameHandler.class).to(AnnotationNameHandler.class);
        bind(QueryRunner.class).toInstance(new QueryRunner());
        bind(SqlFactory.class).to(SimpleSqlFactory.class);
        bind(ConnectionManager.class).toInstance(new ConnectionManager());
        bind(TxManager.class).toInstance(new TxManager());
    }
}

/**
 *
 */
package com.haozileung.web.init;

import com.google.inject.AbstractModule;
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
class DaoModule : AbstractModule() {
    private final val logger: Logger = LoggerFactory.getLogger(DaoModule::class.java);

    /*
     * (non-Javadoc)
     *
     * @see com.google.inject.AbstractModule#configure()
     */
    override fun configure() {
        try {
            val config: PropertiesConfiguration = PropertiesConfiguration("app.properties");
            val p: PoolProperties = PoolProperties();
            p.url = config.getString("db.url");
            p.username = config.getString("db.username");
            p.password = config.getString("db.password");
            p.driverClassName = "com.mysql.jdbc.Driver";
            p.isJmxEnabled = false;
            p.isTestWhileIdle = true;
            p.isTestOnBorrow = true;
            p.validationQuery = "SELECT 1";
            p.isTestOnReturn = false;
            p.validationInterval = 30000;
            p.timeBetweenEvictionRunsMillis = 30000;
            p.maxActive = 100;
            p.initialSize = 10;
            p.maxWait = 10000;
            p.removeAbandonedTimeout = 60;
            p.minEvictableIdleTimeMillis = 30000;
            p.minIdle = 10;
            p.isLogAbandoned = true;
            p.isRemoveAbandoned = false;
            p.jdbcInterceptors = "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer";
            val ds: DataSource = DataSource();
            ds.poolProperties = p;
            bind(QueryRunner::class.java
            ).toInstance(QueryRunner(ds));
        } catch (e: ConfigurationException) {
            logger.error("Config File Load Error: {}", e.message);
        }
    }
}

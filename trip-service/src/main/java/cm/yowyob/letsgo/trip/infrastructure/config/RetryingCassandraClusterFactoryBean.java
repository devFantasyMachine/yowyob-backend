/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.config;/*
package cm.yowyob.letsgo.trip.application.config;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.cassandra.config.CassandraCqlTemplateFactoryBean;

public class RetryingCassandraClusterFactoryBean extends CassandraCqlTemplateFactoryBean {

    private static final Logger LOG = LoggerFactory.getLogger(RetryingCassandraClusterFactoryBean.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }

    private void connect() throws Exception {

        try {

            super.afterPropertiesSet();

        } catch (IllegalArgumentException | NoHostAvailableException e) {

            LOG.warn(e.getMessage());
            LOG.warn("Retrying connection in 10 seconds");
            sleep();
            //connect();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
*/

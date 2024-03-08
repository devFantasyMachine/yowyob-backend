/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.config;/*



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

@EnableScheduling
@Configuration
public class SchedulerConfig {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private QuartzProperties quartzProperties;
	
			
	@Value("${spring.redis.host}")
	String host;

	@Value("${spring.redis.password}")
	String password;

	@Value("${spring.redis.port}")
	Integer port;
	
*/
/* 	@Bean
	JedisPool jedisPool(){

		return new JedisPool(host, port);
	} *//*



	@Bean
	SchedulerFactoryBean schedulerFactoryBean() {

		SchedulerJobFactory jobFactory = new SchedulerJobFactory();
		jobFactory.setApplicationContext(applicationContext);

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties()); 
		

       		properties.put("org.quartz.jobStore.class", "net.joelinn.quartz.jobstore.RedisJobStore");
       		properties.put("org.quartz.jobStore.host", host);
       		properties.put("org.quartz.jobStore.misfireThreshold", "60000");
       		//properties.put("org.quartz.jobStore.useProperties", true);

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setOverwriteExistingJobs(true);  
		factory.setQuartzProperties(properties);
		factory.setJobFactory(jobFactory);
		return factory;
	}
}


*/
/* public class SchedulingConfig {

	
    @Value("${spring.redis.host}")
    String host;

    @Value("${spring.redis.password}")
    String password;

    @Value("${spring.redis.port}")
    Integer port;
    
    @Bean
    JedisPool jedisPool(){

        return new JedisPool(host, port);
    }


 /*   @Bean
    JedisPool jedisPool(){

        return new JedisPool(host, port, (String)null, password);
    }


     @Bean
    JobRunrConfigurationResult initJobRunr(ApplicationContext applicationContext) {


        return JobRunr.configure()
                .useJobActivator(applicationContext::getBean)
                .useStorageProvider(storageProvider())
                .useBackgroundJobServer()
                .useJmxExtensions()
                .initialize();
    }
    

    @Bean
    StorageProvider storageProvider(JedisPool jedisPool){

        JedisRedisStorageProvider storageProvider = new JedisRedisStorageProvider(jedisPool);
         
        
        storageProvider.setUpStorageProvider(DatabaseOptions.CREATE);

    
    	return storageProvider; 
    
    }
  
     

}  

 */

/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@EnableCaching
@EnableDiscoveryClient
@SpringBootApplication
public class TripServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(TripServiceApplication.class, args);
	}

}

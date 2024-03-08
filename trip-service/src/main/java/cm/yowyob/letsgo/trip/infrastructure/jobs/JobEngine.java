/*
 * Copyright (c) 2023. Lorem
 * By Yowyob. @Author FantasyMachine
 */

package cm.yowyob.letsgo.trip.infrastructure.jobs;


import cm.yowyob.letsgo.trip.domain.model.schedule.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;


@Slf4j
@Transactional
@Service
public class JobEngine implements LetsgoScheduler {


	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean; 

	@Autowired
	private JobScheduleCreator scheduleCreator;

	@Autowired
	private ApplicationContext context;



	public boolean deleteJob(@NonNull ScheduledObject scheduledEntity) {

		if (scheduledEntity.getJobStatus()  == JobStatus.DELETED)
			return true;

		try {

			scheduledEntity.delete();			 

			log.info(">>>>> jobName = [" + scheduledEntity.groupJob() +  scheduledEntity.getId() +  "]" + " deleted.");

			return schedulerFactoryBean.getScheduler()
					.deleteJob(new JobKey(scheduledEntity.getId(), scheduledEntity.groupJob()));

		} catch (SchedulerException e) {

			log.error("Failed to delete job - {}", scheduledEntity.getId(),  e);

			return false;
		}
	}
	
	public void deleteJob(String identityId, String JobGroup) {

		try {

			log.info(">>>>> jobName = [" + identityId + " " + JobGroup +  "]" + " deleted.");

			schedulerFactoryBean.getScheduler()
					.deleteJob(new JobKey(identityId, JobGroup));

		} catch (SchedulerException e) {

			log.error("Failed to delete job - {}", identityId, e);

		}
	}

	public boolean pauseJob(@NonNull ScheduledObject scheduledEntity) {

		try {

			scheduledEntity.pause();
			
			schedulerFactoryBean.getScheduler()
					.pauseJob(new JobKey(scheduledEntity.getId(), scheduledEntity.groupJob()));
			log.info(">>>>> jobName = [" + scheduledEntity.groupJob() +  scheduledEntity.getId() + "]" + " paused.");
			return true;

		} catch (SchedulerException e) {
			log.error("Failed to pause job - {}", scheduledEntity.groupJob() +  scheduledEntity.getId(), e);
			return false;
		}
	}

	public boolean resumeJob(@NonNull ScheduledObject scheduledEntity) {

		try {

			scheduledEntity.resume();
			
			schedulerFactoryBean.getScheduler()
					.resumeJob(new JobKey(scheduledEntity.getId(), scheduledEntity.groupJob()));
			log.info(">>>>> jobName = [" + scheduledEntity.groupJob() + scheduledEntity.getId() + "]" + " resumed.");
			return true;

		} catch (SchedulerException e) {
			log.error("Failed to resume job - {}", scheduledEntity.groupJob() + scheduledEntity.getId(), e);
			return false;
		}
	}

	public boolean startJobNow(@NonNull ScheduledObject scheduledEntity) {

		try {

			scheduledEntity.startNow();
			schedulerFactoryBean.getScheduler()
					.triggerJob(new JobKey(scheduledEntity.getId(),scheduledEntity.groupJob()));
			log.info(">>>>> jobName = [" + scheduledEntity.getId() + "]" + " scheduled and started now.");

			return true;
		} catch (SchedulerException e) {
			log.error("Failed to start new job - {}", scheduledEntity.groupJob() + scheduledEntity.getId(), e);
			return false;
		}

	}

	public boolean scheduleNewJob(@NonNull ScheduledObject scheduledEntity, @NonNull Class<? extends Job> cls) {
		
		try {

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = JobBuilder
				.newJob(cls)
				.withIdentity(scheduledEntity.getId(), scheduledEntity.groupJob()).build();


			if (!scheduler.checkExists(jobDetail.getKey())) {

				jobDetail = scheduleCreator.createSimpleJob(true,
						context, scheduledEntity.getId(), scheduledEntity.groupJob(), cls);

				Trigger trigger;
				if (scheduledEntity.getIsCron()) {
					trigger = scheduleCreator.createCronTrigger(scheduledEntity.getId(),
							Date.from(Instant.from(scheduledEntity.getBeginAt())),
							scheduledEntity.getCron(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				} else {

					Long repeatTime = scheduledEntity.getRepeatInterval().toSeconds();

					trigger = scheduleCreator.createSimpleTrigger(scheduledEntity.getId(),
							Date.from(Instant.from(scheduledEntity.getBeginAt())),
							repeatTime, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
				}

				scheduler.scheduleJob(jobDetail, trigger);
				scheduledEntity.scheduleNew();

				log.info(">>>>> jobName = [" + scheduledEntity.groupJob() + scheduledEntity.getId() + "]" + " scheduled.");
				return true;
			} else {
				log.error("scheduleNewJobRequest.jobAlreadyExist");
				return false;
			}
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public void updateScheduleJob(@NonNull ScheduledObject scheduledEntity) {

		Trigger trigger;
		if (scheduledEntity.getIsCron()) {
			trigger = scheduleCreator.createCronTrigger(scheduledEntity.getId(),
					Date.from(Instant.from(scheduledEntity.getBeginAt())),
					scheduledEntity.getCron(), SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		} else {

			Long repeatTime = scheduledEntity.getRepeatInterval().toSeconds();

			trigger = scheduleCreator.createSimpleTrigger(scheduledEntity.getId(),
					Date.from(Instant.from(scheduledEntity.getBeginAt())),
					repeatTime, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		}

		scheduledEntity.update();

		try {
			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(scheduledEntity.getId()),
			trigger);

			log.info(">>>>> jobName = [" + scheduledEntity.groupJob() + scheduledEntity.getId() + "]" + " updated and scheduled.");
		} catch (SchedulerException e) {
			log.error(e.getMessage(), e);
		}
	}

}

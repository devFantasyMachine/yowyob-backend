package cm.yowyob.letsgo.driver.domain.events;


import cm.yowyob.letsgo.driver.domain.model.timetable.ScheduleTask;

public interface PlanningEventListener {

    void onNewPlanningEntry(ScheduleTask planningEntry);
}



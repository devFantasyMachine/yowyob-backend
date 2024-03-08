/*
package cm.yowyob.letsgo.trip.infrastructure.services;


import cm.enspy.gi.project.trip_service.models.Notification;
import cm.enspy.gi.project.trip_service.models.NotificationReason;
import cm.enspy.gi.project.trip_service.models.ScheduledTrip;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class NotificationService {

    @Autowired
    Producer<Notification> producer;


    


    public MessageId sendFailReservation(String userId, String tripId, NotificationReason reason) throws PulsarClientException {

        //TODO
        
        Notification notification = new Notification();

      

        return producer.send(notification);
        
    }

    public MessageId sendSuccessReservation(String userId, String tripId, String reservId) throws PulsarClientException  {


        //TODO

        Notification notification = new Notification();
        

        return producer.send(notification);
        
    }

    public void sendNewScheduledTrip(ScheduledTrip trip) {
    }

    public void sendVehiculeForScheduledTrip(ScheduledTrip trip) {
    }
    
}



*/

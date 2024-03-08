package cm.yowyob.letsgo.trip.domain.managers;


import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.Itinerary;
import cm.yowyob.letsgo.trip.domain.model.plan.itinerary.ItineraryProduct;
import cm.yowyob.letsgo.trip.domain.model.plan.leg.Leg;
import cm.yowyob.letsgo.trip.domain.model.plan.stops.Stop;
import cm.yowyob.letsgo.trip.domain.model.resources.ResourceStatus;
import cm.yowyob.letsgo.trip.domain.model.resources.TripResource;

import java.util.*;
import java.util.stream.IntStream;


public class ItineraryProductHelper {


    List<ItineraryProduct> generate(UUID planId, TripResource resource, Stop from, Stop to) {

        List<ItineraryProduct> products = new ArrayList<>();
        float k = resource.getTotalQuantity() / resource.getUnitQuantity();

        for (int i = 0; i < k; i++) {

            ItineraryProduct product_i = ItineraryProduct.builder()
                    .productId(UUID.randomUUID())
                    .planId(planId)
                    .resource(resource)
                    .cost(resource.getDefaultUnitCost())
                    .fromLocation(from)
                    .toLocation(to)
                    .status(ResourceStatus.FREE)
                    .quantity(resource.getUnitQuantity())
                    .build();

            products.add(product_i);
        }


        return products;
    }


    List<ItineraryProduct> generate(UUID planId, TripResource resource, TreeSet<Stop> stops) {

        List<ItineraryProduct> allForProduct = new ArrayList<>();

        for (Stop from : stops) {

            for (Stop to : stops.tailSet(from, false)) {

                allForProduct.addAll(generate(planId, resource, from, to));
            }
        }

        return allForProduct;
    }


    // FIXME: 24/08/2023
    public List<ItineraryProduct> generateWithoutComplementOfA(List<ItineraryProduct> A,
                                                    UUID planId,
                                                    TripResource resource,
                                                    TreeSet<Stop> stops) {


        List<ItineraryProduct> E = generate(planId, resource, stops);

        // remove same A product in E
        List<ItineraryProduct> theComplementOfAInE = getTheComplementOfAInE(A, E);

        E.removeAll(theComplementOfAInE);
        E.addAll(A);

        return E;
    }



    List<ItineraryProduct> getTheComplementOfAInE(List<ItineraryProduct> A,
                                                  List<ItineraryProduct> E) {

        List<ItineraryProduct> allForProduct = new ArrayList<>();

        for (ItineraryProduct itineraryProduct : E) {

            if (!A.contains(itineraryProduct)){
                allForProduct.add(itineraryProduct);
            }
        }

        return allForProduct;
    }


    @SuppressWarnings("unchecked")
    public <L extends Leg> Itinerary<L> applySigma(Itinerary<L>  I, Stop stop, Integer j) {

        SortedSet<L> legs = new TreeSet<>();

        if (j != 0) {

            IntStream.range(0, j)
                    .mapToObj(I::getLeg)
                    .forEach(legs::add);
        }

        Stop newStop = stop.withPos(j+1);

        L jLeg = I.getLeg(j);

        // if j == I.length() - 1, we extends itinerary, we create one leg, else we split itinerary
        if (j < I.length() - 1) {

            // split - we create two leg
            for (int i = j+1; i < I.length() + 1; i++) {

                legs.add((L) I.getLeg(i).withPositionShift(+1));
            }

            L newJLeg = (L) jLeg.subLeg(newStop);
            legs.add(newJLeg);
        }

        L newJ_1Leg = (L) jLeg.createNext(newStop);
        legs.add(newJ_1Leg);

        return new Itinerary<>(legs);
    }




    List<ItineraryProduct> applySigma(List<ItineraryProduct> E, Stop newStop, Integer j) {

        List<ItineraryProduct> allForProduct = new ArrayList<>();



        return allForProduct;
    }






}

package cm.yowyob.letsgo.driver.domain.handler;




public class PlanningManager {




/*


    public Vehicle addPlan(UUID vehicleId,
                           String ownerId,
                           Task task,
                           ZonedDateTime beginAt,
                           ZonedDateTime endAt) throws WritingRessourceUnauthorizedException {

        Vehicle vehicle = getVehicle(vehicleId);

        Set<Claim> claims = vehicle.getUserClaims().get(ownerId);

        Optional<Claim> writeClaim = claims.stream()
                .filter(Claim::getIsActive)
                .filter(claim -> claim.getClaimType() == ClaimType.ALL || claim.getClaimType() == ClaimType.PLANNING_READ_WRITE)
                .findFirst();

        if (writeClaim.isEmpty())
            throw new WritingRessourceUnauthorizedException();

        PlanningEntry planningEntry = new PlanningEntry(beginAt.toLocalTime(),
                endAt.toLocalTime(),
                task.getTaskType(),
                task.getTaskNote(),
                task.getReason(),
                task.getPriority(),
                task.getOwnerId(),
                task.getRefId(),
                beginAt.toLocalDate(),
                endAt.toLocalDate(),
                TaskStatus.UNCONFIRMED,
                false,
                Duration.ZERO);

        vehicle.getPlanning()
                .add(planningEntry);

        return vehicleService.save(vehicle);
    }
*/




}

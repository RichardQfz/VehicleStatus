package com.savms.repository;

import com.savms.entity.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VehicleStatusRepository
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public double getVehicleSpeedByPlate(String plate)
    {
        Query query = new Query(Criteria.where("plate").is(plate));
        VehicleStatus vehicleStatus = mongoTemplate.findOne(query, VehicleStatus.class);
        return vehicleStatus != null ? vehicleStatus.getSpeed() : -1;
    }

    public VehicleStatus getVehicleStatusByPlate(String plate)
    {
        Query query = new Query(Criteria.where("plate").is(plate));
        return mongoTemplate.findOne(query, VehicleStatus.class);
    }

    public List<VehicleStatus> getAllVehicleStatuses()
    {
        return mongoTemplate.findAll(VehicleStatus.class);
    }

    public boolean updateVehicleStatusByPlate(String plate, VehicleStatus newStatus)
    {
        Query query = new Query(Criteria.where("plate").is(plate));
        Update update = new Update()
                .set("speed", newStatus.getSpeed())
                .set("leftoverEnergy", newStatus.getLeftoverEnergy())
                .set("connectionStatus", newStatus.getConnectionStatus())
                .set("taskStatus", newStatus.getTaskStatus())
                .set("healthStatus", newStatus.getHealthStatus())
                .set("engineRPM", newStatus.getEngineRPM())
                .set("lubeOilPressure", newStatus.getLubeOilPressure())
                .set("fuelPressure", newStatus.getFuelPressure())
                .set("coolantPressure", newStatus.getCoolantPressure())
                .set("lubeOilTemp", newStatus.getLubeOilTemp())
                .set("coolantTemp", newStatus.getCoolantTemp())
                .set("engineCondition", newStatus.getEngineCondition());

        return mongoTemplate.updateFirst(query, update, VehicleStatus.class).wasAcknowledged();
    }

    public boolean deleteVehicleStatusByPlate(String plate)
    {
        Query query = new Query(Criteria.where("plate").is(plate));
        return mongoTemplate.remove(query, VehicleStatus.class).wasAcknowledged();
    }

    public boolean insertVehicleStatus(VehicleStatus status)
    {
        VehicleStatus inserted = mongoTemplate.insert(status);
        return inserted != null;
    }

    public boolean existsByPlate(String plate)
    {
        Query query = new Query(Criteria.where("plate").is(plate));
        return mongoTemplate.exists(query, VehicleStatus.class);
    }
}
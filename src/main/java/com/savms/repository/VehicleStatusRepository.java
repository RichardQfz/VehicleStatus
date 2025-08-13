package com.savms.repository;

import com.savms.entity.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository class for vehicle status.
 *
 * @author Yutong Cheng u7739713
 */
@Repository
public class VehicleStatusRepository
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public double getVehicleSpeedByPlate( String plate )
    {
        Query query = new Query( Criteria.where("licensePlate").is(plate) );
        Vehicle vehicleStatus = mongoTemplate.findOne( query, Vehicle.class );
        return vehicleStatus != null ? vehicleStatus.getSpeed() : -1;
    }

    public Vehicle getVehicleStatusByPlate(String plate )
    {
        Query query = new Query( Criteria.where("licensePlate").is(plate) );
        return mongoTemplate.findOne( query, Vehicle.class );
    }

    public List<Vehicle> getAllVehicleStatuses()
    {
        return mongoTemplate.findAll( Vehicle.class );
    }

    public boolean updateVehicleStatusByPlate( String plate, Vehicle newStatus )
    {
        Query query = new Query(Criteria.where("licensePlate").is(plate));
        Update update = new Update()
                .set( "speed", newStatus.getSpeed() )
                .set( "leftoverEnergy", newStatus.getLeftoverEnergy() )
                .set( "connectionStatus", newStatus.getConnectionStatus() )
                .set( "taskStatus", newStatus.getTaskStatus() )
                .set( "healthStatus", newStatus.getHealthStatus() )
                .set( "engineRPM", newStatus.getEngineRPM() )
                .set( "lubeOilPressure", newStatus.getLubeOilPressure() )
                .set( "fuelPressure", newStatus.getFuelPressure() )
                .set( "coolantPressure", newStatus.getCoolantPressure() )
                .set( "lubeOilTemp", newStatus.getLubeOilTemp() )
                .set( "coolantTemp", newStatus.getCoolantTemp() )
                .set( "engineCondition", newStatus.getEngineCondition() );

        return mongoTemplate.updateFirst( query, update, Vehicle.class ).wasAcknowledged();
    }

    public boolean deleteVehicleStatusByPlate( String plate )
    {
        Query query = new Query( Criteria.where("licensePlate").is(plate) );
        return mongoTemplate.remove(query, Vehicle.class).wasAcknowledged();
    }

    public boolean insertVehicleStatus( Vehicle status )
    {
        Vehicle inserted = mongoTemplate.insert(status);
        return inserted != null;
    }

    public boolean existsByPlate( String plate )
    {
        Query query = new Query(Criteria.where("licensePlate").is(plate));
        return mongoTemplate.exists(query, Vehicle.class);
    }

    public Vehicle getVehicleStatusById(ObjectId id)
    {
        return mongoTemplate.findById(id, Vehicle.class);
    }

    public boolean updateVehicleStatusById(ObjectId id, Vehicle newStatus)
    {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update()
                .set( "speed", newStatus.getSpeed() )
                .set( "leftoverEnergy", newStatus.getLeftoverEnergy() )
                .set( "connectionStatus", newStatus.getConnectionStatus() )
                .set( "taskStatus", newStatus.getTaskStatus() )
                .set( "healthStatus", newStatus.getHealthStatus() )
                .set( "engineRPM", newStatus.getEngineRPM() )
                .set( "lubeOilPressure", newStatus.getLubeOilPressure() )
                .set( "fuelPressure", newStatus.getFuelPressure() )
                .set( "coolantPressure", newStatus.getCoolantPressure() )
                .set( "lubeOilTemp", newStatus.getLubeOilTemp() )
                .set( "coolantTemp", newStatus.getCoolantTemp() )
                .set( "engineCondition", newStatus.getEngineCondition() );

        return mongoTemplate.updateFirst( query, update, Vehicle.class ).wasAcknowledged();
    }

    public boolean deleteVehicleStatusById(ObjectId id)
    {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.remove(query, Vehicle.class).wasAcknowledged();
    }

    public boolean existsById(ObjectId id)
    {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.exists(query, Vehicle.class);
    }
}
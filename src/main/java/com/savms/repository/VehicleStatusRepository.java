package com.savms.repository;

import com.savms.entity.VehicleStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleStatusRepository
{
    @Autowired
    private MongoTemplate mongoTemplate;

    public double getVehicleSpeedByPlate( String plate )
    {
        Query query = new Query( Criteria.where( "plate" ).is( plate ) );
        VehicleStatus vehicleStatus = mongoTemplate.findOne( query, VehicleStatus.class );
        return vehicleStatus != null ? vehicleStatus.getSpeed() : -1;
    }
}
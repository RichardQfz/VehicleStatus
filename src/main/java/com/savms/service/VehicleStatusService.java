package com.savms.service;

import com.savms.repository.VehicleStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleStatusService
{
    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;

    public double getSpeedByPlate( String plate )
    {
        return vehicleStatusRepository.getVehicleSpeedByPlate( plate );
    }
}
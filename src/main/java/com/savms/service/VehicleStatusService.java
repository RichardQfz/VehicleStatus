package com.savms.service;

import com.savms.entity.Vehicle;
import com.savms.repository.VehicleStatusRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for vehicle status.
 *
 * @author Yutong Cheng u7739713
 */
@Service
public class VehicleStatusService
{
    @Autowired
    private VehicleStatusRepository vehicleStatusRepository;

    public double getSpeedByPlate(String plate)
    {
        return vehicleStatusRepository.getVehicleSpeedByPlate(plate);
    }

    public Vehicle getStatusByPlate(String plate)
    {
        return vehicleStatusRepository.getVehicleStatusByPlate(plate);
    }

    public List<Vehicle> getAllStatuses()
    {
        return vehicleStatusRepository.getAllVehicleStatuses();
    }

    public boolean updateStatusByPlate(String plate, Vehicle newStatus)
    {
        return vehicleStatusRepository.updateVehicleStatusByPlate(plate, newStatus);
    }

    public boolean deleteStatusByPlate(String plate)
    {
        return vehicleStatusRepository.deleteVehicleStatusByPlate(plate);
    }

    public boolean insertStatus(Vehicle status)
    {
        return vehicleStatusRepository.insertVehicleStatus(status);
    }

    public boolean existsByPlate(String plate)
    {
        return vehicleStatusRepository.existsByPlate(plate);
    }

    public Vehicle getStatusById(ObjectId id)
    {
        return vehicleStatusRepository.getVehicleStatusById(id);
    }

    public boolean updateStatusById(ObjectId id, Vehicle newStatus)
    {
        return vehicleStatusRepository.updateVehicleStatusById(id, newStatus);
    }

    public boolean deleteStatusById(ObjectId id)
    {
        return vehicleStatusRepository.deleteVehicleStatusById(id);
    }

    public boolean existsById(ObjectId id)
    {
        return vehicleStatusRepository.existsById(id);
    }
}
package com.savms.service;

import com.savms.entity.VehicleStatus;
import com.savms.repository.VehicleStatusRepository;
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

    public VehicleStatus getStatusByPlate(String plate)
    {
        return vehicleStatusRepository.getVehicleStatusByPlate(plate);
    }

    public List<VehicleStatus> getAllStatuses()
    {
        return vehicleStatusRepository.getAllVehicleStatuses();
    }

    public boolean updateStatusByPlate(String plate, VehicleStatus newStatus)
    {
        return vehicleStatusRepository.updateVehicleStatusByPlate(plate, newStatus);
    }

    public boolean deleteStatusByPlate(String plate)
    {
        return vehicleStatusRepository.deleteVehicleStatusByPlate(plate);
    }

    public boolean insertStatus(VehicleStatus status)
    {
        return vehicleStatusRepository.insertVehicleStatus(status);
    }

    public boolean existsByPlate(String plate)
    {
        return vehicleStatusRepository.existsByPlate(plate);
    }
}
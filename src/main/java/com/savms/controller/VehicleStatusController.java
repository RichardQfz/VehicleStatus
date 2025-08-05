package com.savms.controller;

import com.savms.entity.Vehicle;
import com.savms.service.VehicleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for vehicle status.
 *
 * @author Yutong Cheng u7739713
 */
@RestController
@RequestMapping("/api/vehicle-status")
public class VehicleStatusController
{
    @Autowired
    private VehicleStatusService vehicleStatusService;

    @GetMapping("/speed/{plate}")
    public double getSpeedByPlate(@PathVariable String plate)
    {
        return vehicleStatusService.getSpeedByPlate(plate);
    }

    @GetMapping("/{plate}")
    public Vehicle getStatusByPlate(@PathVariable String plate)
    {
        return vehicleStatusService.getStatusByPlate(plate);
    }

    @GetMapping("/all")
    public List<Vehicle> getAllStatuses()
    {
        return vehicleStatusService.getAllStatuses();
    }

    @PostMapping("/")
    public boolean insertStatus(@RequestBody Vehicle status)
    {
        return vehicleStatusService.insertStatus(status);
    }

    @PutMapping("/{plate}")
    public boolean updateStatus(@PathVariable String plate, @RequestBody Vehicle status)
    {
        return vehicleStatusService.updateStatusByPlate(plate, status);
    }

    @DeleteMapping("/{plate}")
    public boolean deleteStatus(@PathVariable String plate)
    {
        return vehicleStatusService.deleteStatusByPlate(plate);
    }

    @GetMapping("/exists/{plate}")
    public boolean existsByPlate(@PathVariable String plate)
    {
        return vehicleStatusService.existsByPlate(plate);
    }
}
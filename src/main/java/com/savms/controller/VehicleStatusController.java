package com.savms.controller;

import com.savms.entity.VehicleStatus;
import com.savms.service.VehicleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public VehicleStatus getStatusByPlate(@PathVariable String plate)
    {
        return vehicleStatusService.getStatusByPlate(plate);
    }

    @GetMapping("/all")
    public List<VehicleStatus> getAllStatuses()
    {
        return vehicleStatusService.getAllStatuses();
    }

    @PostMapping("/")
    public boolean insertStatus(@RequestBody VehicleStatus status)
    {
        return vehicleStatusService.insertStatus(status);
    }

    @PutMapping("/{plate}")
    public boolean updateStatus(@PathVariable String plate, @RequestBody VehicleStatus status)
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
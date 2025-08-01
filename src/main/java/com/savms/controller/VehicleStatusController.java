package com.savms.controller;

import com.savms.service.VehicleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vehicle-status")
public class VehicleStatusController
{
    @Autowired
    private VehicleStatusService vehicleStatusService;

    @GetMapping("/speed/{plate}")
    public double getSpeedByPlate( @PathVariable String plate )
    {
        return vehicleStatusService.getSpeedByPlate( plate );
    }
}
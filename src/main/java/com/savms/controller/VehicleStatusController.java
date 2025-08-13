package com.savms.controller;

import com.savms.entity.Vehicle;
import com.savms.service.VehicleStatusService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/id/{id}")
    public ResponseEntity<Vehicle> getStatusById(@PathVariable String id)
    {
        try {
            ObjectId objectId = new ObjectId(id);
            Vehicle vehicle = vehicleStatusService.getStatusById(objectId);
            if (vehicle != null) {
                return ResponseEntity.ok(vehicle);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<String> updateStatusById(@PathVariable String id, @RequestBody Vehicle status)
    {
        try {
            ObjectId objectId = new ObjectId(id);
            boolean updated = vehicleStatusService.updateStatusById(objectId, status);
            if (updated) {
                return ResponseEntity.ok("Vehicle updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid vehicle ID format");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteStatusById(@PathVariable String id)
    {
        try {
            ObjectId objectId = new ObjectId(id);
            boolean deleted = vehicleStatusService.deleteStatusById(objectId);
            if (deleted) {
                return ResponseEntity.ok("Vehicle deleted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid vehicle ID format");
        }
    }

    @GetMapping("/exists/id/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable String id)
    {
        try {
            ObjectId objectId = new ObjectId(id);
            boolean exists = vehicleStatusService.existsById(objectId);
            return ResponseEntity.ok(exists);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
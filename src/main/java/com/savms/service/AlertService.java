package com.savms.service;

import com.savms.entity.Alert;
import com.savms.entity.Vehicle;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for alert operations.
 */
@Service
public class AlertService
{
    @Autowired
    private VehicleStatusService vehicleStatusService;

    @Autowired
    private LogService logService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * Get all alerts for a vehicle by license plate
     */
    public List<Alert> getAlertsByLicensePlate(String licensePlate) {
        Query query = new Query(Criteria.where("licensePlate").is(licensePlate));
        return mongoTemplate.find(query, Alert.class);
    }

    /**
     * Get all active alerts for a vehicle by license plate
     */
    public List<Alert> getActiveAlertsByLicensePlate(String licensePlate) {
        Query query = new Query(
            Criteria.where("licensePlate").is(licensePlate)
                .and("status").is("ACTIVE")
        );
        return mongoTemplate.find(query, Alert.class);
    }

    /**
     * Check and generate new alerts based on vehicle status
     */
    public List<Alert> checkAndGenerateAlerts(String licensePlate) {
        Vehicle vehicleStatus = vehicleStatusService.getStatusByPlate(licensePlate);
        if (vehicleStatus == null) {
            return new ArrayList<>();
        }
        
        List<Alert> newAlerts = new ArrayList<>();
        ObjectId vehicleId = vehicleStatus.getId();

        // Check for excessive speed
        if (vehicleStatus.getSpeed() > 120) {
            Alert speedAlert = new Alert(
                vehicleId,
                licensePlate,
                "SPEED_OVER_LIMIT",
                "Vehicle speed too high: " + vehicleStatus.getSpeed() + " km/h",
                vehicleStatus.getSpeed() > 150 ? "CRITICAL" : "HIGH"
            );
            if (shouldCreateAlert(speedAlert)) {
                mongoTemplate.save(speedAlert);
                newAlerts.add(speedAlert);
                logService.logAlert(speedAlert);
            }
        }

        // Check for low energy
        if (vehicleStatus.getLeftoverEnergy() < 20) {
            Alert energyAlert = new Alert(
                vehicleId,
                licensePlate,
                "LOW_ENERGY",
                "Low energy level: " + vehicleStatus.getLeftoverEnergy() + "%",
                vehicleStatus.getLeftoverEnergy() < 10 ? "CRITICAL" : "MEDIUM"
            );
            if (shouldCreateAlert(energyAlert)) {
                mongoTemplate.save(energyAlert);
                newAlerts.add(energyAlert);
                logService.logAlert(energyAlert);
            }
        }

        // Check connection status
        if (vehicleStatus.getConnectionStatus() == 0) {
            Alert connectionAlert = new Alert(
                vehicleId,
                licensePlate,
                "CONNECTION_LOST",
                "Vehicle connection lost",
                "HIGH"
            );
            if (shouldCreateAlert(connectionAlert)) {
                mongoTemplate.save(connectionAlert);
                newAlerts.add(connectionAlert);
                logService.logAlert(connectionAlert);
            }
        }

        // Check engine status
        if (vehicleStatus.getEngineCondition() == 0) {
            Alert engineAlert = new Alert(
                vehicleId,
                licensePlate,
                "ENGINE_ISSUE",
                "Engine condition abnormal",
                "CRITICAL"
            );
            if (shouldCreateAlert(engineAlert)) {
                mongoTemplate.save(engineAlert);
                newAlerts.add(engineAlert);
                logService.logAlert(engineAlert);
            }
        }

        // Check oil temperature
        if (vehicleStatus.getLubeOilTemp() > 120) {
            Alert oilTempAlert = new Alert(
                vehicleId,
                licensePlate,
                "OIL_TEMP_HIGH",
                "Oil temperature too high: " + vehicleStatus.getLubeOilTemp() + "°C",
                "HIGH"
            );
            if (shouldCreateAlert(oilTempAlert)) {
                mongoTemplate.save(oilTempAlert);
                newAlerts.add(oilTempAlert);
                logService.logAlert(oilTempAlert);
            }
        }

        // Check coolant temperature
        if (vehicleStatus.getCoolantTemp() > 100) {
            Alert coolantTempAlert = new Alert(
                vehicleId,
                licensePlate,
                "COOLANT_TEMP_HIGH",
                "Coolant temperature too high: " + vehicleStatus.getCoolantTemp() + "°C",
                "HIGH"
            );
            if (shouldCreateAlert(coolantTempAlert)) {
                mongoTemplate.save(coolantTempAlert);
                newAlerts.add(coolantTempAlert);
                logService.logAlert(coolantTempAlert);
            }
        }

        // Check oil pressure
        if (vehicleStatus.getLubeOilPressure() < 1.0) {
            Alert oilPressureAlert = new Alert(
                vehicleId,
                licensePlate,
                "OIL_PRESSURE_LOW",
                "Oil pressure too low: " + vehicleStatus.getLubeOilPressure() + " bar",
                "CRITICAL"
            );
            if (shouldCreateAlert(oilPressureAlert)) {
                mongoTemplate.save(oilPressureAlert);
                newAlerts.add(oilPressureAlert);
                logService.logAlert(oilPressureAlert);
            }
        }

        // Check fuel pressure
        if (vehicleStatus.getFuelPressure() < 2.0) {
            Alert fuelPressureAlert = new Alert(
                vehicleId,
                licensePlate,
                "FUEL_PRESSURE_LOW",
                "Fuel pressure too low: " + vehicleStatus.getFuelPressure() + " bar",
                "HIGH"
            );
            if (shouldCreateAlert(fuelPressureAlert)) {
                mongoTemplate.save(fuelPressureAlert);
                newAlerts.add(fuelPressureAlert);
                logService.logAlert(fuelPressureAlert);
            }
        }

        return newAlerts;
    }

    /**
     * Resolve an alert by setting its status to RESOLVED
     */
    public boolean resolveAlert(ObjectId alertId) {
        Query query = new Query(Criteria.where("id").is(alertId));
        Alert alert = mongoTemplate.findOne(query, Alert.class);
        if (alert != null && "ACTIVE".equals(alert.getStatus())) {
            alert.setStatus("RESOLVED");
            alert.setResolvedAt(LocalDateTime.now());
            mongoTemplate.save(alert);
            return true;
        }
        return false;
    }

    /**
     * Dismiss an alert by setting its status to DISMISSED
     */
    public boolean dismissAlert(ObjectId alertId) {
        Query query = new Query(Criteria.where("id").is(alertId));
        Alert alert = mongoTemplate.findOne(query, Alert.class);
        if (alert != null && "ACTIVE".equals(alert.getStatus())) {
            alert.setStatus("DISMISSED");
            alert.setResolvedAt(LocalDateTime.now());
            mongoTemplate.save(alert);
            return true;
        }
        return false;
    }

    /**
     * Check if a similar alert already exists and is still active
     */
    private boolean shouldCreateAlert(Alert newAlert) {
        Query query = new Query(
            Criteria.where("vehicleId").is(newAlert.getVehicleId())
                .and("alertType").is(newAlert.getAlertType())
                .and("status").is("ACTIVE")
        );
        return !mongoTemplate.exists(query, Alert.class);
    }
} 
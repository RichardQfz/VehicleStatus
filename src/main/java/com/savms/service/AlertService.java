package com.savms.service;

import com.savms.entity.Alert;
import com.savms.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * Check alert base on vehicle license plate
     */
    public List<Alert> getAlertsByLicensePlate(String licensePlate) {
        Vehicle vehicleStatus = vehicleStatusService.getStatusByPlate(licensePlate);
        if (vehicleStatus == null) {
            return new ArrayList<>();
        }
        System.out.println(vehicleStatus);
        List<Alert> alerts = new ArrayList<>();

        // Check for excessive speed
        if (vehicleStatus.getSpeed() > 120) {
            Alert speedAlert = new Alert(
                licensePlate,
                "SPEED_OVER_LIMIT",
                "Vehicle speed too high: " + vehicleStatus.getSpeed() + " km/h",
                vehicleStatus.getSpeed() > 150 ? "CRITICAL" : "HIGH"
            );
            alerts.add(speedAlert);
        }

        // Check for low energy
        if (vehicleStatus.getLeftoverEnergy() < 20) {
            Alert energyAlert = new Alert(
                licensePlate,
                "LOW_ENERGY",
                "Low energy level: " + vehicleStatus.getLeftoverEnergy() + "%",
                vehicleStatus.getLeftoverEnergy() < 10 ? "CRITICAL" : "MEDIUM"
            );
            alerts.add(energyAlert);
        }

        // Check connection status
        if (vehicleStatus.getConnectionStatus() == 0) {
            Alert connectionAlert = new Alert(
                licensePlate,
                "CONNECTION_LOST",
                "Vehicle connection lost",
                "HIGH"
            );
            alerts.add(connectionAlert);
        }

        // Check engine status
        if (vehicleStatus.getEngineCondition() == 0) {
            Alert engineAlert = new Alert(
                licensePlate,
                "ENGINE_ISSUE",
                "Engine condition abnormal",
                "CRITICAL"
            );
            alerts.add(engineAlert);
        }

        // Check oil temperature
        if (vehicleStatus.getLubeOilTemp() > 120) {
            Alert oilTempAlert = new Alert(
                licensePlate,
                "OIL_TEMP_HIGH",
                "Oil temperature too high: " + vehicleStatus.getLubeOilTemp() + "°C",
                "HIGH"
            );
            alerts.add(oilTempAlert);
        }

        // Check coolant temperature
        if (vehicleStatus.getCoolantTemp() > 100) {
            Alert coolantTempAlert = new Alert(
                licensePlate,
                "COOLANT_TEMP_HIGH",
                "Coolant temperature too high: " + vehicleStatus.getCoolantTemp() + "°C",
                "HIGH"
            );
            alerts.add(coolantTempAlert);
        }

        // Check oil pressure
        if (vehicleStatus.getLubeOilPressure() < 1.0) {
            Alert oilPressureAlert = new Alert(
                licensePlate,
                "OIL_PRESSURE_LOW",
                "Oil pressure too low: " + vehicleStatus.getLubeOilPressure() + " bar",
                "CRITICAL"
            );
            alerts.add(oilPressureAlert);
        }

        // Check fuel pressure
        if (vehicleStatus.getFuelPressure() < 2.0) {
            Alert fuelPressureAlert = new Alert(
                licensePlate,
                "FUEL_PRESSURE_LOW",
                "Fuel pressure too low: " + vehicleStatus.getFuelPressure() + " bar",
                "HIGH"
            );
            alerts.add(fuelPressureAlert);
        }

        return alerts;
    }
} 
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
     * 根据车牌号检查车辆状态并生成相应的alert
     */
    public List<Alert> getAlertsByLicensePlate(String licensePlate) {
        Vehicle vehicleStatus = vehicleStatusService.getStatusByPlate(licensePlate);
        if (vehicleStatus == null) {
            return new ArrayList<>();
        }
        System.out.println(vehicleStatus);
        List<Alert> alerts = new ArrayList<>();

        // 检查速度过快
        if (vehicleStatus.getSpeed() > -2) {
            Alert speedAlert = new Alert(
                licensePlate,
                "SPEED_OVER_LIMIT",
                "车辆速度过快: " + vehicleStatus.getSpeed() + " km/h",
                vehicleStatus.getSpeed() > 150 ? "CRITICAL" : "HIGH"
            );
            alerts.add(speedAlert);
        }

        // 检查电量不足
        if (vehicleStatus.getLeftoverEnergy() < 20) {
            Alert energyAlert = new Alert(
                licensePlate,
                "LOW_ENERGY",
                "电量不足: " + vehicleStatus.getLeftoverEnergy() + "%",
                vehicleStatus.getLeftoverEnergy() < 10 ? "CRITICAL" : "MEDIUM"
            );
            alerts.add(energyAlert);
        }

        // 检查连接状态
        if (vehicleStatus.getConnectionStatus() == 0) {
            Alert connectionAlert = new Alert(
                licensePlate,
                "CONNECTION_LOST",
                "车辆连接丢失",
                "HIGH"
            );
            alerts.add(connectionAlert);
        }

        // 检查发动机状态
        if (vehicleStatus.getEngineCondition() == 0) {
            Alert engineAlert = new Alert(
                licensePlate,
                "ENGINE_ISSUE",
                "发动机状态异常",
                "CRITICAL"
            );
            alerts.add(engineAlert);
        }

        // 检查机油温度
        if (vehicleStatus.getLubeOilTemp() > 120) {
            Alert oilTempAlert = new Alert(
                licensePlate,
                "OIL_TEMP_HIGH",
                "机油温度过高: " + vehicleStatus.getLubeOilTemp() + "°C",
                "HIGH"
            );
            alerts.add(oilTempAlert);
        }

        // 检查冷却液温度
        if (vehicleStatus.getCoolantTemp() > 100) {
            Alert coolantTempAlert = new Alert(
                licensePlate,
                "COOLANT_TEMP_HIGH",
                "冷却液温度过高: " + vehicleStatus.getCoolantTemp() + "°C",
                "HIGH"
            );
            alerts.add(coolantTempAlert);
        }

        // 检查机油压力
        if (vehicleStatus.getLubeOilPressure() < 1.0) {
            Alert oilPressureAlert = new Alert(
                licensePlate,
                "OIL_PRESSURE_LOW",
                "机油压力过低: " + vehicleStatus.getLubeOilPressure() + " bar",
                "CRITICAL"
            );
            alerts.add(oilPressureAlert);
        }

        // 检查燃油压力
        if (vehicleStatus.getFuelPressure() < 2.0) {
            Alert fuelPressureAlert = new Alert(
                licensePlate,
                "FUEL_PRESSURE_LOW",
                "燃油压力过低: " + vehicleStatus.getFuelPressure() + " bar",
                "HIGH"
            );
            alerts.add(fuelPressureAlert);
        }

        return alerts;
    }
} 
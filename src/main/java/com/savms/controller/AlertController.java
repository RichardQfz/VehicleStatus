package com.savms.controller;

import com.savms.entity.Alert;
import com.savms.service.AlertService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for alert operations.
 *
 */
@RestController
@RequestMapping("/api/alert")
public class AlertController
{
    @Autowired
    private AlertService alertService;

    /**
     * Get all alerts for specified license plate
     * GET /api/alert/{licensePlate}
     */
    @GetMapping("/{licensePlate}")
    public List<Alert> getAlertsByLicensePlate(@PathVariable String licensePlate) {
        return alertService.getAlertsByLicensePlate(licensePlate);
    }

    /**
     * Get only active alerts for specified license plate
     * GET /api/alert/{licensePlate}/active
     */
    @GetMapping("/{licensePlate}/active")
    public List<Alert> getActiveAlertsByLicensePlate(@PathVariable String licensePlate) {
        return alertService.getActiveAlertsByLicensePlate(licensePlate);
    }

    /**
     * Check and generate new alerts for specified license plate
     * POST /api/alert/{licensePlate}/check
     */
    @PostMapping("/{licensePlate}/check")
    public List<Alert> checkAndGenerateAlerts(@PathVariable String licensePlate) {
        return alertService.checkAndGenerateAlerts(licensePlate);
    }

    /**
     * Resolve an alert by ID
     * PUT /api/alert/{alertId}/resolve
     */
    @PutMapping("/{alertId}/resolve")
    public ResponseEntity<String> resolveAlert(@PathVariable String alertId) {
        try {
            ObjectId objectId = new ObjectId(alertId);
            boolean resolved = alertService.resolveAlert(objectId);
            if (resolved) {
                return ResponseEntity.ok("Alert resolved successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid alert ID format");
        }
    }

    /**
     * Dismiss an alert by ID
     * PUT /api/alert/{alertId}/dismiss
     */
    @PutMapping("/{alertId}/dismiss")
    public ResponseEntity<String> dismissAlert(@PathVariable String alertId) {
        try {
            ObjectId objectId = new ObjectId(alertId);
            boolean dismissed = alertService.dismissAlert(objectId);
            if (dismissed) {
                return ResponseEntity.ok("Alert dismissed successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid alert ID format");
        }
    }
} 
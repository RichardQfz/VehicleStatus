package com.savms.entity;

import java.time.LocalDateTime;

/**
 * Alert entity for display only - not stored in database.
 *
 * @author Yutong Cheng u7739713
 */
public class Alert
{
    private String licensePlate;
    private String alertType;
    private String description;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private LocalDateTime timestamp;

    public Alert() {}

    public Alert(String licensePlate, String alertType, String description, String severity) {
        this.licensePlate = licensePlate;
        this.alertType = alertType;
        this.description = description;
        this.severity = severity;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
} 
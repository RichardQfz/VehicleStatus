package com.savms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;

/**
 * Alert entity for vehicle status alerts stored in MongoDB.
 * Links to Vehicle entity via vehicleId (MongoDB ObjectId).
 *
 */
@Document(collection = "alerts")
public class Alert
{
    @Id
    private ObjectId id;
    
    @Field("vehicleId")
    private ObjectId vehicleId;
    
    @Field("licensePlate")
    private String licensePlate;
    
    @Field("alertType")
    private String alertType;
    
    @Field("description")
    private String description;
    
    @Field("severity")
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    
    @Field("status")
    private String status; // ACTIVE, RESOLVED, DISMISSED
    
    @Field("timestamp")
    private LocalDateTime timestamp;
    
    @Field("resolvedAt")
    private LocalDateTime resolvedAt;

    public Alert() {}

    public Alert(ObjectId vehicleId, String licensePlate, String alertType, String description, String severity) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.alertType = alertType;
        this.description = description;
        this.severity = severity;
        this.status = "ACTIVE";
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(ObjectId vehicleId) {
        this.vehicleId = vehicleId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
} 
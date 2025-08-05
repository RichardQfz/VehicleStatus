package com.savms.controller;

import com.savms.entity.Alert;
import com.savms.service.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for alert operations.
 *
 * @author Yutong Cheng u7739713
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
} 
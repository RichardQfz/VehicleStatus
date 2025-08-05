package com.savms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for logging system.
 *
 * @author Yutong Cheng u7739713
 */
@Component
@ConfigurationProperties(prefix = "app.logging")
public class LogConfig
{
    private String alertLogPath = "./logs/alerts.log";
    private boolean enabled = true;
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public LogConfig() {}

    public String getAlertLogPath()
    {
        return alertLogPath;
    }

    public void setAlertLogPath( String alertLogPath )
    {
        this.alertLogPath = alertLogPath;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    public String getDateFormat()
    {
        return dateFormat;
    }

    public void setDateFormat( String dateFormat )
    {
        this.dateFormat = dateFormat;
    }
}
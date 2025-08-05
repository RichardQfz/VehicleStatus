package com.savms.service;

import com.savms.config.LogConfig;
import com.savms.entity.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service class for logging operations.
 *
 * @author Yutong Cheng u7739713
 */
@Service
public class LogService
{
    @Autowired
    private LogConfig logConfig;

    public void logAlert( Alert alert )
    {
        if( !logConfig.isEnabled() )
        {
            return;
        }

        try
        {
            ensureLogDirectoryExists();
            writeAlertToLog( alert );
        }
        catch( IOException e )
        {
            System.err.println( "Failed to write alert log: " + e.getMessage() );
        }
    }

    private void ensureLogDirectoryExists() throws IOException
    {
        File logFile = new File( logConfig.getAlertLogPath() );
        File logDir = logFile.getParentFile();
        
        if( logDir != null && !logDir.exists() )
        {
            if( !logDir.mkdirs() )
            {
                throw new IOException("Failed to create log directory: " + logDir.getAbsolutePath());
            }
        }
    }

    private void writeAlertToLog( Alert alert ) throws IOException
    {
        System.out.println("Print");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( logConfig.getDateFormat() );
        String timestamp = LocalDateTime.now().format( formatter );
        
        String logEntry = String.format
        (
            "[%s] ALERT - License: %s, Type: %s, Severity: %s, Description: %s%n",
            timestamp,
            alert.getLicensePlate(),
            alert.getAlertType(),
            alert.getSeverity(),
            alert.getDescription()
        );

        try( BufferedWriter writer = new BufferedWriter( new FileWriter( logConfig.getAlertLogPath(), true ) ) )
        {
            writer.write( logEntry );
        }
    }

    public void updateLogPath( String newPath )
    {
        logConfig.setAlertLogPath( newPath );
    }

    public String getCurrentLogPath()
    {
        return logConfig.getAlertLogPath();
    }
}
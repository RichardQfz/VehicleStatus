package entity;

/**
 * Entity class for vehicle status.
 *
 * @author Yutong Cheng u7739713
 */
public class VehicleStatus
{
    private double speed;
    private double leftoverEnergy;

    private int connectionStatus;
    private int taskStatus;
    private int healthStatus;

    private int engineRPM;
    private double lubeOilPressure;
    private double fuelPressure;
    private double coolantPressure;

    private double lubeOilTemp;
    private double coolantTemp;

    private int engineCondition;

    public VehicleStatus() {}

    // Getters and Setters
    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed( double speed )
    {
        this.speed = speed;
    }

    public double getLeftoverEnergy()
    {
        return leftoverEnergy;
    }

    public void setLeftoverEnergy( double leftoverEnergy )
    {
        this.leftoverEnergy = leftoverEnergy;
    }

    public int getConnectionStatus()
    {
        return connectionStatus;
    }

    public void setConnectionStatus( int connectionStatus )
    {
        this.connectionStatus = connectionStatus;
    }

    public int getTaskStatus()
    {
        return taskStatus;
    }

    public void setTaskStatus( int taskStatus )
    {
        this.taskStatus = taskStatus;
    }

    public int getHealthStatus()
    {
        return healthStatus;
    }

    public void setHealthStatus( int healthStatus )
    {
        this.healthStatus = healthStatus;
    }

    public int getEngineRPM()
    {
        return engineRPM;
    }

    public void setEngineRPM( int engineRPM )
    {
        this.engineRPM = engineRPM;
    }

    public double getLubeOilPressure()
    {
        return lubeOilPressure;
    }

    public void setLubeOilPressure(double lubeOilPressure)
    {
        this.lubeOilPressure = lubeOilPressure;
    }

    public double getFuelPressure()
    {
        return fuelPressure;
    }

    public void setFuelPressure( double fuelPressure )
    {
        this.fuelPressure = fuelPressure;
    }

    public double getCoolantPressure()
    {
        return coolantPressure;
    }

    public void setCoolantPressure( double coolantPressure )
    {
        this.coolantPressure = coolantPressure;
    }

    public double getLubeOilTemp()
    {
        return lubeOilTemp;
    }

    public void setLubeOilTemp( double lubeOilTemp )
    {
        this.lubeOilTemp = lubeOilTemp;
    }

    public double getCoolantTemp()
    {
        return coolantTemp;
    }

    public void setCoolantTemp( double coolantTemp )
    {
        this.coolantTemp = coolantTemp;
    }

    public int getEngineCondition()
    {
        return engineCondition;
    }

    public void setEngineCondition( int engineCondition )
    {
        this.engineCondition = engineCondition;
    }
}

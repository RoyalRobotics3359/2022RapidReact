package org.royalrobotics.commands;

import java.util.Timer;
import java.util.TimerTask;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.DriveSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedDriveForward extends CommandBase {
  
    private DriveSubsystem drive;
    Timer timer;
    long seconds;
    double percent;

  public TimedDriveForward(DriveSubsystem driveSubsystem, long seconds, double percentPower) {
    timer = new Timer();
    drive = driveSubsystem;
    percent = percentPower;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.schedule(new HelperTask(this), seconds * 1000);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      // Drive forward at 50% power
      drive.tankDriveVolts(percent * Constants.MAX_VOLTAGE, percent * Constants.MAX_VOLTAGE);

      
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer = null;
    drive.tankDriveVolts(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer == null) {
        return true;
    }
    return false;
  }
  
  public class HelperTask extends TimerTask {

    private TimedDriveForward command;

    public HelperTask(TimedDriveForward command) {
        this.command = command;
    }

    @Override
    public void run() {
        command.timer = null;
    }
      
  }
}

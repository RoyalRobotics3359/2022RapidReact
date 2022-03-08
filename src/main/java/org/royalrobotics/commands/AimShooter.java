// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.DriveSubsystem;
import org.royalrobotics.subsystems.Turret;
import org.royalrobotics.Constants;
//import org.royalrobotics.OperatorConsole;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AimShooter extends CommandBase {

  private Turret turret;
  private DriveSubsystem drive;
  //private OperatorConsole console;
  //private double moveTurret;
  
  /** Creates a new AimShooter. */
  public AimShooter(Turret turretSubsystem, DriveSubsystem driveSubsystem) {
    turret = turretSubsystem;
    drive = driveSubsystem;
    addRequirements(turret);
    if (!Constants.TURRET_EXISTS)
    {
      addRequirements(drive);
    }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (Constants.TURRET_EXISTS) {
      turret.aim();
    } else {
      // TODO:  Turn the robot to face the target
      // Compute angle from Limelight to %power for drive
      // Set left & right power
      //drive.turnToAngle(angle);

      //drive.aim(turret);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.setTurretStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

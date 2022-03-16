// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class BringShooterUpToSpeed extends CommandBase {
  
  private Shooter shooter;

  private boolean running;
  private boolean finished;

  /** Creates a new Shoot. */
  public BringShooterUpToSpeed(Shooter shooterSubsystem) {
    shooter = shooterSubsystem;
    running = false;
    finished = false;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //System.out.println("Shoot::initialize()");
    //running = false;
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println("BringShooterUpToSpeed.execute()");
    running = true;
    shooter.turnOnPichingMachine();
    SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // System.out.println("BringShooterUpToSpeed.end(" + interrupted + ")");
    running = false;
  }

  public boolean isReadyToShoot() {
    if (shooter.getRPM() >= Constants.SHOOTER_RPM_MINIMUM) {
      finished = true;
    }
    return finished;
  }

  public boolean isRunning(){
    return running;
  }

  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isReadyToShoot();
  }
}

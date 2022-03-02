// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import com.revrobotics.CANSparkMax;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Shooter;

// This command turns on the shooter's pitching machine and waits until
// the motor's RPM reaches the setpoint.  It does not turn off the shooter.
public class BringShooterUpToSpeed extends CommandBase {

  private Shooter shooter;

  /** Creates a new Shoot. */
  public BringShooterUpToSpeed(Shooter shooterSubsystem) {
    shooter = shooterSubsystem;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //System.out.println("Shoot::initialize()");
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //System.out.println("Shoot::execute()");
    shooter.turnOnPichingMachine();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //System.out.println("Shoot::end(" + interrupted + ")");
  }

  public boolean isReadyToShoot() {
    boolean ready = false;
    if (shooter.getRPM() >= Constants.SHOOTER_RPM_MINIMUM) {
      ready = true;
    }
    return ready;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isReadyToShoot();
  }
}

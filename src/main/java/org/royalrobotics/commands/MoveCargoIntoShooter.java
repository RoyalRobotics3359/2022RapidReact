// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

//import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Shooter;

public class MoveCargoIntoShooter extends CommandBase {

  private Shooter shooter;
  private Hopper hopper;

  /** Creates a new MoveCargoIntoShooter. */
  public MoveCargoIntoShooter(Shooter shooterSubsystem, Hopper hopperSubsytem) {
    shooter = shooterSubsystem;
    hopper = hopperSubsytem;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hopper.hopperStopperRetract();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    hopper.hopperStop();
    shooter.turnOffPitchingMacine();
  }

  private boolean isTimeExpired() {
    // FIXME!!!  Replace with actual timer class or sensor
    return false;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return isTimeExpired();
  }
}

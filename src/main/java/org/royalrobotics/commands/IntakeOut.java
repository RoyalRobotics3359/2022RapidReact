// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
/***
 * Rotates Intake motor reverse. This class ONLY is used to spit a ball out of our intake.
 * This class needs to make sure the intake arm is already extended.
 */
public class IntakeOut extends CommandBase {

  private Intake intake;
  private Hopper hopper;

  /**
   * 
   */
  public IntakeOut(Intake intakeSubsystem, Hopper hopperSubsystem) {
    super();
    intake = intakeSubsystem;
    hopper = hopperSubsystem;
    addRequirements(intake, hopper);  }


  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("INTAKEOUT Execute Running");
    intake.intakeMotorOut();
    hopper.hopperDown();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("INTAKEOUT End Running");
    intake.intakeStop();
    hopper.hopperStop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

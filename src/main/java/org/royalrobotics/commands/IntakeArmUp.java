// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;

/***
 * This class moves the intake arm solenoid upwards in its resting position and stops moving the intake motor.
 */
public class IntakeArmUp extends CommandBase {

  private Intake intake;

  /** Creates a new IntakeArmUp. */
  public IntakeArmUp(Intake intakeSubsystem) {
    super();
    intake = intakeSubsystem;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.intakeSolenoidIn();
    intake.intakeStop();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/***
 * Lowers the intake arm solenoid and turns the intake motors inwards to intake a ball.
 * This class needs to make sure the intake arm is already down.
 */
public class IntakeIn extends CommandBase {

  private Intake intake;
  private Hopper hopper;
  private JoystickButton button;

  /** Creates a new IntakeIn. */
  public IntakeIn(Intake intakeSubsystem, Hopper hopperSubsystem, JoystickButton joystickButton) {
    super();
    intake = intakeSubsystem;
    hopper = hopperSubsystem;
    button = joystickButton;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (button.get()){
      // System.out.println("IntakeIn.execute()");
      intake.intakeSolenoidOut();
      intake.intakeMotorIn();
      hopper.hopperUp();
    } 
    else
    {
      intake.intakeStop();
      hopper.hopperStop();
      intake.intakeSolenoidIn();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // System.out.println("IntakeIn.end(" + interrupted + ")");
    intake.intakeStop();
    hopper.hopperStop();
    intake.intakeSolenoidIn();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

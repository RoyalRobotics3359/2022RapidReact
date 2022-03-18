// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TimedIntakeIn extends CommandBase {

  private Intake intake;
  private Hopper hopper;

  private Timer timer;
  private long seconds;

  /** Creates a new IntakeIn. */
  public TimedIntakeIn(Intake intakeSubsystem, Hopper hopperSubsystem, long seconds) {
    super();
    intake = intakeSubsystem;
    hopper = hopperSubsystem;
    timer = new Timer();
    this.seconds = seconds;
    addRequirements(intake, hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // System.out.println("IntakeIn.execute()");
    intake.intakeSolenoidOut();
    intake.intakeMotorIn();
    hopper.hopperUp();
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
    if (timer.get() >= seconds){
      return true;
    }
    return false;
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class RetractClimber extends CommandBase {

  private Climber climber;
  private Timer timer;
  
  public RetractClimber(Climber c) {
    climber = c;
    addRequirements(climber);

    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    System.out.println("RetractClimber.execute()");
    climber.retractClimber();
    climber.startMotor();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    climber.stopMotor();
    System.out.println("RetractClimber.end("+interrupted+")");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return timer.hasElapsed(Constants.CLIMBER_RETRACT_TIME);
    return false;
  }
}

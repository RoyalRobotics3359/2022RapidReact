// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands.autocode;

import org.royalrobotics.OperatorConsole;
import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.Drive.*;

import edu.wpi.first.wpilibj.AnalogGyro;
import org.royalrobotics.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Drive10Feet extends CommandBase {

  private Drive drive;

  public Drive10Feet() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
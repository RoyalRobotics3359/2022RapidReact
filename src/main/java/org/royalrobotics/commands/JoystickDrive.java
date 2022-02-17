// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.Constants;
import org.royalrobotics.OperatorConsole;
import org.royalrobotics.subsystems.Drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class JoystickDrive extends CommandBase {

  Drive drive;
  OperatorConsole console;
  
  /** Creates a new JoystickDrive. */
  public JoystickDrive(OperatorConsole operatorConsole, Drive driveSubsystem) {
    super();
    // Use addRequirements() here to declare subsystem dependencies.
    drive = driveSubsystem;
    console = operatorConsole;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double leftPercent = console.getLeftY() * Constants.Speeds.driving.speed;
    double rightPercent = console.getRightY() * Constants.Speeds.driving.speed;
    SmartDashboard.putNumber("LEFT P", leftPercent);
    SmartDashboard.putNumber("RIGHT P", rightPercent);
    drive.setSpeed(leftPercent, rightPercent);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("JoystickDrive.end("+interrupted+")");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

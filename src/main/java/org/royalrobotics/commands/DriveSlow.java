// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.DriveSubsystem;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class DriveSlow extends ParallelCommandGroup {



  public DriveSlow(DriveSubsystem driveSubsystem, Intake intakeSubsystem, Hopper hopperSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new TimedDriveForward(driveSubsystem, 3, 0.15), new TimedIntakeIn(intakeSubsystem, hopperSubsystem, 3));
  }
}

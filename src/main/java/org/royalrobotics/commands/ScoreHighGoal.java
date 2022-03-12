// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.royalrobotics.subsystems.DriveSubsystem;
//import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Shooter;
import org.royalrobotics.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreHighGoal extends SequentialCommandGroup {

  private boolean finished;
  private boolean running;

  private Shooter shooter;
  private Hopper hopper;
  private Turret turret;
  private DriveSubsystem drive;


  /** Creates a new ScoreHighGoal. */
  public ScoreHighGoal(Shooter shooterSubsystem, Hopper hopperSubsystem, Turret turretSubsystem, DriveSubsystem driveSubsystem, double delay) {
    shooter = shooterSubsystem;
    hopper = hopperSubsystem;
    turret = turretSubsystem;
    drive = driveSubsystem;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new PrepareShooter(shooter, turret, drive), 
        new Shoot(shooter, hopper, delay)
    );
    finished = false;
    running = false;
  }

  @Override
  public void execute() {
    running = true;
    super.execute();
  }

  public void finish() {
    finished = true;
  }

  public boolean isRunning(){
    return running;
  }

  @Override
  public boolean isFinished() {
    if (!finished){
      return super.isFinished();
    }
    return true;
  }
}

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
  public ScoreHighGoal(Shooter shooterSubsystem, Hopper hopperSubsystem, Turret turretSubsystem, DriveSubsystem driveSubsystem, double delay, boolean aim) {
    shooter = shooterSubsystem;
    hopper = hopperSubsystem;
    turret = turretSubsystem;
    drive = driveSubsystem;
    if (aim){
      addCommands(
        new PrepareShooter(shooter, turret, drive), 
        new Shoot(shooter, hopper, delay)
    );
    } else{
      addCommands(
        new BringShooterUpToSpeed(shooterSubsystem),
        new Shoot(shooter, hopper, delay)
    );
    }
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    
    finished = false;
    running = false;
    System.out.println("scorehighgoal.scorehighgoal");
  }

  @Override
  public void execute() {
    running = true;
    if (!isFinished()){
      super.execute();
    }
  }

  public void finish() {
    finished = true;
  }

  public boolean isRunning(){
    return running;
  }

  @Override
  public void end(boolean interrupted) {
    shooter.turnOffPitchingMacine();
    hopper.hopperStop();
    hopper.hopperStopperExtend();
    running = false;
  }

  @Override
  public boolean isFinished() {
    if (finished){
      return true;
    }
    return super.isFinished();
  }
}

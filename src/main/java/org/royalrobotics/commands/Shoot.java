// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Shoot extends CommandBase {

  private Shooter shooter;
  private Hopper hopper;

  private Timer timer;
  private double delay;


  public Shoot(Shooter shooterSubsystem, Hopper hopperSubsystem, double delay) {
    shooter = shooterSubsystem;
    hopper = hopperSubsystem;
    this.delay = delay;
    addRequirements(shooter, hopper);
    timer = new Timer();
    timer.start();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.turnOnPichingMachine();
    hopper.hopperUp();
    hopper.hopperStopperRetract();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.turnOffPitchingMacine();
    hopper.hopperStop();
    hopper.hopperStopperExtend();
    timer.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    System.out.println(timer.get());
    if (timer.get() >= delay){
      return true;
    }
    return false;
  }
}

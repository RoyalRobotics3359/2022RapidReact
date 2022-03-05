// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

//import com.revrobotics.CANSparkMax;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Shooter;

// This command turns on the shooter's pitching machine and waits until
// the motor's RPM reaches the setpoint.  It does not turn off the shooter.
public class BringShooterUpToSpeed extends CommandBase {

  private Shooter shooter;
  private boolean running;
  private boolean ready;
  private Hopper hopper;

  /** Creates a new Shoot. */
  public BringShooterUpToSpeed(Shooter shooterSubsystem, Hopper hopperSubsystem) {
    shooter = shooterSubsystem;
    hopper = hopperSubsystem;
    running = false;
    ready = false;
    addRequirements(shooter, hopper);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //System.out.println("Shoot::initialize()");
    //running = false;
  }


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (running) {
      shooter.turnOnPichingMachine();
      SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
      if (isReadyToShoot()){
        hopper.hopperUp();
        hopper.hopperStopperRetract();
      }
    }
      // running = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // FIXME:  For testing only, normally we want the shooter to keep running
    shooter.turnOffPitchingMacine();
    hopper.hopperStop();
    hopper.hopperStopperExtend();
    running = false;
  }

  public boolean isReadyToShoot() {
    if (shooter.getRPM() >= Constants.SHOOTER_RPM_MINIMUM) {
      ready = true;
    }
    return ready;
  }

  public void stop() {
    shooter.turnOffPitchingMacine();
    running = false;
  }

  public boolean isRunning(){
    return running;
  }

  public void setRunning() {
    running = true;
    ready = false;
  }
  
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}

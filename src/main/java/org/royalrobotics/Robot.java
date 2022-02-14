// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

//import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Climber;
import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.Shooter;
import org.royalrobotics.commands.ExtendClimber;
import org.royalrobotics.commands.JoystickDrive;
import org.royalrobotics.commands.RetractClimber;
import org.royalrobotics.commands.ScoreHighGoal;
import org.royalrobotics.commands.TimedDriveForward;
import org.royalrobotics.commands.BringShooterUpToSpeed;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  
  private Timer m_timer = new Timer();

  private Drive drive;
  private OperatorConsole console;
  private Shooter shooter;
  private Climber climber;
  private Compressor compressor;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    drive = new Drive();
    shooter = new Shooter();
    climber = new Climber();
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    compressor.enableDigital();
    console = new OperatorConsole();

    console.getShootButton().whenPressed(new ScoreHighGoal(shooter));
    console.getExtendClimberButton().whenPressed(new ExtendClimber(climber));
    console.getRetractClimber().whenPressed(new RetractClimber(climber));

    CommandScheduler.getInstance().setDefaultCommand(drive, new JoystickDrive(console, drive));
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
    CommandScheduler.getInstance().schedule(new TimedDriveForward(drive, 3, 0.50));
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();
  }
}

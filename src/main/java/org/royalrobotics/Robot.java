// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.util.sendable.Sendable;
//import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
//import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;

//import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Climber;
import org.royalrobotics.subsystems.ColorSensor;
import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.DriveSubsystem;
import org.royalrobotics.subsystems.Hopper;
import org.royalrobotics.subsystems.Intake;
import org.royalrobotics.subsystems.Shooter;
import org.royalrobotics.subsystems.Turret;
import org.royalrobotics.commands.ExtendClimber;
import org.royalrobotics.commands.IntakeArmUp;
import org.royalrobotics.commands.IntakeIn;
import org.royalrobotics.commands.IntakeOut;
import org.royalrobotics.commands.JoystickDrive;
import org.royalrobotics.commands.RetractClimber;
import org.royalrobotics.commands.ReverseClimber;
import org.royalrobotics.commands.ScoreHighGoal;
import org.royalrobotics.commands.TimedDriveForward;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.I2C;

import javax.lang.model.element.ModuleElement.Directive;

import com.revrobotics.ColorSensorV3;

//import java.beans.Encoder;

import org.royalrobotics.commands.AimShooter;
import org.royalrobotics.commands.DriveForwardThenShoot;
//import org.royalrobotics.commands.BringShooterUpToSpeed;
import org.royalrobotics.commands.ManualShoot;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  
  private Timer m_timer = new Timer();

  private DriveSubsystem driveSubsystem;
  private Drive drive;

  private OperatorConsole console;
  private Shooter shooter;
  private Climber climber;
  private Compressor compressor;
  private Intake intake;
  private Hopper hopper;
  private Turret turret;
  private ColorSensor colorSensor;
  private RobotContainer container;


  private ManualShoot manualShootCommand;
  private ScoreHighGoal automatedShootCommand;
  private IntakeIn intakeIn;

  private UsbCamera camera;

  private Command driveForwardThenShoot;
  private Command sCurve;
  private Command autonomousCommand;

  private SendableChooser<Command> chooser;

  private boolean dPadPressed;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //drive = new Drive();
    driveSubsystem = new DriveSubsystem();
    climber = new Climber();
    intake = new Intake();
    hopper = new Hopper();
    turret = new Turret();
    shooter = new Shooter(turret);
    compressor = new Compressor(PneumaticsModuleType.CTREPCM);
    compressor.enableDigital();
    console = new OperatorConsole();
    colorSensor = new ColorSensor();
    // intakeIn = new IntakeIn(intake, hopper, console.getIntakeInButton());
    container = new RobotContainer();
   // console.getShootButton().whenPressed(new ScoreHighGoal(shooter, hopper));

     //  Enabling Camera Video
    camera = CameraServer.startAutomaticCapture();
     // Setting the resolution and brightness
    camera.setResolution(640, 480);
    camera.setBrightness(50);

    // Get a CvSink. This will capture Mats from the camera
    CvSink cvSink = CameraServer.getVideo();
    // Setup a CvSource. This will send images back to the Dashboard
    CvSource outputStream = CameraServer.putVideo("Rectangle", 640, 480);
    // set FPS
    camera.setFPS(12);

    // //Color Sensor
    // Color detectedColor = m_colorSensor.getColor();

    // SmartDashboard.putNumber("Red", detectedColor.red);
    // SmartDashboard.putNumber("Blue", detectedColor.blue);


    // CLIMB
    console.getExtendClimberButton().whenPressed(new ExtendClimber(climber));
    console.getReverseClimbMotor().whenHeld(new ReverseClimber(climber));

    console.getRetractClimber().whenHeld(new RetractClimber(climber));
    
    // INTAKE / HOPPER
    console.getIntakeInButton().whenHeld(new IntakeIn(intake, hopper));
    console.getIntakeOutButton().whileHeld(new IntakeOut(intake, hopper));
    //console.getIntakeArmUpButton().whenPressed(new IntakeArmUp(intake));

    //console.getTurretAimButton().whileHeld(new AimShooter(turret, drive));

    //console.getShoot().whileHeld(new BringShooterUpToSpeed(shooter));
    // CommandScheduler.getInstance().setDefaultCommand(intake, intakeIn);
    // CommandScheduler.getInstance().setDefaultCommand(shooter, shootCommand);


    CommandScheduler.getInstance().setDefaultCommand(driveSubsystem, new JoystickDrive(console, driveSubsystem));
    //CommandScheduler.getInstance().setDefaultCommand(drive, new JoystickDrive(console, drive));
  
    driveForwardThenShoot = new DriveForwardThenShoot(shooter, hopper, turret, driveSubsystem, 3.0);
    sCurve = RobotContainer.SCurveCommand(driveSubsystem);
    
  
    chooser = new SendableChooser<>();

    chooser.setDefaultOption("Back up, shoot, back up out of tarmac", driveForwardThenShoot);
    chooser.addOption("S-Curve", sCurve);
    chooser.addOption("Back Out Of Tarmack (3sec, -.2)", new TimedDriveForward(driveSubsystem, 3, -0.2));
    SmartDashboard.putData(chooser);

    dPadPressed = false;
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.reset();
    m_timer.start();
    driveSubsystem.resetOdometry(new Pose2d());
    //CommandScheduler.getInstance().schedule(new TimedDriveForward(driveSubsystem, 2, -0.3));

    //CommandScheduler.getInstance().schedule(RobotContainer.DriveStraightCommand(driveSubsystem, 1));
    //CommandScheduler.getInstance().schedule(new DriveForwardThenShoot(shooter, hopper, turret, driveSubsystem, 3.0));
    //CommandScheduler.getInstance().schedule(RobotContainer.SCurveCommand(driveSubsystem));

    //container.getAutonomousCommand().schedule();

    //String autoList[] = {"Back Out Of Tarmack (3sec, -.2)", "S-Curve", "Shoot"};

    //SmartDashboard.putStringArray("Auto List", autoList);

    

    // At the beginning of auto
    // String autoName = SmartDashboard.getString("Auto Selector", "Drive Forwards"); // This would make "Drive Forwards the default auto
    // switch(autoName) {
    //   case "Back Out Of Tarmack (3sec, -.2)":
    //     new TimedDriveForward(driveSubsystem, 2, -0.25).schedule();
    //     break;
    //   case "S-Curve":
    //     sCurve.schedule();
    //     break;
    //   case "Shoot":
    //     // auto he
    //     break;
    // }

    autonomousCommand = getAutonomousCommand();

    if (autonomousCommand != null){
      autonomousCommand.schedule();
    }

    

  }

  public Command getAutonomousCommand() {
    return chooser.getSelected();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();

  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {
    if (autonomousCommand != null){
      autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {

    // used for manual shooting
    if (console.getManualTrigger() > 0.67)
    {
      if (manualShootCommand == null) {
        manualShootCommand = new ManualShoot(shooter, hopper);
      }
      if (!manualShootCommand.isRunning()) 
      {
        //System.out.println("running");(
        manualShootCommand.schedule();
      }
    }
    else
    {
      if (manualShootCommand != null) {
        manualShootCommand.finish();
        manualShootCommand = null;
      }
    }
    
    // Used for automated Shooting
    if (manualShootCommand == null && console.getAutomatedTrigger() > 0.67){
        if (automatedShootCommand == null){
          automatedShootCommand = new ScoreHighGoal(shooter, hopper, turret, driveSubsystem, 3.0, true);
        } 
        if (!automatedShootCommand.isRunning()){
          automatedShootCommand.schedule();
        }
        if (automatedShootCommand.isFinished()) {
          automatedShootCommand.cancel();
        }
    }
    else if (manualShootCommand == null && console.getAutomatedTrigger() <= 0.67){
      if (automatedShootCommand != null){
        automatedShootCommand.finish();
        automatedShootCommand = null;
      }
    }
    CommandScheduler.getInstance().run();

    SmartDashboard.putNumber("Distance", turret.getDistanceFromTarget());


    int dPadAngle = console.getDPadAngle();
    if (dPadAngle == 90){
      dPadPressed = true;
      turret.rotateTurretLeft(.1);
    } else if (dPadAngle == 270){
      dPadPressed = true;
      turret.rotateTurretRight(.1);
    } else if (dPadPressed){
      dPadPressed = false;
      turret.setTurretStop();
    }
    //SmartDashboard.putNumber("Distance From Target: ", turret.getDistanceFromTarget());
    //SmartDashboard.putNumber("Horizantal Error", turret.getHorizantalAngle());
  }

  

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    CommandScheduler.getInstance().run();

    if (ColorSensor.match.color == ColorSensor.blue) {
      if (Constants.ALLIANCE.equals("red") == true) {
        Shooter.shooterMotor.set(0.4);
      } else if (Constants.ALLIANCE.equals("blue") == true) {
        if (console.getManualTrigger() > 0.67)
    {
      if (manualShootCommand == null) {
        manualShootCommand = new ManualShoot(shooter, hopper);
      }
      if (!manualShootCommand.isRunning()) 
      {
        //System.out.println("running");(
        CommandScheduler.getInstance().schedule(manualShootCommand);
      }
    }
    else
    {
      if (manualShootCommand != null) {
        manualShootCommand.finish();
        manualShootCommand = null;
      }
    }
      }
    }
    
    if (ColorSensor.match.color == ColorSensor.red) {
      if (Constants.ALLIANCE.equals("blue") == true) {
        Shooter.shooterMotor.set(0.4);
      } else if (Constants.ALLIANCE.equals("red") == true) {
        if (console.getManualTrigger() > 0.67)
    {
      if (manualShootCommand == null) {
        manualShootCommand = new ManualShoot(shooter, hopper);
      }
      if (!manualShootCommand.isRunning()) 
      {
        //System.out.println("running");(
        CommandScheduler.getInstance().schedule(manualShootCommand);
      }
    }
    else
    {
      if (manualShootCommand != null) {
        manualShootCommand.finish();
        manualShootCommand = null;
      }
    }
      }
    }

  }
}



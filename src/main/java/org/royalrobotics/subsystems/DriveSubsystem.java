package org.royalrobotics.subsystems;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
//import edu.wpi.first.wpilibj.examples.ramsetecommand.Constants.DriveConstants;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class DriveSubsystem extends SubsystemBase {

  private CANSparkMax leftMaster, leftFollower, rightMaster, rightFollower;

  
  // The motors on the left side of the drive.
  private final MotorControllerGroup m_leftMotors;

  // The motors on the right side of the drive.
  private final MotorControllerGroup m_rightMotors;

  // The robot's drive
  private DifferentialDrive m_drive;

  



  private final RelativeEncoder m_leftEncoder, m_rightEncoder;

  private PIDController leftPIDController;
  private PIDController rightPIDController;

  private static final double LEFT_KP = 0.0;
  private static final double LEFT_KI = 0.0;
  private static final double LEFT_KD = 0.0;

  private static final double RIGHT_KP = 0.0;
  private static final double RIGHT_KI = 0.0;
  private static final double RIGHT_KD = 0.0;







  // The gyro sensor
  private final Gyro m_gyro = new ADXRS450_Gyro();

  // Odometry class for tracking robot pose
  private final DifferentialDriveOdometry m_odometry;


  private final DifferentialDriveKinematics m_kinematics;



  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.

    leftMaster = new CANSparkMax(CanId.frontLeft.id, MotorType.kBrushless);
    leftFollower = new CANSparkMax(CanId.rearLeft.id, MotorType.kBrushless);
    rightMaster = new CANSparkMax(CanId.frontRight.id, MotorType.kBrushless);
    rightFollower = new CANSparkMax(CanId.rearRight.id, MotorType.kBrushless);

    m_leftMotors = new MotorControllerGroup(leftMaster, leftFollower);
    m_rightMotors = new MotorControllerGroup(rightMaster, rightFollower);

    m_rightMotors.setInverted(true);

    m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);


    m_leftEncoder = leftMaster.getEncoder();
    m_rightEncoder = rightMaster.getEncoder();

    /***
     * These are regular PID controllers because the Ramsete Controller that these are used in in the Robotontainer class
     * only accepts PIDController not SparkMaxPIDController
     */
    leftPIDController = new PIDController(LEFT_KP, LEFT_KI, LEFT_KD);
    leftPIDController = new PIDController(RIGHT_KP, RIGHT_KI, RIGHT_KD);




    resetEncoders();
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());

    m_kinematics = new DifferentialDriveKinematics(Constants.kTrackwidthMeters); 
  }

  @Override
  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(
        m_gyro.getRotation2d(), getLeftDistanceTraveled(), getRightDistanceTraveled());

  }

  public double getLeftDistanceTraveled() {
    return m_leftEncoder.getVelocity() / Constants.GEAR_RATIO * 2.0 * Math.PI * Units.inchesToMeters(Constants.WHEEL_RADIUS) / 60.0;
  }

  public double getRightDistanceTraveled() {
    return m_rightEncoder.getVelocity() / Constants.GEAR_RATIO * 2.0 * Math.PI * Units.inchesToMeters(Constants.WHEEL_RADIUS) / 60.0;
  }

  public PIDController getLeftPidController(){
    return leftPIDController;
  }

  public PIDController getRightPIDCPidController(){
    return rightPIDController;
  }
  /**
   * Returns the currently-estimated pose of the robot.
   *
   * @return The pose.
   */
  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  /**
   * Returns the current wheel speeds of the robot.
   *
   * @return The current wheel speeds.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftDistanceTraveled(), getRightDistanceTraveled());
  }

  public DifferentialDriveKinematics getKinematics(){
    return m_kinematics;
  }

  /**
   * Resets the odometry to the specified pose.
   *
   * @param pose The pose to which to set the odometry.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /**
   * Controls the left and right sides of the drive directly with voltages.
   *
   * @param leftVolts the commanded left output
   * @param rightVolts the commanded right output
   */
  public void tankDriveVolts(double leftVolts, double rightVolts) {
    //SmartDashboard.putNumber("Left volts: ", leftVolts);
    //SmartDashboard.putNumber("Right volts: ", rightVolts);
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
    m_drive.feed();
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    m_leftEncoder.setPosition(0.0);
    m_rightEncoder.setPosition(0.0);
  }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  public double getAverageEncoderDistance() {
    return (getLeftDistanceTraveled() + getRightDistanceTraveled()) / 2.0;
  }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  
  

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from -180 to 180
   */
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return -1.0 * m_gyro.getRate();
  }


}
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.lang.Math;

/**
 * Turret
 * 
 * This class is responsible for aiming the robot's shooter to point
 * at the target using the LimeLight camera
 */
public class Turret extends SubsystemBase {

  private NetworkTable table;
  private NetworkTableEntry targetOffset_H; // Changed from targetOffsetAngle_Horizontal
  private NetworkTableEntry targetOffset_V; // Changed from targetOffsetAngle_Vertical
  private NetworkTableEntry targetArea;
  private NetworkTableEntry targetSkew;
  private NetworkTableEntry targetDetected;

  private CANSparkMax turretMotor;
  private SparkMaxPIDController turretController;

  private boolean ready;

  private Double angleOfTurret, targetAngle, vFieldOfView, hFieldOfView, vPh;
  private Integer turretGearRatio, ticksPerRotation;

  private static final double KP = 0.000;
  private static final double KI = 0.000;
  private static final double KD = 0.000;

  public Turret() {
    if (Constants.TURRET_EXISTS) {
      table = NetworkTableInstance.getDefault().getTable("limelight");
      turretMotor = new CANSparkMax(CanId.turretMotor.id, MotorType.kBrushless);
      turretController = turretMotor.getPIDController();
      turretMotor.setIdleMode(IdleMode.kBrake);
      turretController.setP(KP);
      turretController.setI(KI);
      turretController.setD(KD);
      turretController.setReference(0.0, CANSparkMax.ControlType.kPosition);
      turretGearRatio = 1;
      ticksPerRotation = 42;
      vFieldOfView = Math.toRadians(49.7 / 2); // "/2" is for formula
      hFieldOfView = 59.6;
      vPh = 2.0 * Math.tan(vFieldOfView);
      ready = false;

      targetOffset_H = table.getEntry("tx");
      targetOffset_V = table.getEntry("ty");
      targetArea = table.getEntry("ta");
      targetSkew = table.getEntry("ts");

      // angle limelight is tilted backwards from vertical
      double limelightMountAngleDegrees = 0.0;

      // distance from the center of the Limelight lens to the floor
      double limelightLensHeightInches = 0.0;

      // distance from the target to the floor

    }
  }

  public boolean isTargetDetected() {
    if (table.getEntry("targetArea").getDouble(0.0) > 0.0) {
      return true;
    } else {
      return false;
    }
  }

  public boolean aim() {

    boolean found = false;
    if (Constants.TURRET_EXISTS) {
      targetDetected = table.getEntry("tv");
      if (targetDetected.getDouble(0.0) == 1) {
        // targetOffset_H = table.getEntry("tx");
        // targetOffset_V = table.getEntry("ty");
        // targetArea = table.getEntry("ta");
        // targetSkew = table.getEntry("ts");

        // double tx = targetOffset_H.getDouble(0.0);
        // SmartDashboard.putNumber("Target Angle (Horizontal)",
        // targetOffsetAngle_Horizontal.getDouble(0.0));
        SmartDashboard.putNumber("Target Angle (Horizontal)", getTx());

        // SmartDashboard.putNumber("Target Angle (Vertical)",
        // targetOffset_V.getDouble(0.0));

        angleOfTurret = (turretMotor.getEncoder().getPosition()) * (turretGearRatio) * (360 / ticksPerRotation);
        targetAngle = vPh / 2 * table.getEntry("ty").getDouble(0.0);

        // if (getTx() >= 5.0){
        // rotateTurretLeft();
        // System.out.println("turn right");
        // return false;
        // } else if (getTx() <= -5.0){
        // rotateTurretRight();
        // System.out.println("turn left");
        // return false;
        // } else{
        // setTurretStop();
        // System.out.println("locked on");
        // return true;
        // }

        if (getTx() >= 15.0) {
          rotateTurretLeft(0.2);
        } else if (getTx() < 15.0 && getTx() >= 5.0) {
          rotateTurretLeft(0.1);
        } else if (getTx() <= -5.0 && getTx() > -15.0) {
          rotateTurretRight(0.1);
        } else if (getTx() < -15.0) {
          rotateTurretRight(0.2);
        } else {
          setTurretStop();
          found = true;
        }

      }

    }
    return found;
  }

  public double getDistanceFromTarget() {

    double ty = getVerticalAngle();

    double limelightMountAngleDegrees = 33.0;
    double limelightLensHeightInches = 42.0;
    double goalHeightInches = 107.0; // 104 is from floor to reflective tape on bar; added +3in so it can get over
                                     // that bar

    double angleToGoalDegrees = limelightMountAngleDegrees + ty;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    // calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches)
        / Math.tan(angleToGoalRadians);

    SmartDashboard.putNumber("Distance From Goal", distanceFromLimelightToGoalInches);

    return distanceFromLimelightToGoalInches;

  }

  public void rotateTurretLeft(double speed) {
    if (Constants.TURRET_EXISTS) {
      turretMotor.set(speed);
    }
  }

  public void rotateTurretRight(double speed) {
    if (Constants.TURRET_EXISTS) {
      turretMotor.set(-1 * speed);
    }
  }

  public void setTurretSpeed(double speed) {
    if (Constants.TURRET_EXISTS) {
      turretMotor.set(speed);
    }

  }

  public void setTurretStop() {
    if (Constants.TURRET_EXISTS) {
      turretMotor.set(0.0);
    }
  }

  public double getHorizantalAngle() {
    return targetOffset_H.getDouble(0.0);

  }

  public double getVerticalAngle() {
    // targetOffset_V = table.getEntry("ty");
    return targetOffset_V.getDouble(0.0);
  }

  public double getTx() {
    return targetOffset_H.getDouble(0.0);
  }

  public void getDistance() {
    // TODO
  }

}

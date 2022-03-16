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
  private NetworkTableEntry targetOffset_H; //Changed from targetOffsetAngle_Horizontal
  private NetworkTableEntry targetOffset_V; //Changed from targetOffsetAngle_Vertical
  private NetworkTableEntry targetArea;
  private NetworkTableEntry targetSkew;
  private NetworkTableEntry targetDetected;

  private CANSparkMax turretMotor;
  private SparkMaxPIDController turretController;


  private Double angleOfTurret, targetAngle, vFieldOfView, hFieldOfView, vPh;
  private Integer turretGearRatio, ticksPerRotation;

  private double limelightMountAngleDegrees, limelightLensHeightInches, goalHeightInches;


  private static final double KP = 0.000;
  private static final double KI = 0.000;
  private static final double KD = 0.000;

  public Turret() {
    if (Constants.TURRET_EXISTS){
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
      vFieldOfView = Math.toRadians(49.7/2); // "/2" is for formula
      hFieldOfView = 59.6;
      vPh = 2.0*Math.tan(vFieldOfView);


      //angle limelight is tilted backwards from vertical
      double limelightMountAngleDegrees = 0.0;

      // distance from the center of the Limelight lens to the floor
      double limelightLensHeightInches = 0.0;

      // distance from the target to the floor
      double goalHeightInches = 107.0;//104 is from floor to reflective tape on bar; added +3in so it can get over that bar
      

      
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

    

    if (Constants.TURRET_EXISTS ) {
      targetDetected = table.getEntry("tv");
      if (targetDetected.getDouble(0.0) == 1){
        targetOffset_H = table.getEntry("tx");
        targetOffset_V = table.getEntry("ty");
        targetArea = table.getEntry("ta");
        targetSkew = table.getEntry("ts");
      
        

        double tx =  targetOffset_H.getDouble(0.0);
        //SmartDashboard.putNumber("Target Angle (Horizontal)", targetOffsetAngle_Horizontal.getDouble(0.0));
        SmartDashboard.putNumber("Target Angle (Horizontal)", tx);

        SmartDashboard.putNumber("Target Angle (Vertical)", targetOffset_V.getDouble(0.0));

        angleOfTurret = (turretMotor.getEncoder().getPosition()) * (turretGearRatio) * (360/ticksPerRotation);
        
        targetAngle = vPh/2 * table.getEntry("ty").getDouble(0.0);

        //double turretAdjust = turretController.calculate(tx, 0.0);

        //SmartDashboard.putNumber("Turret Adjust", turretAdjust);
        
        //turretMotor.set(turretAdjust);
      }
    } 
    return false;
  }

  public double getDistanceFromTarget(){

    double ty = getVerticalAngle();

    double angleToGoalDegrees = limelightMountAngleDegrees + ty;
    double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);

    //calculate distance
    double distanceFromLimelightToGoalInches = (goalHeightInches - limelightLensHeightInches) / Math.tan(angleToGoalRadians);

    SmartDashboard.putNumber("Distance From Goal", distanceFromLimelightToGoalInches);

    return distanceFromLimelightToGoalInches;
    
}

  public void rotateTurretLeft(){
    if (Constants.TURRET_EXISTS){
      turretMotor.set(.3);
    }
  }

  public void rotateTurretRight(){
    if (Constants.TURRET_EXISTS){
      turretMotor.set(-.3);
    }
  }

  public void setTurretSpeed(double speed){
    if (Constants.TURRET_EXISTS){
      turretMotor.set(speed);
    }

  }

  public void setTurretStop() {
    if (Constants.TURRET_EXISTS){
      turretMotor.set(0.0);
    }
  }

  public double getHorizantalAngle() {
    return targetOffset_H.getDouble(0.0);
    
  }

  public double getVerticalAngle(){
    return targetOffset_V.getDouble(0.0);
  }


  public void getDistance(){
    //TODO
  }


}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** 
 * Turret
 * 
 * This class is responsible for aiming the robot's shooter to point
 * at the target using the LimeLight camera
 */
public class Turret extends SubsystemBase {

  private NetworkTable table;
  private NetworkTableEntry targetOffsetAngle_Horizontal;
  private NetworkTableEntry targetOffsetAngle_Vertical;
  private NetworkTableEntry targetArea;
  private NetworkTableEntry targetSkew;

  private CANSparkMax turretMotor;

  private Double angleOfTurret;
  private Integer turretGearRatio, ticksPerRotation;

  private PIDController turretController;

  public static final double Kp = 0.2;
  public static final double Ki = 0.005;
  public static final double Kd = 0.001;


  public Turret() {
    if (Constants.TURRET_EXISTS){
      table = NetworkTableInstance.getDefault().getTable("limelight");
      turretMotor = new CANSparkMax(CanId.turretMotor.id, MotorType.kBrushless);
      turretGearRatio = 1;
      ticksPerRotation = 42;
      turretController = new PIDController(Kp, Ki, Kd);

    }
  }

  public boolean aim() {
    if (Constants.TURRET_EXISTS) {
      targetOffsetAngle_Horizontal = table.getEntry("tx");
      targetOffsetAngle_Vertical = table.getEntry("ty");
      targetArea = table.getEntry("ta");
      targetSkew = table.getEntry("ts");

      double tx =  table.getEntry("tx").getDouble(0.0);

      //SmartDashboard.putNumber("Target Angle (Horizontal)", targetOffsetAngle_Horizontal.getDouble(0.0));
      SmartDashboard.putNumber("Target Angle (Horizontal)", tx);

      SmartDashboard.putNumber("Target Angle (Vertical)", targetOffsetAngle_Vertical.getDouble(0.0));

      angleOfTurret = (turretMotor.getEncoder().getPosition()) * (turretGearRatio) * (360/ticksPerRotation);


      double turretAdjust = turretController.calculate(tx, 0.0);

      SmartDashboard.putNumber("Turret Adjust", turretAdjust);
      
      turretMotor.set(turretAdjust);
      

    }
    return false;
  }

  public void setTurretSpeed(double speed){
    turretMotor.set(speed);

  }

  public void setTurretStop() {
    turretMotor.set(0.0);

  }


}

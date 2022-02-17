// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

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

  public Turret() {
  }

  public boolean aim() {
    table = NetworkTableInstance.getDefault().getTable("limelight");
    targetOffsetAngle_Horizontal = table.getEntry("tx");
    targetOffsetAngle_Vertical = table.getEntry("ty");
    targetArea = table.getEntry("ta");
    targetSkew = table.getEntry("ts");

    SmartDashboard.putNumber("Target Angle (Horizontal)", targetOffsetAngle_Horizontal.getNumber(0.0).doubleValue());
    SmartDashboard.putNumber("Target Angle (Vertical)", targetOffsetAngle_Vertical.getNumber(0.0).doubleValue());
    return false;
  }


}

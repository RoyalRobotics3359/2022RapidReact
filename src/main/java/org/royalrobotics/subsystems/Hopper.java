// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;
import org.royalrobotics.Constants.Speeds;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hopper extends SubsystemBase {

  private CANSparkMax hopperMotor;

  public Hopper() {
    if (Constants.HOPPER_EXSISTS){
      hopperMotor = new CANSparkMax(CanId.hopperMotor.id, MotorType.kBrushless);
      hopperMotor.setInverted(CanId.hopperMotor.reversed);
    }
  }

  public void hopperIn(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(Speeds.hopperIn.speed);
    }
  }

  public void hopperOut(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(Speeds.hopperOut.speed);
    }
  }

  public void hopperStop(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(0.0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

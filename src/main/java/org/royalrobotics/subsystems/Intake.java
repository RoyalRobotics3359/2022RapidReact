// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeArmMotor;

  public Intake() {
    super();
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor = new CANSparkMax(CanId.intakeArm.id, MotorType.kBrushless);
      intakeArmMotor.setInverted(CanId.intakeArm.reversed);
    }
  }

  public void intakeMotorIn(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor.set(Constants.Speeds.intakeIn.speed);
    }
  }

  public void intakeMotorOut(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor.set(Constants.Speeds.intakeOut.speed);
    }
  }

  public void intakeStop(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor.set(0.0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;
import org.royalrobotics.Constants.Pnuematics;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  private CANSparkMax intakeArmMotor;
  private DoubleSolenoid intakeArmSolenoid;

  public Intake() {
    super();
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor = new CANSparkMax(CanId.intakeArm.id, MotorType.kBrushed);
      intakeArmMotor.setInverted(CanId.intakeArm.reversed);
      intakeArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pnuematics.extendIntake.channel, Pnuematics.retractIntake.channel);
      intakeArmSolenoid.set(Value.kReverse);
    }
  }

  public void intakeMotorIn(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmMotor.set(Constants.Speeds.intakeIn.speed);
      System.out.println("intake in");
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

  public void intakeSolenoidOut(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmSolenoid.set(Value.kForward);
    }
  }

  public void intakeSolenoidIn(){
    if (Constants.INTAKE_EXSISTS){
      intakeArmSolenoid.set(Value.kReverse);
    }
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

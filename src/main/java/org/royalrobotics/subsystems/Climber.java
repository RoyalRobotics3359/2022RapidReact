// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.Pnuematics;
import org.royalrobotics.Constants.Speeds;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Climber extends SubsystemBase {

  private DoubleSolenoid climbSolenoid;
  private CANSparkMax climbMotor;


  public Climber() {
    if (Constants.CLIMBER_EXISTS) {
      climbMotor = new CANSparkMax(CanId.climber.id, MotorType.kBrushed);
      climbMotor.setInverted(CanId.climber.reversed);
      stopMotor();
      climbSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pnuematics.extendClimber.channel, Pnuematics.retractClimber.channel);
      retractClimber();
    }
  }

  public void extendClimber() {
    if (Constants.CLIMBER_EXISTS) {
      climbSolenoid.set(Value.kForward);
    }
  }

  public void retractClimber() {
    if (Constants.CLIMBER_EXISTS) {
      climbSolenoid.set(Value.kReverse);
      startMotor();
    }
  }

  public void startMotor() {
    if (Constants.CLIMBER_EXISTS){
      climbMotor.set(Speeds.climber.speed);
    }
  }

  public void stopMotor() {
    if (Constants.CLIMBER_EXISTS) {
      climbMotor.set(0.0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

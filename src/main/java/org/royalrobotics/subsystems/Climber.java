// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.Pnuematics;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Climber extends SubsystemBase {

  public DoubleSolenoid climbSolenoid; 

  public Climber() {
    climbSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pnuematics.extendClimber.channel, Pnuematics.retractClimber.channel);
    retractClimber();
  }

  public void extendClimber() {
    climbSolenoid.set(Value.kForward);
  }

  public void retractClimber() {
    climbSolenoid.set(Value.kReverse);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;
import org.royalrobotics.Constants.Pnuematics;
import org.royalrobotics.Constants.Speeds;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Hopper extends SubsystemBase {

  private CANSparkMax hopperMotor;
  private DoubleSolenoid hopperStopperSolenoid;

  public Hopper() {
    if (Constants.HOPPER_EXSISTS){
      // FIXME; verify motortype
      hopperMotor = new CANSparkMax(CanId.hopperMotor.id, MotorType.kBrushed);
      hopperMotor.setInverted(CanId.hopperMotor.reversed);
      hopperStopperSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, Pnuematics.extendHopperStopper.channel, Pnuematics.retractHopperStopper.channel);
      hopperStopperExtend();
    }
  }

  public void hopperUp(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(Speeds.hopperIn.speed);
    }
  }

  public void hopperDown(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(Speeds.hopperOut.speed);
    }
  }

  public void hopperStop(){
    if (Constants.HOPPER_EXSISTS){
      hopperMotor.set(0.0);
    }
  }

  public void hopperStopperExtend(){
    if (Constants.HOPPER_EXSISTS){
      hopperStopperSolenoid.set(Value.kForward);
    }
  }

  public void hopperStopperRetract(){
    if (Constants.HOPPER_EXSISTS){
      hopperStopperSolenoid.set(Value.kReverse);
    }
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

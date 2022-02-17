package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    private CANSparkMax shooterMotor;
    private CANSparkMax hopperMotor;
    
    // CANSparkMax motor controller for shooter pitching machine wheel
    // CANSparkMax motor controller for turret rotation
    // LimeLight camera to detect direction high goal

    public Shooter() {
        super();
        if (Constants.SHOOTER_EXSISTS){
            shooterMotor = new CANSparkMax(CanId.shooter.id, MotorType.kBrushless);
            shooterMotor.setInverted(CanId.shooter.reversed);

            hopperMotor = new CANSparkMax(CanId.hopper.id, MotorType.kBrushless);
            hopperMotor.setInverted(CanId.hopper.reversed);
        }
    }

    public double getRPM() {
        if (Constants.SHOOTER_EXSISTS){
            return shooterMotor.getEncoder().getVelocity();
        }
        else{
            return 0.0;
        }
    }

    public void turnOnPichingMachine() {
        if (Constants.SHOOTER_EXSISTS){
            shooterMotor.set(Constants.Speeds.shoot.speed);
        }
    }

    public void turnOffPitchingMacine() {
        if (Constants.SHOOTER_EXSISTS){
            shooterMotor.set(0.0);
        }
    }

    public void turnOnHopper() {
        if (Constants.SHOOTER_EXSISTS){
            hopperMotor.set(Constants.Speeds.hopperIn.speed);
        }
    }

    public void turnOffHopper() {
        if (Constants.SHOOTER_EXSISTS){
            hopperMotor.set(0.0);
        }
    }
}

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
        shooterMotor = new CANSparkMax(CanId.shooter.id, MotorType.kBrushless);
        shooterMotor.setInverted(CanId.shooter.reversed);

        hopperMotor = new CANSparkMax(CanId.hopper.id, MotorType.kBrushless);
        hopperMotor.setInverted(CanId.hopper.reversed);
    }

    public double getRPM() {
        return shooterMotor.getEncoder().getVelocity();
    }

    public void turnOnPichingMachine() {
        shooterMotor.set(Constants.Speeds.shoot.speed);
    }

    public void turnOffPitchingMacine() {
        shooterMotor.set(0.0);
    }

    public void turnOnHopper() {
        hopperMotor.set(Constants.Speeds.hopper.speed);
    }

    public void turnOffHopper() {
        hopperMotor.set(0.0);
    }
}

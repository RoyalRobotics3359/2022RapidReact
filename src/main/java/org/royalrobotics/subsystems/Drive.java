package org.royalrobotics.subsystems;

import org.royalrobotics.Constants;
import org.royalrobotics.OperatorConsole;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive extends SubsystemBase {
    private CANSparkMax leftMaster;
    private CANSparkMax leftFollower;
    private CANSparkMax rightMaster;
    private CANSparkMax rightFollower;

    private OperatorConsole oc;

    public Drive() {
        super();
        leftMaster = new CANSparkMax(CanId.frontLeft.id, MotorType.kBrushless);
        leftFollower = new CANSparkMax(CanId.rearLeft.id, MotorType.kBrushless);
        rightMaster = new CANSparkMax(CanId.frontRight.id, MotorType.kBrushless);
        rightFollower = new CANSparkMax(CanId.rearRight.id, MotorType.kBrushless);

        // Set the followers to follow their respetive masters
        leftFollower.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax, CanId.frontLeft.id);
        rightFollower.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax, CanId.frontRight.id);


        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        rightMaster.setInverted(CanId.frontRight.reversed);
        rightFollower.setInverted(CanId.rearRight.reversed);
        leftMaster.setInverted(CanId.frontLeft.reversed);
        leftFollower.setInverted(CanId.rearRight.reversed);


        // Set the voltage ramp rate to prevent jerkiness
        leftMaster.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        rightMaster.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        leftFollower.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        rightFollower.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
    } 

    public void setSpeed(double leftPercentPower, double rightPercentPower) {
        leftMaster.set(leftPercentPower);
        rightMaster.set(rightPercentPower);
    }

    public void stop() {
        leftMaster.set(0.0);
        rightMaster.set(0.0);
    }
}
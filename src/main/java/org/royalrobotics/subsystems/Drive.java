package org.royalrobotics.subsystems;

import org.royalrobotics.Constants;
import org.royalrobotics.OperatorConsole;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive extends SubsystemBase {
    private CANSparkMax leftMaster, leftFollower, rightMaster, rightFollower;

    private NetworkTable table;

    private RelativeEncoder LeftMasterEncoder;
    public Encoder encoder;

    private OperatorConsole oc;

    private NetworkTableEntry targetOffset_H, targetOffset_V, targetArea, targetSkew;

    public static final double wheelDiameter = 6;
    public static final double pulsePerRevolution = 360;
    public static final double encoderGearRatio = 3;
    public static final double gearRatio = 64.0/20.0;
    public static final double Fudgefactor = 1.0;

    public Drive() {
        super();
        if (Constants.DRIVE_EXISTS) {
        encoder = new Encoder(0, 1); 
        leftMaster = new CANSparkMax(CanId.frontLeft.id, MotorType.kBrushless);
        leftFollower = new CANSparkMax(CanId.rearLeft.id, MotorType.kBrushless);
        rightMaster = new CANSparkMax(CanId.frontRight.id, MotorType.kBrushless);
        rightFollower = new CANSparkMax(CanId.rearRight.id, MotorType.kBrushless);

        table = NetworkTableInstance.getDefault().getTable("limelight");

        rightMaster.restoreFactoryDefaults();
        rightFollower.restoreFactoryDefaults();
        leftMaster.restoreFactoryDefaults();
        leftFollower.restoreFactoryDefaults();

        // Grouping encoders together
        MotorControllerGroup leftEncoderGroup = new MotorControllerGroup(leftMaster, leftFollower);
        MotorControllerGroup rightEncoderGroup = new MotorControllerGroup(rightMaster, rightFollower);

        //
        // DifferentialDrive diffDrive = new DifferentialDrive(leftEncoderGroup, rightEncoderGroup);

        // Set the followers to follow their respetive masters
        leftFollower.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax, CanId.frontLeft.id);
        rightFollower.follow(CANSparkMax.ExternalFollower.kFollowerSparkMax, CanId.frontRight.id);

        // We need to invert one side of the drivetrain so that positive voltages
        // result in both sides moving forward. Depending on how your robot's
        // gearbox is constructed, you might have to invert the left side instead.
        rightMaster.setInverted(CanId.frontRight.reversed);
        rightFollower.setInverted(CanId.rearRight.reversed);
        leftMaster.setInverted(CanId.frontLeft.reversed);
        leftFollower.setInverted(CanId.rearLeft.reversed);

        rightMaster.setIdleMode(IdleMode.kBrake);
        rightFollower.setIdleMode(IdleMode.kBrake);
        leftMaster.setIdleMode(IdleMode.kBrake);
        leftFollower.setIdleMode(IdleMode.kBrake);

        // Set the voltage ramp rate to prevent jerkiness
        //leftMaster.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        //rightMaster.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        //leftFollower.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        //rightFollower.setOpenLoopRampRate(Constants.DRIVE_MOTOR_RAMP_TIME);
        }
    } 

    public void setSpeed(double leftPercentPower, double rightPercentPower) {
        if (Constants.DRIVE_EXISTS) {
            if (leftPercentPower >= -Constants.JOYSTICK_DEADBAND && leftPercentPower <= Constants.JOYSTICK_DEADBAND){
                leftMaster.set(0.0);
            }else {
                leftMaster.set(leftPercentPower);
            }

            if (rightPercentPower >= -Constants.JOYSTICK_DEADBAND && rightPercentPower <= Constants.JOYSTICK_DEADBAND){
                rightMaster.set(0.0);
            }else{
                rightMaster.set(rightPercentPower);
            }
            SmartDashboard.putNumber("Left M Current", leftMaster.getOutputCurrent());
            SmartDashboard.putNumber("Right M Current", rightMaster.getOutputCurrent());
            SmartDashboard.putNumber("Left F Current", leftFollower.getOutputCurrent());
            SmartDashboard.putNumber("Right F Current", rightFollower.getOutputCurrent());

            SmartDashboard.putNumber("Left rpm", leftMaster.getEncoder().getVelocity());
            SmartDashboard.putNumber("Right rpm", rightMaster.getEncoder().getVelocity());
            }
    }

    public void aim(Turret turret) {

        double horizantal_angle = turret.getHorizantalAngle();
        double vertical_angle = turret.getVerticalAngle();

        double tx =  targetOffset_H.getDouble(0.0);

        if (tx > 1.0 || tx < -1.0){
            leftMaster.set(Math.signum(tx)*-.1);
            rightMaster.set(Math.signum(tx)*.1);
        } else {
            leftMaster.set(0.0);
            rightMaster.set(0.0);
        }

    }

    /*public void drive10Feet(){
        LeftMasterEncoder = leftMaster.getEncoder();

        LeftMasterEncoder.getPosition();





    }*/

    public void stop() {
        if (Constants.DRIVE_EXISTS) {
            leftMaster.set(0.0);
            rightMaster.set(0.0);
        }
    }
}
package org.royalrobotics.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.opencv.features2d.KAZE;
import org.royalrobotics.Constants;
import org.royalrobotics.Constants.CanId;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    private CANSparkMax shooterMotor;

    private SparkMaxPIDController shootercontroller;


    private NetworkTable table;

    private NetworkTableEntry targetOffset_V; //Changed from targetOffsetAngle_Vertical


    private static final double KP = 0.000;
    private static final double KI = 0.000;
    private static final double KD = 0.000;

    private Turret turret;




    
    // CANSparkMax motor controller for shooter pitching machine wheel
    // CANSparkMax motor controller for turret rotation
    // LimeLight camera to detect direction high goal

    public Shooter(Turret turretSubsystem) {
        super();
        if (Constants.SHOOTER_EXSISTS){
            table = NetworkTableInstance.getDefault().getTable("limelight");

            turret = turretSubsystem;

            shooterMotor = new CANSparkMax(CanId.shooter.id, MotorType.kBrushless);
            shooterMotor.restoreFactoryDefaults();
            shooterMotor.setInverted(CanId.shooter.reversed);
            shooterMotor.setIdleMode(IdleMode.kCoast);

            // shootercontroller = shooterMotor.getPIDController();
            // shootercontroller.setP(KP);
            // shootercontroller.setI(KI);
            // shootercontroller.setD(KD);
            // shootercontroller.setReference(0.0, CANSparkMax.ControlType.kVelocity);//sets it equal to 0
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

   
    // This sets shooter RPM to speed based on how far we are away from the goal.
    public void setShooterRPM(){
        double distanceFromGoal = turret.getDistanceFromTarget();

        double rpmSetpoint = ((distanceFromGoal -1) * 200)  + (3000);// quick function that adds 200rpm to shooter for every inch increased

        shootercontroller.setReference(rpmSetpoint, CANSparkMax.ControlType.kVelocity);
    }

}

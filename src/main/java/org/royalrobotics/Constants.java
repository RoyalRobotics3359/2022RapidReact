package org.royalrobotics;

import org.royalrobotics.commands.ExtendClimber;
import org.royalrobotics.commands.RetractClimber;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {
    
    //
    //[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ CAN ID'S ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]\\
    // 
    //  Enumeration of all the CAN devices on the CAN bus
    //
    public enum CanId {
        // motor controllers on 40 amp breaker
        frontLeft(3, false),
        frontRight(2, true),
        rearLeft(4, false),  
        rearRight(1, true),
        climber(6, true),     
        shooter(8, true),   
        // motor controllers on 20 amp breaker
        intakeArm(7, false),
        hopperMotor(9, false),
        turretMotor(10, false);

        public final int id;
        public final boolean reversed;

        private CanId(int newId, boolean rev) {
            id = newId;
            reversed = rev;
        }
    }

    public enum Speeds {
        shoot(1.0),
        hopperIn(.50),
        hopperOut(-.50),
        driving(0.70),
        intakeIn(0.70),
        intakeOut(-0.70),
        climber(0.50);      // FIXME: Set actual value

        public final double speed;

        private Speeds(double s) {
            speed = s;
        }
    }

    public enum Pnuematics {
        extendClimber(0),
        retractClimber(1),
        extendIntake(2),
        retractIntake(3),
        extendHopperStopper(4),
        retractHopperStopper(5);


        public final int channel;

        private Pnuematics(int ch) {
            channel = ch;
        }

    }


    public static final double DRIVE_MOTOR_RAMP_TIME = 1.5;
    public static final double CLIMBER_EXTEND_TIME = 0.5;
    public static final double CLIMBER_RETRACT_TIME = 3.0;


    public static final double SHOOTER_RPM_MINIMUM = 4800.0;

    public static final int LEFT_JOYSTICK_ID = 1;
    public static final int RIGHT_JOYSTICK_ID = 5;

    public static final int CONTROLLER_ID = 0;

    public static final double JOYSTICK_DEADBAND = 0.1;

    public static final double MAX_VOLTAGE = 10.0;

    // ROBOT PROFILE CONSTANTS\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    // The Robot Characterization Toolsuite provides a convenient tool for obtaining these
    // values for your robot.
    public static final double ksVolts = 0.17674;
    public static final double kvVoltSecondsPerMeter = 3.4213;
    public static final double kaVoltSecondsSquaredPerMeter = 0.4197;

    // P value for drive
    public static final double kPDriveVel = 4.3267;

    public static final double kTrackwidthMeters = 0.6096;
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static final double GEAR_RATIO = 10.71;

    public static final double WHEEL_RADIUS = 3.0;

    ///////////////////////////[[[[[[[[[[[[[LOGIC]]]]]]]]]]]]]\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static final boolean HOPPER_EXSISTS = true;
    public static final boolean SHOOTER_EXSISTS = true ; //true
    public static final boolean INTAKE_EXSISTS = true; //true
    public static final boolean CARGOPICKUP_EXISTS = false;
    public static final boolean CLIMBER_EXISTS = true;
    public static final boolean DRIVE_EXISTS = true; //true
    public static final boolean TURRET_EXISTS = false;

    //////////[ALLIANCE]\\\\\\\\\
    public static final String ALLIANCE = "red"; // set to red or blue








}

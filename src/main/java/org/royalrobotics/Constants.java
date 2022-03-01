package org.royalrobotics;

public class Constants {
    
    //
    //[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ CAN ID'S ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]\\
    // 
    //  Enumeration of all the CAN devices on the CAN bus
    //
    public enum CanId {
        frontLeft(3, false),
        frontRight(2, true),
        rearLeft(4, false),
        rearRight(1, true),
        shooter(5, false),
        hopper(6, false),
        intakeArm(7, false),
        hopperMotor(8, false),
        turretMotor(9, false);

        public final int id;
        public final boolean reversed;

        private CanId(int newId, boolean rev) {
            id = newId;
            reversed = rev;
        }
    }

    public enum Speeds {
        shoot(.95),
        hopperIn(.40),
        hopperOut(-.40),
        driving(0.70),
        intakeIn(0.70),
        intakeOut(-0.70);

        public final double speed;

        private Speeds(double s) {
            speed = s;
        }
    }

    public enum Pnuematics {
        extendClimber(0),
        retractClimber(1),
        extendIntake(2),
        retractIntake(3);

        public final int channel;

        private Pnuematics(int ch) {
            channel = ch;
        }

    }


    public static final double DRIVE_MOTOR_RAMP_TIME = 1.5;
    public static final double CLIMBER_EXTEND_TIME = 0.5;
    public static final double CLIMBER_RETRACT_TIME = 3.0;


    public static final double SHOOTER_RPM_MINIMUM = 4000.0;

    public static final int LEFT_JOYSTICK_ID = 1;
    public static final int RIGHT_JOYSTICK_ID = 5;

    public static final int CONTROLLER_ID = 0;

    public static final double JOYSTICK_DEADBAND = 0.05;

    ///////////////////////////[[[[[[[[[[[[[LOGIC]]]]]]]]]]]]]\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static final boolean HOPPER_EXSISTS = false;
    public static final boolean SHOOTER_EXSISTS = false;
    public static final boolean INTAKE_EXSISTS = true;
    public static final boolean CARGOPICKUP_EXISTS = false;
    public static final boolean CLIMBER_EXISTS = false;
    public static final boolean DRIVE_EXISTS = true;
    public static final boolean TURRET_EXISTS = false;








}

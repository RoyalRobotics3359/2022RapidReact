package org.royalrobotics;

public class Constants {
    
    //
    //[[[[[[[[[[[[[[[[[[[[[[[[[[[[[ CAN ID'S ]]]]]]]]]]]]]]]]]]]]]]]]]]]]]\\
    // 
    //  Enumeration of all the CAN devices on the CAN bus
    //
    public enum CanId {
        frontLeft(1, false),
        frontRight(2, true),
        rearLeft(3, false),
        rearRight(4, true),
        shooter(5, false),
        hopper(6, false);

        public final int id;
        public final boolean reversed;

        private CanId(int newId, boolean rev) {
            id = newId;
            reversed = rev;
        }
    }

    /**
     * Joystick
     * 
     * Enumeration of all joysticks used by the operator console
     */
    public enum JoystickButtons {
        shoot(0),
        deployPickup(4),
        retractPickup(5);

        public final int button;

        private JoystickButtons(int b) {
            button = b;
        }
    }

    public enum Speeds {
        shoot(.95),
        hopper(.40),
        driving(0.70);

        public final double speed;

        private Speeds(double s) {
            speed = s;
        }
    }

    public static final double DRIVE_MOTOR_RAMP_TIME = 1.5;

    public static final double SHOOTER_RPM_MINIMUM = 4000.0;

    public static final int LEFT_JOYSTICK_ID = 1;
    public static final int RIGHT_JOYSTICK_ID = 5;

    public static final int CONTROLLER_ID = 0;



}

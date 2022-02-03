package org.royalrobotics;

public class Constants {
    
    /**
     * CanId
     * 
     * Enumeration of all the CAN devices on the CAN bus
     */
    public enum CanId {
        frontLeft(1),
        frontRight(2),
        rearLeft(3),
        rearRight(4);

        public final int id;

        private CanId(int newId) {
            id = newId;
        }
    }

    /**
     * Joystick
     * 
     * Enumeration of all joysticks used by the operator console
     */
    public enum Joystick {
        left(0),
        right(1);

        public final int channel;

        private Joystick(int channelNum) {
            channel = channelNum;
        }
    }
}

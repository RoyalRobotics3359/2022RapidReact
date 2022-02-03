package org.royalrobotics.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    
    // Owns hardware related to shooting cargo in the goal
    
    // CANSparkMax motor controller for shooter pitching machine wheel
    // CANSparkMax motor controller for turret rotation
    // LimeLight camera to detect direction high goal

    public Shooter() {
        super();
    }
}

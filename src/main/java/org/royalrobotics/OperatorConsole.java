// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import org.royalrobotics.commands.Shoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Add your docs here. */
public class OperatorConsole {

    XboxController controller;

    public OperatorConsole() {
        controller = new XboxController(Constants.CONTROLLER_ID);


    }

    public double getLeft() {
        return controller.getRawAxis(1); // FIX
    }

    public Joystick getRight() {
        return right;// FIX
    }

}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/** Add your docs here. */
public class OperatorConsole {

    private XboxController controller;
    private JoystickButton shootButton;
    private JoystickButton extendClimber, retractClimber;
    private JoystickButton intakeIn, intakeOut;
    private JoystickButton turretAim;

    public OperatorConsole() {
        controller = new XboxController(Constants.CONTROLLER_ID);
        shootButton = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        extendClimber = new JoystickButton(controller, XboxController.Button.kX.value);
        retractClimber = new JoystickButton(controller, XboxController.Button.kY.value);
        intakeIn = new JoystickButton(controller, XboxController.Button.kRightBumper.value);
        intakeOut = new JoystickButton(controller, XboxController.Button.kLeftBumper.value);
        turretAim = new JoystickButton(controller, XboxController.Button.kA.value);
    }

    public JoystickButton getExtendClimberButton() {
        return extendClimber;
    }

    public JoystickButton getRetractClimber() {
        return retractClimber;
    }

    public JoystickButton getShootButton() {
        return shootButton;
    }

    public JoystickButton getIntakeInButton(){
        return intakeIn;
    }

    public JoystickButton getIntakeOutButton(){
        return intakeOut;
    }

    public JoystickButton getTurretAimButton(){
        return turretAim;
    }


    public double getLeftY() {
        return controller.getRawAxis(1) * -1.0; 
    }

    public double getLeftX() {
        return controller.getRawAxis(0);
    }

    public double getRightY() {
        return controller.getRawAxis(5) * -1.0; 
    }

    public double getRightX() {
        return controller.getRawAxis(4);
    }


}

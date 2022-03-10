// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.subsystems;

import com.revrobotics.ColorSensorV3;

import org.royalrobotics.Constants;

import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase {
  /** Creates a new ColorSensor. */

  // Creating new I2C port
  private final I2C.Port i2cPort;
  private final ColorSensorV3 m_colorSensor;
  private final ColorMatch m_colorMatcher;

  private final Color blue, red;

  private final String color;


  public ColorSensor() {

    // Setting up the I^2C port on roboRIO
    i2cPort = I2C.Port.kOnboard;

    // Setting up v3 Color Sensor
    m_colorSensor = new ColorSensorV3(i2cPort);
    m_colorMatcher = new ColorMatch();

    // Preloading colors for the color sensor to compare the cargo's color to
    blue = new Color(0,0,255);
    red = new Color(255,0,0);

    m_colorMatcher.addColorMatch(blue);
    m_colorMatcher.addColorMatch(red);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    Color detectedColor = m_colorSensor.getColor();

    /**
     * Run the color match algorithm on our detected color
     */
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    switch (color) {
      case "Blue":
        match.color = blue;
        break;
      case "Red":
        match.color = red;
        break;
    }


    // SmartDashboard give out (r,g,b) values of detected color
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);

    }

    public Color getBallColor() {
      
    }


  }
}

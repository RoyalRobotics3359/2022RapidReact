// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.DriveSubsystem;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/** Add your docs here. */
public class RobotContainer {

    private SimpleMotorFeedforward feedForward;
    private DriveSubsystem drive;
    private RamseteController ramseteController;

    private Trajectory trajectory;
    private static final String trajectoryJSON = "paths/SimpleCurve.wpilib";


    public RobotContainer(){
        ramseteController = new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta); //recommended values
        feedForward = new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter);
    }

  

    public Command getAutonomousCommand(){
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2));
        config.setKinematics(drive.getKinematics());

        /**
         * This is the way you can import the JSON file from the PathWeaver tool to create custom paths
         */
        // try{
        //     Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
        //     trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        //   } catch (IOException ex){
        //     DriverStation.reportError("Unable to open Trajectory: " + trajectoryJSON, ex.getStackTrace());
        //   }

        // Simpler way of making a path
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            Arrays.asList(new Pose2d(), new Pose2d(1.0, 0, new Rotation2d())) // moves 1 meter forward using config
        , config);

        // BOOM!  Bob's your unkle
        RamseteCommand command = new RamseteCommand(
            trajectory,
            drive::getPose,
            ramseteController,
            feedForward,
            drive.getKinematics(),
            drive::getWheelSpeeds,
            drive.getLeftPidController(),
            drive.getRightPIDCPidController(),
            drive::tankDriveVolts,
            drive
        );

        return command;
    }


}

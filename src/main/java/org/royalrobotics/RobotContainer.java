// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.royalrobotics.subsystems.Drive;
import org.royalrobotics.subsystems.DriveSubsystem;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
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

    private DriveSubsystem drive;

    //private static final String trajectoryJSON = "paths/SimpleCurve.wpilib";


    public RobotContainer(){
    }

  

    public static Command getAutonomousCommandFromFile(DriveSubsystem drive, String fileName){
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2)); 
        config.setKinematics(drive.getKinematics());

        Trajectory trajectory;

        /**
         * This is the way you can import the JSON file from the PathWeaver tool to create custom paths
         */
        try {
            Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("Paths/" + fileName);
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
            return createCommand(drive, trajectory);
        } catch (IOException ex){
            DriverStation.reportError("Unable to open Trajectory: " + fileName, ex.getStackTrace());
        }
        return null;
    }

    public static Command DriveStraightCommand(DriveSubsystem drive, double distanceInMeters){  
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2)); 
        config.setKinematics(drive.getKinematics());

        // Simpler way of making a path
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
            Arrays.asList(new Pose2d(), new Pose2d(distanceInMeters, 0, new Rotation2d())), config);

        return createCommand(drive, trajectory);
    }


    public static Command SCurveCommand(DriveSubsystem drive){  
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2)); 
        config.setKinematics(drive.getKinematics());

        // Simpler way of making a path
        Trajectory trajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(3, 0, new Rotation2d(0)),
            // Pass config
            config);

        return createCommand(drive, trajectory);
    }

    public static Command Turn90(DriveSubsystem drive){  
        TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2), Units.feetToMeters(2)); 
        config.setKinematics(drive.getKinematics());

        // Simpler way of making a path
        Trajectory trajectory =
        TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(0)),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(new Translation2d(0.1, 0)),
            new Pose2d(0.2, 0, new Rotation2d(Math.toRadians(90.0))),
            // Pass config
            config);

        return createCommand(drive, trajectory);
    }


    private static Command createCommand(DriveSubsystem drive, Trajectory trajectory)
    {

        RamseteController ramseteController = new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta); //recommended values
        SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter);

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

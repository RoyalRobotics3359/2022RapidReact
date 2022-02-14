// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.royalrobotics.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.royalrobotics.Constants;
import org.royalrobotics.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ScoreHighGoal extends SequentialCommandGroup {

  private boolean finished;

  /** Creates a new ScoreHighGoal. */
  public ScoreHighGoal(Shooter shooterSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new BringShooterUpToSpeed(shooterSubsystem), new MoveCargoIntoShooter(shooterSubsystem));
    finished = false;
  }

  @Override
  public void end(boolean interrupated) {
    if (interrupated) {
      finished = true;
    }
  }

  @Override
  public boolean isFinished() {
    return finished;
  }
}

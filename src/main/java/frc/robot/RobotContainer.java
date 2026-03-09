// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandPS4Controller;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.OperatorConstants;
// import frc.robot.subsystems.IntakeArmSubsystem;
// import frc.robot.subsystems.IntakeRollerSubsystem;
// import frc.robot.subsystems.ShooterRollerSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IndexRollerSubsystem;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.RPM;

import edu.wpi.first.math.MathUtil;


public class RobotContainer {

  // Subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();
  private final IndexRollerSubsystem m_indexRollerSubsystem = new IndexRollerSubsystem();

  //Dont use
  // private final IntakeArmSubsystem m_intakeArmSubsystem = new IntakeArmSubsystem();
  // private final IntakeRollerSubsystem m_intakeRollerSubsystem = new IntakeRollerSubsystem();
  // private final ShooterRollerSubsystem m_shooterRollerSubsystem = new ShooterRollerSubsystem();


  // Operator Controllers
  private final CommandPS4Controller m_OperatorController = new CommandPS4Controller(OperatorConstants.kOperatorControllerPort);
  private final CommandPS4Controller m_driverController = new CommandPS4Controller(OperatorConstants.kDriverControllerPort);


  public RobotContainer() {

    // Swerve
    m_robotDrive.setDefaultCommand(
        // The left stick controls translation of the robot.
        // Turning is controlled by the X axis of the right stick.
        new RunCommand(
            () -> m_robotDrive.drive(
                -MathUtil.applyDeadband(m_driverController.getRawAxis(1), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRawAxis(0), OIConstants.kDriveDeadband),
                -MathUtil.applyDeadband(m_driverController.getRawAxis(2), OIConstants.kDriveDeadband),
                true),
            m_robotDrive));


    DriverStation.silenceJoystickConnectionWarning(true); // Just gets rid of those annoying "joystick not connected" even though it is. Always double check though. Don't worry about it

    //Default Commands
    m_shooterSubsystem.setDefaultCommand(m_shooterSubsystem.set(0)); // If no other inputs are read, motors stop (or rather, motors use 0% of power)
    m_climbSubsystem.setDefaultCommand(m_climbSubsystem.set(0));
    m_indexRollerSubsystem.setDefaultCommand(m_indexRollerSubsystem.set(0));

    // m_intakeArmSubsystem.setDefaultCommand(m_intakeArmSubsystem.setAngle(Degrees.of(0)));
    // m_intakeRollerSubsystem.setDefaultCommand(m_intakeRollerSubsystem.set(0));
    configureBindings();




  }

  private void configureBindings() {

    // SHOOTER - Sets ONLY the shooter speed at various RPM's to score
    // m_OperatorController.L2().whileTrue(m_shooterSubsystem.setVelocity(RPM.of(600))); // CounterClockwise - Reverse
    // m_OperatorController.R2().whileTrue(m_shooterSubsystem.setVelocity(RPM.of(-300)));  // Clockwise - Shoots

    // !!!!!!!! TIP: Spin the Shooter Roller ahead of time so it can build speed, then use the Index Roller to send up shooter.

    // Intake Fuel - Sets the shooter (also the intake) Rollers to intake along with the Index Roller
    // Negative RPM for Shooter will SHOOT the balls out. A positive RPM will move the balls back down the shooter.
    m_OperatorController.L2()
                        .whileTrue(m_shooterSubsystem.setVelocity(RPM.of(-3500)));
                                                      // .alongWith(m_indexRollerSubsystem.set(-0.9)));

      // Cycle Fuel to Shooter
      m_OperatorController.R2()
                        .whileTrue(m_shooterSubsystem.setVelocity(RPM.of(-100)));
                                                      // .alongWith(m_indexRollerSubsystem.set(0.9)));


    // Test whether the positive or negative dutycycle rotates the index CW or CCW
    m_OperatorController.button(3).whileTrue(m_indexRollerSubsystem.set(0.8)); // Positive Intake
    m_OperatorController.button(4).whileTrue(m_indexRollerSubsystem.set(-0.8));         // Negative reverse

                                                      
    // When using the climb, if for any reason the robot isn't strong enough, check IRL mechanism. Or increase dutycycle but not over 1
    // Climber - Climb Up/ Climb Down -> DRIVER CONTROLLER
    m_driverController.button(3).whileTrue(m_climbSubsystem.set(0.8));
    m_driverController.button(4).whileTrue(m_climbSubsystem.set(-0.8));

    // ----------------------------------------------------------------------



    // INTAKE ARM - Sets the intake arm at various angles
    // m_OperatorController.button(3).whileTrue(m_intakeArmSubsystem.setAngle(Degrees.of(-15)));
    // m_OperatorController.button(4).whileTrue(m_intakeArmSubsystem.setAngle(Degrees.of(50)));    // BE CAREFUL WHEN TESTING THIS, USE LOW PID and FF values!!!!!
    // m_OperatorController.button(5).whileTrue(m_intakeArmSubsystem.set(0.3));

    // Intake balls
    // m_OperatorController.button(5).whileTrue(m_intakeRollerSubsystem.set(0.4));
    // m_OperatorController.button(6).whileTrue(m_intakeRollerSubsystem.set(-0.4));

    // // // Spin Intake Rollers (not intake) and shoot
    // m_OperatorController.button(7)
    //   .whileTrue(m_intakeRollerSubsystem.set(0.4)
    //                                     .alongWith(m_shooterSubsystem.setVelocity(RPM.of(300))));

    // Climber - Climb Up/ Climb Down -> Driver Controller
    // m_driverController.button(1).whileTrue(m_climbSubsystem.set(0.8));
    // m_driverController.button(2).whileTrue(m_climbSubsystem.set(-0.8));



    m_driverController.button(6)
            .whileTrue(
              new RunCommand(()-> m_robotDrive.setX(), m_robotDrive));

    m_driverController.button(7)
            .onTrue(
              new InstantCommand(()-> m_robotDrive.zeroHeading(), m_robotDrive));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

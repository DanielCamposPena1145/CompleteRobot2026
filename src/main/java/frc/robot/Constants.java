// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.measure.AngularAcceleration;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;

import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.DegreesPerSecond;
import static edu.wpi.first.units.Units.DegreesPerSecondPerSecond;
import static edu.wpi.first.units.Units.RPM;
import static edu.wpi.first.units.Units.RotationsPerSecondPerSecond;

/** Add your docs here. */
public final class Constants {
    public static class OperatorConstants {
        public static final int kOperatorControllerPort = 0;
        public static final int kDriverControllerPort = 1;
    }

    public static class TurretSubsystemConstants {
        // Keep in mind this is for the MAIN FLYWHEEL
        public static final DCMotor dcMotor = DCMotor.getNEO(1); // Don't worry about this
        public static final double gearRatio = 12;
        public static final int canID = 1; 
        public static final double kP = 1; // The actual value for P may be lower than 1
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kS = 0;
        public static final double kV = 0;
        public static final double kA = 0;
        // public static final double kG = 0; // Unused for turrets
        public static final AngularVelocity maxVelocityRPM = RPM.of(5000); // change these values cuz they're kinda crazy. Start from low values and work your way up.
        public static final AngularAcceleration maxAccelerationRPM = RotationsPerSecondPerSecond.of(2500);
        public static final Current statorCurrentLimit = Amps.of(40);
    }


    public static class IntakeArmConstants {
        public static final double kP = 1; 
        public static final double kI = 0;
        public static final double kD = 0;
        public static final double kS = 0;
        public static final double kV = 0;
        public static final double kG = 0; // Gravity Constant
        public static final AngularVelocity maxVelocityDegPerSec = DegreesPerSecond.of(90);
        public static final AngularAcceleration maxAccelerationDegPerSecPerSec = DegreesPerSecondPerSecond.of(45);
        public static final Current statorCurrentLimit = Amps.of(40);


    }
}

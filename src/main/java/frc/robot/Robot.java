package frc.robot;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;


public class Robot extends TimedRobot {
    private final MotorController leftMotor = new PWMSparkMax(0);
    private final MotorController rightMotor = new PWMSparkMax(1);
    private final ShuffleboardTab tab = Shuffleboard.getTab("Main");
    private final GenericEntry targetSpeedEntry = tab.add("Target speed", 0.0).getEntry();
    private double targetSpeed = 0.0;

    @Override
    public void robotInit() {
        CommandJoystick leftStick = new CommandJoystick(0);
        rightMotor.setInverted(true);

        leftStick.povUp().onTrue(new InstantCommand(this::increaseSpeed));
        leftStick.povDown().onTrue(new InstantCommand(this::decreaseSpeed));
    }

    @Override
    public void teleopPeriodic() {
        leftMotor.set(targetSpeed);
        rightMotor.set(targetSpeed);

        targetSpeedEntry.setDouble(targetSpeed);
        CommandScheduler.getInstance().run();
    }

    public void increaseSpeed() {
        targetSpeed += 0.1;
    }

    public void decreaseSpeed() {
        targetSpeed -= 0.1;
    }
}

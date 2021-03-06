/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;

/**
 * This OpMode uses the common HardwareK9bot class to define the devices on the robot.
 * All device access is managed through the HardwareK9bot class. (See this class for device names)
 * The code is structured as a LinearOpMode
 * N
 * This particular OpMode executes a basic Tank Drive Teleop for the K9 bot
 * It raises and lowers the arm using the Gampad Y and A buttons respectively.
 * It also opens and closes the claw slowly using the X and B buttons.
 *
 * Note: the configuration of the servos is such that
 * as the arm servo approaches 0, the arm position moves up (away from the floor).
 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Icebreakers: AutoV6BeaconsRed", group="Autonomous")
//@Disabled
public class AutoV6BeaconsRed extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware robot = new Hardware();
    int servoUp = 1;
    int servoDown = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        robot.shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            robot.beaconServo.setPosition(servoUp);
            //drive forward a little bit
            driveForwards(500);
           //turn left to face twoards far beacon
            turnLeft(1000);
            //drive until it detects the stripe then stop
            driveBackwards(1000);
            //check light sensor values here if doesnt detect keep going until detection
            //stop once detects white line
            //turn left then check for stripe again
            turnLeft(500);
            //check for stripe
            //if if doesnt find line move until it detects
            robot.beaconServo.setPosition(servoDown);
            //drive and slam into beacon
            driveBackwards(1000);
            //check beaconcolor, if it isnt red, slam into it again
            robot.beaconServo.setPosition(servoUp);
            robot.readColorSensor();
            while (robot.beaconColor == robot.blue)
            {
                mashButton();
            }
            driveForwards(200);
            turnLeft(500);
            driveBackwards(700);
            turnLeft(300);
            //detect stripe/ line up again
            robot.readColorSensor();
            while (robot.beaconColor == robot.blue)
            {
                mashButton();
            }
            driveForwards(1100);

            // Send telemetry message to signify robot running
            //telemetry.addData("right", "%.2f", right);
            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(40);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }

    public void stopWheels() {
        robot.leftBackMotor.setPower(0);
        robot.leftFrontMotor.setPower(0);
        robot.rightBackMotor.setPower(0);
        robot.rightFrontMotor.setPower(0);
    }
    public void driveForwards(long durationMillis) {
        robot.leftBackMotor.setPower(1);
        robot.leftFrontMotor.setPower(1);
        robot.rightBackMotor.setPower(1);
        robot.rightFrontMotor.setPower(1);
        rest(durationMillis);
        stopWheels();
    }
    public void driveSlow(long durationMillis) {
        robot.leftBackMotor.setPower(0.3);
        robot.leftFrontMotor.setPower(0.3);
        robot.rightBackMotor.setPower(0.3);
        robot.rightFrontMotor.setPower(0.3);
        rest(durationMillis);
        stopWheels();
    }
    public void driveBackwards(long durationMillis) {
        robot.leftBackMotor.setPower(-1);
        robot.leftFrontMotor.setPower(-1);
        robot.rightBackMotor.setPower(-1);
        robot.rightFrontMotor.setPower(-1);
        rest(durationMillis);
        stopWheels();
    }
    public void turnRight(long durationMillis) {
        robot.leftBackMotor.setPower(1);
        robot.leftFrontMotor.setPower(1);
        robot.rightBackMotor.setPower(-1);
        robot.rightFrontMotor.setPower(-1);
        rest(durationMillis);
        stopWheels();
    }
    public void turnLeft(long durationMillis) {
        robot.leftBackMotor.setPower(-1);
        robot.leftFrontMotor.setPower(-1);
        robot.rightBackMotor.setPower(1);
        robot.rightFrontMotor.setPower(1);
        rest(durationMillis);
    }

    public void mashButton() {
        robot.beaconServo.setPosition(servoUp);
        driveForwards(500);
        robot.beaconServo.setPosition(servoDown);
        driveBackwards(1000);
        rest(5000);
        robot.readColorSensor();
    }

    public void startShooter(long durationMillis, long sleepIncr) {
        robot.shooter.setPower(0.2);
        rest(sleepIncr);
        robot.shooter.setPower(0.4);
        rest(sleepIncr);
        robot.shooter.setPower(0.6);
        rest(sleepIncr);
        robot.shooter.setPower(0.8);
        rest(sleepIncr);
        robot.shooter.setPower(1);
        rest(durationMillis);
    }
    public void stopShooter() {
        robot.shooter.setPower(1);
        rest(200);
        robot.shooter.setPower(0.8);
        rest(200);
        robot.shooter.setPower(0.6);
        rest(200);
        robot.shooter.setPower(0.4);
        rest(200);
        robot.shooter.setPower(0.2);
        rest(200);
        robot.shooter.setPower(0);
    }
    public void rest(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {

        }
    }
}
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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robocol.RobocolDatagramSocket;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file provides basic Telop driving for a Pushbot robot.
 * The code is structured as an Iterative OpMode
 *
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 *
 * This particular OpMode executes a basic Tank Drive Teleop for a PushBot
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="IceBreakers: AutoV2", group="Autonomous")
public class AutoV2 extends OpMode {

    /* Declare OpMode members. */
    Hardware robot = new Hardware(); // use the class created to define a Pushbot's hardware
    private ElapsedTime runTimer = new ElapsedTime();
    private static final double forwardTime = 1;
    private static final double forwardTimeTwo = 3;

    private enum AutoState {
        InitialState,
        DriveStraightStart,
        DriveStraightWait,
        Done,
        DoneOne,
        Shoot,
        DriveStraightBall,
        DriveStraightBallWait,
        FinalState,
    }

    ;
    private AutoState AutoCurrentState = AutoState.InitialState;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("OK", "Hello Driver");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        switch (AutoCurrentState) {
            case InitialState:
                AutoCurrentState = AutoState.DriveStraightStart;
                break;
            case DriveStraightStart:
                robot.leftFrontMotor.setPower(1.0);
                robot.rightFrontMotor.setPower(1.0);
                robot.leftBackMotor.setPower(1.0);
                robot.rightBackMotor.setPower(1.0);
                runTimer.reset();
                break;
            case DriveStraightWait:
                if (runTimer.time() > forwardTime) {
                    AutoCurrentState = AutoState.Done;
                }
                break;
            case Done:
                robot.shooter.setPower(0);
                robot.triggerServo.setPosition(1);
                robot.leftFrontMotor.setPower(0.0);
                robot.rightFrontMotor.setPower(0.0);
                robot.leftBackMotor.setPower(0.0);
                robot.rightBackMotor.setPower(0.0);
            case Shoot:
                robot.shooter.setPower(0.1);
                sleep(150);
                robot.shooter.setPower(0.3);
                sleep(150);
                robot.shooter.setPower(0.5);
                sleep(150);
                robot.shooter.setPower(0.7);
                sleep(150);
                robot.shooter.setPower(0.9);
                sleep(150);
                robot.shooter.setPower(1.0);
                sleep(100);
                robot.triggerServo.setPosition(0.25);
                sleep(1000);
                robot.triggerServo.setPosition(1);
                sleep(1000);
                robot.triggerServo.setPosition(0.25);
                sleep(1000);
                robot.triggerServo.setPosition(1);
                robot.shooter.setPower(0.9);
                sleep(150);
                robot.shooter.setPower(0.7);
                sleep(150);
                robot.shooter.setPower(0.5);
                sleep(150);
                robot.shooter.setPower(0.3);
                sleep(150);
                robot.shooter.setPower(0.1);
                break;
            case DoneOne:
                robot.shooter.setPower(0);
                robot.triggerServo.setPosition(1);
                robot.leftFrontMotor.setPower(0.0);
                robot.rightFrontMotor.setPower(0.0);
                robot.leftBackMotor.setPower(0.0);
                robot.rightBackMotor.setPower(0.0);
                break;
            case DriveStraightBall:
                robot.leftFrontMotor.setPower(1.0);
                robot.rightFrontMotor.setPower(1.0);
                robot.leftBackMotor.setPower(1.0);
                robot.rightBackMotor.setPower(1.0);
                runTimer.reset();
                break;
            case DriveStraightBallWait:
                if (runTimer.time() > forwardTimeTwo) {
                    AutoCurrentState = AutoState.Done;
                }
                break;
            case FinalState:
                break;
        }
    }
        // Run wheels importn tank mode (note: The joystick goes negative when pushed forwards, so negate it)

    /*
     * Code to run ONCE after the driver hits STOP
     */
        @Override
        public void stop() {
        }


    private void sleep(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {

        }
    }
}

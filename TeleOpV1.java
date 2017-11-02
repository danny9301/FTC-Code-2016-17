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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

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

@TeleOp(name="IceBreakers: Tank", group="IceBreakers")
//@Disable
public class TeleOpV1 extends OpMode {

    /* Declare OpMode members. */
    Hardware robot = new Hardware(); // use the class created to define hardware

    public double left;
    public double right;
    public double shooterleft;
    public Float spin = 0f;
    int colorVal = 0;

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

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        left = 0.9 * -gamepad1.left_stick_y;
        right = 0.9 * -gamepad1.right_stick_y;

        boolean buttonA = gamepad2.a;
        boolean buttonY = gamepad2.y;
        boolean buttonB = gamepad2.b;
        boolean reverseButton = gamepad2.right_bumper;
        //boolean capMotorbutton = gamepad2.left_bumper;

        robot.leftFrontMotor.setPower(left);
        robot.rightFrontMotor.setPower(right);
        robot.leftBackMotor.setPower(left);
        robot.rightBackMotor.setPower(right);


        //robot.shooter.setPower(shooterleft);

        if(gamepad2.x){
            if (spin < 1f){
                spin = spin + 0.009f;
            }
            robot.shooter.setPower(spin);
        }else{
            spin = 0f;
            robot.shooter.setPower(spin);
            robot.shooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
        if(buttonA) {
            robot.intake.setPower(1);
        } else if(reverseButton) {
            robot.intake.setPower(-1);
        } else {
            robot.intake.setPower(0);
        }

        //This if/else sets the intake power to 50 if a is pressed, when its not pressed the power goes to 0
//        if (buttonA) {
//            robot.intake.setPower(75);
//        } else {
//            robot.intake.setPower(0);
//        }

        if (buttonY) {
            robot.triggerServo.setPosition(0.25);

        }else {
            robot.triggerServo.setPosition(1);
        }
        // Use gamepad left & right Bumpers to open and close the claw

        if (buttonB) {
            robot.beaconServo.setPosition(0.1);
        }   else {
            robot.beaconServo.setPosition(1);
        }

        // Send telemetry message to signify robot running;
        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);
        telemetry.addData("blue = ", robot.color.blue());
        telemetry.addData("red = ", robot.color.red());
        telemetry.addData("green = ", robot.color.green());
        updateTelemetry(telemetry);
    }

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

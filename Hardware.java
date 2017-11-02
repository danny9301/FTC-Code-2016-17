package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.ftccommon.Device;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRColor;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 *
 */
public class Hardware
{
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor intake = null;
    public DcMotor shooter = null;

    public Servo    triggerServo    = null;
    public Servo    beaconServo     = null;

    ColorSensor color = null;

    boolean colorisvalid = false;
    boolean lightisvalid = false;

    float hsv[] = {0,0,0};

    public LightSensor light = null;

    public int beaconColor = 0;
    int blue = 1;
    int red = 2;
    int none = 3;

//    public SensorMRColor beaconSensor = null;

    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public Hardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftFrontMotor = hwMap.dcMotor.get("left_front");
        rightFrontMotor = hwMap.dcMotor.get("right_front");
        leftBackMotor = hwMap.dcMotor.get("left_back");
        rightBackMotor = hwMap.dcMotor.get("right_back");
        intake = hwMap.dcMotor.get("intake");
        shooter = hwMap.dcMotor.get("shooter");

        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        leftBackMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        intake.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftFrontMotor.setPower(0);
        rightFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightBackMotor.setPower(0);
        intake.setPower(0);
        shooter.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        triggerServo = hwMap.servo.get("triggerServo");
        triggerServo.setPosition(1);
        beaconServo = hwMap.servo.get("beaconServo");
        beaconServo.setPosition(1);

        color = hwMap.colorSensor.get("color");
        color.enableLed(false);
    }

    public void readColorSensor() {
        Color.RGBToHSV(color.red() * 8, color.green()  * 8, color.blue() * 8,hsv);

        colorisvalid = hsv[1] > 0.5;

        if(colorisvalid = true) {
            beaconColor = hsv[0] > 100 ? blue: red;
        } else {
            beaconColor = none;
        }
    }



    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}


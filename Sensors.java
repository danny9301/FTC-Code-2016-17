//package org.firstinspires.ftc.teamcode;
//
//import android.graphics.Color;
//
//import com.qualcomm.robotcore.hardware.ColorSensor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.LightSensor;
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//
//
///**
// * Created by CMS_Robotics on 1/24/17.
// */
//
//public class Sensors {
//    ColorSensor color = null;
//    HardwareMap ahwmap;
//    boolean colorisvalid = false;
//    boolean lightisvalid = false;
//    float hsv[] = {0,0,0};
//
//    LightSensor light = null;
//
//
//    public int beaconColor = 0;
//    int blue = 1;
//    int red = 2;
//    int none = 3;
//
//    void init (HardwareMap hwMap) {
//        ahwmap = hwMap;
//
//        color = ahwmap.colorSensor.get("color");
//        color.enableLed(false);
//    }
//
//    void readColorSensor() {
//        Color.RGBToHSV(color.red() * 8, color.green()  * 8, color.blue() * 8,hsv);
//
//        colorisvalid = hsv[1] > 0.5;
//
//        if(colorisvalid = true) {
//            beaconColor = hsv[0] > 100 ? blue: red;
//        } else {
//            beaconColor = none;
//        }
////        beaconColor = hsv[0] > 100 ? "blue": "red";
//    }
//
//    public void readLightSensor() {
//        light.getLightDetected();
//    }
//}

package com.mariogrip.octodroid;

/**
 * Created by mariogrip on 02.12.14.
 */
public class util_send extends util {
    public static void goX(String value){
        String sendvalue = "{\n" +
                "  \"command\": \"jog\",\n" +
                "  \"x\": "+value+"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goZ(String value){
        String sendvalue = "{\n" +
                "  \"command\": \"jog\",\n" +
                "  \"z\": "+value+"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goY(String value){
        String sendvalue = "{\n" +
                "  \"command\": \"jog\",\n" +
                "  \"y\": "+value+"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goHome(){
        String sendvalue = "{\n" +
                "  \"command\": \"home\",\n" +
                "  \"axes\": [\"x\", \"y\", \"z\"]\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goHomeY(){
        String sendvalue = "{\n" +
                "  \"command\": \"home\",\n" +
                "  \"axes\": [\"y\"]\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goHomeZ(){
        String sendvalue = "{\n" +
                "  \"command\": \"home\",\n" +
                "  \"axes\": [\"z\"]\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static void goHomeX(){
        String sendvalue = "{\n" +
                "  \"command\": \"home\",\n" +
                "  \"axes\": [\"x\"]\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/printhead", sendvalue);
    }
    public static boolean stopprint(){
        String sendvalue = "{\n" +
                "  \"command\": \"cancel\"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "job", sendvalue);
        return true;
    }
    public static boolean startprint(){
        String sendvalue = "{\n" +
                "  \"command\": \"start\"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "job", sendvalue);
        return true;
    }
    public static boolean pauseprint(){
        String sendvalue = "{\n" +
                "  \"command\": \"pause\"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "job", sendvalue);
        return true;
    }
    public static void setBedTemp(String temp){
        String sendvalue = "{\n" +
                "  \"command\": \"temp\",\n" +
                "  \"temps\": {\n" +
                "    \"bed\": " + temp +"\n" +
                "  }\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/heater", sendvalue);
    }
    public static void setExtTemp(String temp){
        String sendvalue = "{\n" +
                "  \"command\": \"temp\",\n" +
                "  \"temps\": {\n" +
                "    \"hotend\": " + temp +"\n" +
                "  }\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/heater", sendvalue);
    }
    public static void Extrude(String value){
        String sendvalue = "{\n" +
                "  \"command\": \"extrude\",\n" +
                "  \"amount\": "+value+"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "printer/feeder", sendvalue);
    }
    public static void Disconnect(){
        String sendvalue = "{\n" +
                "  \"command\": \"disconnect\"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "connection", sendvalue);
    }
    public static void Connect(String save, String AutoCon, String baudrate, String port){
        String sendvalue = "{\n" +
                "  \"command\": \"connect\",\n" +
                "  \"port\": \""+port+"\",\n" +
                "  \"baudrate\": "+baudrate+",\n" +
                "  \"save\": "+save+",\n" +
                "  \"autoconnect\": "+AutoCon+"\n" +
                "}";
        sendcmd(Activity.ip, Activity.key, "connection", sendvalue);
    }
}

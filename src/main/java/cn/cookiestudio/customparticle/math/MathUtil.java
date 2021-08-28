package cn.cookiestudio.customparticle.math;

import cn.nukkit.Player;
import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;

public class MathUtil {

    public static double sin(double angle){
        return Math.sin(Math.PI * (angle / 180));
    }

    public static double cos(double angle){
        return Math.cos(Math.PI * (angle / 180));
    }

    public static double tan(double angle){
        return Math.tan(Math.PI * (angle / 180));
    }

    public static double asin(double sin){
        return 180 * Math.asin(sin) / Math.PI;
    }

    public static double acos(double cos){
        return 180 * Math.asin(cos) / Math.PI;
    }

    public static double atan(double tan){
        return 180 * Math.asin(tan) / Math.PI;
    }

    public static double minAbs(double a,double b){
        return (Math.abs(a) < Math.abs(b)) ? a : b;
    }

    public static double maxAbs(double a,double b){
        return (Math.abs(a) > Math.abs(b)) ? a : b;
    }

    public static BVector3 getFaceDirection(Location location,double length){
        return new BVector3(location.getYaw() - 270,-location.getPitch(),length);
    }
}

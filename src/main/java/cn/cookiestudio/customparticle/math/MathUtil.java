package cn.cookiestudio.customparticle.math;

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

    public static BVector3 newVector3(double xzAxisAngle, double yAxisAngle, double length){
        return new BVector3(xzAxisAngle,yAxisAngle,length);
    }

    public static BVector3 newVector3(Vector3 pos){
        return new BVector3(pos);
    }

    public static BVector3 newVector3(BVector3 vec){
        return new BVector3(vec);
    }

    public static BVector3 getFaceDirection(Location location,double length){
        return newVector3(location.getYaw() - 270,-location.getPitch(),length);
    }
}

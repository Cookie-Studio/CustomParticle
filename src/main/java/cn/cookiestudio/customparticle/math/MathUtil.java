package cn.cookiestudio.customparticle.math;

import cn.nukkit.level.Location;
import cn.nukkit.math.Vector3;
import lombok.Getter;

public class MathUtil {

    @Getter
    private static MathUtil instance;

    static{
        instance = new MathUtil();
    }

    public double sin(double angle){
        return Math.sin(Math.PI * (angle / 180));
    }

    public double cos(double angle){
        return Math.cos(Math.PI * (angle / 180));
    }

    public double tan(double angle){
        return Math.tan(Math.PI * (angle / 180));
    }

    public double asin(double sin){
        return 180 * Math.asin(sin) / Math.PI;
    }

    public double acos(double cos){
        return 180 * Math.asin(cos) / Math.PI;
    }

    public double atan(double tan){
        return 180 * Math.asin(tan) / Math.PI;
    }

    public double minAbs(double a,double b){
        return (Math.abs(a) < Math.abs(b)) ? a : b;
    }

    public double maxAbs(double a,double b){
        return (Math.abs(a) > Math.abs(b)) ? a : b;
    }

    public BVector3 newVector3(double xzAxisAngle, double yAxisAngle, double length){
        return new BVector3(xzAxisAngle,yAxisAngle,length);
    }

    public BVector3 newVector3(Vector3 pos){
        return new BVector3(pos);
    }

    public BVector3 newVector3(BVector3 vec){
        return new BVector3(vec);
    }

    public BVector3 getFaceDirection(Location location,double length){
        return newVector3(location.getYaw() - 270,-location.getPitch(),length);
    }
}

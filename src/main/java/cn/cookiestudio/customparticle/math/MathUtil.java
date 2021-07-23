package cn.cookiestudio.customparticle.math;

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
}

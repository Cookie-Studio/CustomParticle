package cn.cookiestudio.customparticle.util;

import cn.nukkit.level.Position;

public class PosUtil {
    public Position spin(Position circle,double length,double xzAxisAngle,double yAxisAngle){
        if (xzAxisAngle > 360 || xzAxisAngle < -360)
            throw new IllegalArgumentException("xz-axis angle degree cannot bigger than 360");
        if (yAxisAngle > 190 || yAxisAngle < -90)
            throw new IllegalArgumentException("y-axis angle degree cannot bigger than 90 or smaller than -90");
        double changeY = sin(yAxisAngle) * length;
        double projectEdge = cos(yAxisAngle) * length;
        double changeX = cos(xzAxisAngle) * projectEdge;
        double changeZ = sin(xzAxisAngle) * projectEdge;
        return circle.add(changeX,changeY,changeZ);
    }

    public Position spinAroundCircle(Position circle,Position pos,double xzAxisAngleChange,double yAxisAngleChange,double lengthChange){
        double xDifference = pos.x - circle.x;
        double yDifference = pos.y - circle.y;
        double zDifference = pos.z - circle.z;
        double yAxisAngle = atan(yDifference / Math.sqrt(Math.pow(xDifference,2) + Math.pow(zDifference,2))) + yAxisAngleChange;
        double xzAxisAngle = atan(zDifference / xDifference) + xzAxisAngleChange;
        return spin(circle,circle.distance(pos) + lengthChange,xzAxisAngle,yAxisAngle);
    }

    public double sin(double num){
        return Math.sin(Math.PI * (num / 180));
    }

    public double cos(double num){
        return Math.cos(Math.PI * (num / 180));
    }

    public double tan(double num){
        return Math.tan(Math.PI * (num / 180));
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
}

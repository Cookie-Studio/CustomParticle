package cn.cookiestudio.customparticle.util;

import cn.nukkit.level.Position;
import lombok.Getter;
import lombok.Setter;

public class PosUtil {
    public Position spin(Position circle,double length,double xzAxisAngle,double yAxisAngle){
        if (yAxisAngle > 190 || yAxisAngle < -90)
            throw new IllegalArgumentException("y-axis angle degree cannot bigger than 90 or smaller than -90");
        double changeY = sin(yAxisAngle) * length;
        double projectEdge = cos(yAxisAngle) * length;
        double changeX = cos(xzAxisAngle) * projectEdge;
        double changeZ = sin(xzAxisAngle) * projectEdge;
        return circle.add(changeX,changeY,changeZ);
    }

    //the base angle added up change angle (only apply in y-axis) CANNOT over limit,or it will cause problems
    public Position spinAroundCircle(Position circle,Position pos,double xzAxisAngleChange,double yAxisAngleChange,double lengthChange){
        double xDifference = pos.x - circle.x;
        double yDifference = pos.y - circle.y;
        double zDifference = pos.z - circle.z;
        double yAxisAngle = atan(yDifference / Math.sqrt(Math.pow(xDifference,2) + Math.pow(zDifference,2))) + yAxisAngleChange;
        double xzAxisAngle = atan(zDifference / xDifference) + xzAxisAngleChange;
        return spin(circle,circle.distance(pos) + lengthChange,xzAxisAngle,yAxisAngle);
    }

    public Position extendAlongLine(Line line,double length){
        double scale = (length + line.getPos1().distance(line.getPos2())) / line.getPos1().distance(line.getPos2());
        double minAbsX = minAbs(line.getPos1().x,line.getPos2().x);
        double minAbsY = minAbs(line.getPos1().y,line.getPos2().y);
        double minAbsZ = minAbs(line.getPos1().z,line.getPos2().z);
        double maxAbsX = maxAbs(line.getPos1().x,line.getPos2().x);
        double maxAbsY = maxAbs(line.getPos1().y,line.getPos2().y);
        double maxAbsZ = maxAbs(line.getPos1().z,line.getPos2().z);
        return new Position(minAbsX + (maxAbsX - minAbsX) * scale,
                minAbsY + (maxAbsY - minAbsY) * scale,
                minAbsZ + (maxAbsZ - minAbsZ) * scale);
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

    public double minAbs(double a,double b){
        return (Math.abs(a) < Math.abs(b)) ? a : b;
    }

    public double maxAbs(double a,double b){
        return (Math.abs(a) > Math.abs(b)) ? a : b;
    }
    
    public Line newLine(Position pos1,Position pos2){
        return new Line(pos1,pos2);
    }
    
    @Getter
    @Setter
    private static class Line{
        
        private Position pos1;
        private Position pos2;
        
        private Line(Position pos1,Position pos2){}
    }
}

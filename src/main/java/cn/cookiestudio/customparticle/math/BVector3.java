package cn.cookiestudio.customparticle.math;

import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import lombok.Getter;

@Getter
public class BVector3 {

    private double xzAxisAngle;
    private double yAxisAngle;//-90 -- 90
    private Vector3 pos;
    private double length;

    public BVector3(double xzAxisAngle, double yAxisAngle, double length){
        if (yAxisAngle > 90 || yAxisAngle < -90)
            throw new IllegalArgumentException("y-axis angle degree cannot bigger than 90 or smaller than -90");
        this.xzAxisAngle = xzAxisAngle;
        this.yAxisAngle = yAxisAngle;
        this.length = length;
        updatePos();
    }

    public BVector3(Vector3 pos){
        this.pos = pos;
        updateAngle();
    }

    public BVector3 extend(double length){
        this.length = length;
        updatePos();
        return this;
    }

    public BVector3 changeAngle(double xzAxisAngle, double yAxisAngle){
        this.xzAxisAngle = xzAxisAngle;
        this.yAxisAngle = yAxisAngle;
        updatePos();
        return this;
    }

    public BVector3 changePos(Vector3 pos){
        this.pos = pos;
        updateAngle();
        return this;
    }

    public Position addToPosition(Position pos){
        return pos.add(this.pos.x,this.pos.y,this.pos.z);
    }

    private void updatePos(){
        double y = MathUtil.getInstance().sin(yAxisAngle) * length;
        double projectEdge = MathUtil.getInstance().cos(yAxisAngle) * length;
        double x = MathUtil.getInstance().cos(xzAxisAngle) * projectEdge;
        double z = MathUtil.getInstance().sin(xzAxisAngle) * projectEdge;
    }

    private void updateAngle(){
        this.xzAxisAngle = MathUtil.getInstance().atan(pos.z / pos.x);
        double projectEdge = Math.sqrt(Math.pow(pos.x,2) + Math.pow(pos.z,2));
        this.yAxisAngle = MathUtil.getInstance().atan(pos.y / projectEdge);
        this.length = Math.sqrt(Math.pow(projectEdge,2) + Math.pow(pos.y,2));
    }
}

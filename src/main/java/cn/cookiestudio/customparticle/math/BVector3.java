package cn.cookiestudio.customparticle.math;

import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import lombok.Getter;

@Getter
public class BVector3{

    private double xzAxisAngle;
    private double yAxisAngle;//-90 -- 90
    private Vector3 pos;
    private double length;

    public BVector3(double xzAxisAngle, double yAxisAngle, double length){
        convertAngle(xzAxisAngle,yAxisAngle);
        this.length = length;
        updatePos();
    }

    public BVector3(Vector3 pos){
        this.pos = pos;
        updateAngle();
    }

    public BVector3(BVector3 vec){
        this.xzAxisAngle = vec.getXzAxisAngle();
        this.yAxisAngle = vec.getYAxisAngle();
        this.pos = vec.getPos();
        this.length = vec.getLength();
    }

    public BVector3 extend(double length){
        this.length += length;
        updatePos();
        return this;
    }

    public BVector3 setAngle(double xzAxisAngle, double yAxisAngle){
        convertAngle(xzAxisAngle,yAxisAngle);
        updatePos();
        return this;
    }

    public BVector3 addAngle(double xzAxisAngle,double yAxisAngle){
        convertAngle(this.xzAxisAngle + xzAxisAngle,this.yAxisAngle + yAxisAngle);
        updatePos();
        return this;
    }

    public BVector3 setPos(double x, double y, double z){
        this.pos = this.pos.setComponents(x,y,z);
        updateAngle();
        return this;
    }

    public BVector3 addPos(double x, double y, double z){
        this.pos = this.pos.add(x,y,z);
        updateAngle();
        return this;
    }

    public Position addToPosition(Position pos){
        return pos.add(this.pos.x,this.pos.y,this.pos.z);
    }

    private void updatePos(){
        double y = MathUtil.sin(yAxisAngle) * length;
        double projectEdge = MathUtil.cos(yAxisAngle) * length;
        double x = MathUtil.cos(xzAxisAngle) * projectEdge;
        double z = MathUtil.sin(xzAxisAngle) * projectEdge;
        this.pos = new Vector3(x,y,z);
    }

    private void updateAngle(){
        this.xzAxisAngle = MathUtil.atan(pos.z / pos.x);
        double projectEdge = Math.sqrt(Math.pow(pos.x,2) + Math.pow(pos.z,2));
        this.yAxisAngle = MathUtil.atan(pos.y / projectEdge);
        this.length = Math.sqrt(Math.pow(projectEdge,2) + Math.pow(pos.y,2));
    }

    //convert the values of yAxisAngle and xzAxisAngle if it's not suitable;
    private void convertAngle(double xzAxisAngle, double yAxisAngle){
        yAxisAngle = yAxisAngle % 360;
        if (Math.abs(yAxisAngle) <= 90){
            this.xzAxisAngle = xzAxisAngle;
            this.yAxisAngle = yAxisAngle;
            return;
        }
        if (yAxisAngle < -90){
            this.yAxisAngle = -(180 + yAxisAngle);
            this.xzAxisAngle = xzAxisAngle + 180;
            return;
        }
        if (yAxisAngle > 90){
            this.yAxisAngle = 180 - yAxisAngle;
            this.xzAxisAngle = xzAxisAngle + 180;
            return;
        }
    }
}

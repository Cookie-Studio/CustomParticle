var ArrayList = Java.type('java.util.ArrayList');

function apply(tick,pos) {
    particle.setParticleEffect(particleEffect[1]);
    var list = new ArrayList();
    var roundPos = posUtil.spinAroundCircle(pos,pos.add(1.5,1,0),tick % 45 * 8,0,0);
    list.add(posUtil.spin(roundPos,0.5,(tick % 360 > 180) ? -180 : 0,(tick % 360 > 180) ? -(tick % 180 - 90) : tick % 180 - 90));
    list.add(posUtil.spin(roundPos,0.5,(tick % 360 > 180) ? -180 : 0,(tick % 360 > 180) ? tick % 180 - 90 : -(tick % 180 - 90)));
    return list;
}
var ArrayList = Java.type('java.util.ArrayList');
var HashMap = Java.type('java.util.HashMap');

function apply(tick,pos) {
    var map = new HashMap();
    var list = new ArrayList();
    var basePos = getBasePos(tick,pos);
    if ((tick / 22.5) % 2 == 0){
        list.add(math.newVector3(0 + tick % 90 * 4,tick % 22.5 * 8 - 90,1).addToPosition(basePos));
        list.add(math.newVector3(180 + tick % 90 * 4,-(tick % 22.5 * 8 - 90),1).addToPosition(basePos));
    }else{
        list.add(math.newVector3(0 + tick % 90 * 4,-(tick % 22.5 * 8 - 90),1).addToPosition(basePos));
        list.add(math.newVector3(180 + tick % 90 * 4,tick % 22.5 * 8 - 90,1).addToPosition(basePos));
    }
    for (var t = -5;t <= 0;t++){
        list.add(getBasePos(tick + t,pos));
    }
    list.add(pos.add(0,1,0));
    map.put(effect[1],list);
    return map;
}

function getBasePos(tick,pos){
    return math.newVector3(tick % 90 * 4,0,4).changePos(0,1,0).addToPosition(pos);
}
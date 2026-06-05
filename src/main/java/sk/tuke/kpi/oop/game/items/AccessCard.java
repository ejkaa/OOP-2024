package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor> {
    public AccessCard() {
        Animation animation = new Animation("sprites/key.png");
        setAnimation(animation);
    }

    @Override
    public Class<LockedDoor> getUsingActorClass() { return LockedDoor.class; }
    @Override
    public void useWith(LockedDoor actor) {
        if (actor.isLocked())
            actor.unlock();
        else actor.lock();
    }
}

package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.Openable;

public class Locker extends AbstractActor implements Usable<Ripley>{
    private boolean looted;
    public Locker() {
        Animation animation = new Animation("sprites/locker.png");
        setAnimation(animation);
        looted = false;
    }

    @Override
    public void useWith(Ripley actor) {
        if (looted) return;
        looted = true;
        Hammer hammer = new Hammer();
        getScene().addActor(hammer,getPosX(),getPosY());
    }

    @Override
    public Class<Ripley> getUsingActorClass() { return Ripley.class; }
}

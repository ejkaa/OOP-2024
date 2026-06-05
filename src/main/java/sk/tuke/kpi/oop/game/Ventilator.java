package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    private boolean repaired;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired", Ventilator.class);

    public Ventilator() {
        Animation animation = new Animation("sprites/ventilator.png", 32,32,0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
        animation.stop();
        repaired = false;
    }

    @Override
    public boolean repair() { // nemizne kladivo ???
        if (repaired) return false;
        repaired = true;
        getAnimation().play();
        getScene().getMessageBus().publish(VENTILATOR_REPAIRED, this);
        return true;
    }
}

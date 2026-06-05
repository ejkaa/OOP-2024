package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean active;
    private boolean exploted;
    private Animation activatedAnimation;
    private Animation explodedAnimation;

    public TimeBomb(float time) {
        this.time = time;
        active = false;
        exploted = false;
        Animation normalAnimation = new Animation("sprites/bomb.png");
        activatedAnimation = new Animation("sprites/bomb_activated.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);
        explodedAnimation = new Animation("sprites/small_explosion.png", 16, 16, 0.1f, Animation.PlayMode.ONCE);
        setAnimation(normalAnimation);
    }

    public boolean isActivated() { return active; }
    public void activate() {
        if (active) return;
        active = true;
        setAnimation(activatedAnimation);
        new ActionSequence<>(
            new Wait<>(time),
            new Invoke<>(this::explode)
        ).scheduleFor(this);
    }

    public boolean isExploded() { return exploted; }
    private void explode() {
        exploted = true;
        setAnimation(explodedAnimation);
        new When<>(
            () -> explodedAnimation.getCurrentFrameIndex() == explodedAnimation.getFrameCount() -1,
            new Invoke<>(() -> getScene().removeActor(this))
        ).scheduleFor(this);
    }
}

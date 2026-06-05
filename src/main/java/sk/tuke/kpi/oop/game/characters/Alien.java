package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;

public class Alien extends AbstractActor implements Movable, Enemy, Alive {
    private final int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;
    public static final Topic<Alien> ALIEN_DIED = Topic.create("alien died", Alien.class);

    public Alien() { this(100, new RandomlyMoving()); }
    public Alien(int health, Behaviour<? super Alien> behaviour) {
        Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);

        this.health = new Health(health);
        this.behaviour = behaviour;
        speed = 3;
    }

    @Override
    public int getSpeed() { return speed; }
    @Override
    public Health getHealth() { return health; }
    @Override
    public void stoppedMoving() { getAnimation().pause(); }
    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (behaviour != null) behaviour.setUp(this);

        health.onFatigued(() -> {
            scene.cancelActions(this);
            scene.removeActor(this);
            scene.getMessageBus().publish(Alien.ALIEN_DIED, this);
        });

        for (Actor actor : getScene().getActors()) {
            if (actor instanceof Enemy) continue;
            if (actor instanceof Alive) {
                Alive alive = (Alive) actor;
                new Loop<>( // todo prerusit ked alive zomrie
                    new Invoke<>( () -> {
                        if (alive.intersects(this))
                            alive.getHealth().drain(1);
                        alive.getHealth().onFatigued(() -> { // todo ma to tu vobec byt?
                            getScene().cancelActions(alive);
//                            scene.removeActor(alive); todo upravit to ze zmizne az ked sa dokonci animacia / dat pomalsie animaciu
                        });
                    })
                ).scheduleFor(this);
            }
        }
    }


}

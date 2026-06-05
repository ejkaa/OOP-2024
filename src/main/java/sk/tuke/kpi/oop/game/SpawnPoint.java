package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class SpawnPoint extends AbstractActor {
    private int alien_counter;
    private Disposable loop;
    private long time;

    public SpawnPoint(int alien_counter) {
        Animation animation = new Animation("sprites/spawn.png", 32, 32);
        setAnimation(animation);
        this.alien_counter = alien_counter;
        time = 0;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Ellipse2D.Float ellipse = new Ellipse2D.Float(getPosX() - 51, getPosY() - 51, 51*2, 51*2);

        loop = new Loop<>(
            new Invoke<>( () -> {
                for (Actor actor : scene.getActors()) {
                    if (actor instanceof Alive && !(actor instanceof Enemy)) {
                        Alive alive = (Alive) actor;
                        Rectangle2D.Float rectangle = new Rectangle2D.Float(alive.getPosX(), alive.getPosY(), alive.getWidth(), alive.getHeight());
                        if (ellipse.intersects(rectangle) && alien_counter > 0)
                            createAlien();
                    }
                }
            })
        ).scheduleFor(this);

    }

    private void createAlien() {
        if (System.currentTimeMillis() - time < 50) return;
        time = System.currentTimeMillis();
        Scene scene = getScene();
        if (scene == null) return;

        Alien alien = new Alien();
        scene.addActor(alien, getPosX(), getPosY());
        alien_counter--;
        if (alien_counter < 1) loop.dispose();
    }
}

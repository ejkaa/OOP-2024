package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Bullet extends AbstractActor implements Fireable {
    public Bullet() { setAnimation(new Animation("sprites/bullet.png", 16, 16)); }

    @Override
    public int getSpeed() { return 4; }
    @Override
    public void startedMoving(Direction direction) { getAnimation().setRotation(direction.getAngle()); }
    @Override
    public void collidedWithWall() { getScene().removeActor(this); }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);

        new Loop<>(
          new Invoke<>( () -> {
              for (Actor actor : scene.getActors()) {
                  if (actor instanceof Alive && !(actor instanceof Armed)) {
                      Alive alive = (Alive) actor;
                      if (alive.intersects(this)) {
                          alive.getHealth().drain(50);
                          scene.removeActor(this);
                      }
                  }
              }
          })
        ).scheduleFor(this);
    }
}

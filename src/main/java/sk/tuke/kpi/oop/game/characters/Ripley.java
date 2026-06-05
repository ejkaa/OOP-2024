package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Game;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private final Animation animation;
    private final Backpack backpack;
    private Health health;
    private Firearm firearm;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley() {
        super("Ripley");
        animation = new Animation("sprites/player.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animation);
        animation.stop();
        backpack = new Backpack("Ripley's backpack", 10);
        health = new Health(100, 100);
        firearm = new Gun(100);
    }

    @Override
    public void startedMoving(Direction direction) {
        animation.setRotation(direction.getAngle());
        animation.play();
    }

    @Override
    public int getSpeed() {
        return 2;
    }
    @Override
    public void stoppedMoving() {
        animation.stop();
    }
    @Override
    public Backpack getBackpack() {
        return backpack;
    }
    @Override
    public Health getHealth() { return health; }
    @Override
    public Firearm getFirearm() { return firearm; }
    @Override
    public void setFirearm(Firearm weapon) { firearm = weapon; }

    public int getAmmo() {
        return firearm.getAmmo();
    }
    public void reload(int ammo) { firearm.reload(ammo); }

    public void showRipleyState() {
        if (getScene() == null) return;
        Game game = getScene().getGame();
        Overlay overlay = game.getOverlay();

        int height = game.getWindowSetup().getHeight();
        int y = height - GameApplication.STATUS_LINE_OFFSET;

        overlay.drawText(String.format("Energy: %d | Ammo: %d", health.getValue(), firearm.getAmmo()), 100, y);
    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        health.onFatigued(() -> {
            scene.cancelActions(this);
            scene.getMessageBus().publish(RIPLEY_DIED, this);
            setAnimation(new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE));
        });
    }
}

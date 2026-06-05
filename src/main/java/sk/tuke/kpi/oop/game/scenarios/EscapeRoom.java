package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.SpawnPoint;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.MotherAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.Objects;

public class EscapeRoom implements SceneListener {
    private Ripley ripley;
    private boolean end;

    public static class Factory implements ActorFactory {
        public static final Topic<Door> FRONT_OPENED = new Topic<>("front opened", Door.class);
        public static final Topic<Door> BACK_OPENED = new Topic<>("back opened", Door.class);
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "ammo":
                    return new Ammo();
                case "alien":
                    if (Objects.equals(type, "running")) return new Alien(100, new RandomlyMoving());
//                    if (Objects.equals(type, "waiting1")) return new Alien(100, null);
                    if (Objects.equals(type, "waiting1")) return new Alien(100, new Observing<>(FRONT_OPENED, door -> door instanceof Door, new RandomlyMoving()));
                    if (Objects.equals(type, "waiting2")) return new Alien(100, new Observing<>(BACK_OPENED, door -> door instanceof Door, new RandomlyMoving()));
//                    if (Objects.equals(type, "waiting2")) return new Alien(100, null);
                case "alien mother":
//                    return new MotherAlien(null);
                    return new MotherAlien(new Observing<>(BACK_OPENED, door -> door instanceof Door, new RandomlyMoving()));
                case "exit door":
                    return new Door(name, Door.Orientation.VERTICAL);
                case "front door":
                    return new Door(name, Door.Orientation.VERTICAL);
                case "back door":
                    return new Door(name, Door.Orientation.HORIZONTAL);

//                case "access card":
//                    return new AccessCard();
//                case "locker":
//                    return new Locker();
//                case "ventilator":
//                    return new Ventilator();
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
//        SceneListener.super.sceneInitialized(scene);
        end = false;
        ripley = scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);

        SpawnPoint spawn = new SpawnPoint(3);
        scene.addActor(spawn, 400, 320);

        Input input = scene.getInput();
        Disposable shooterController = input.registerListener(new ShooterController(ripley));
        Disposable movableController = input.registerListener(new MovableController(ripley));
        Disposable keeperController = input.registerListener(new KeeperController(ripley));

        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, dead_ripley -> {
            movableController.dispose();
            keeperController.dispose();
            shooterController.dispose();
        });
        scene.getMessageBus().subscribe(Door.EXIT_OPENED, door -> end = true);
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        if (ripley != null) ripley.showRipleyState();
        if (end) scene.getGame().getOverlay().drawText("Game over!", 350, 300);
    }

}

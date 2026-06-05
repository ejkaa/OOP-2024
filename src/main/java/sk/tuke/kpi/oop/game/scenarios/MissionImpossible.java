package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;

public class MissionImpossible implements SceneListener {
    private Ripley ripley;

    public static class Factory implements ActorFactory {
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
//                case "door":
//                    return new LockedDoor(name, );
                case "access card":
                    return new AccessCard();
                case "locker":
                    return new Locker();
                case "ventilator":
                    return new Ventilator();
                default:
                    return null;
            }
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);
        Disposable disposable_movable = scene.getInput().registerListener(new MovableController(ripley));
        Disposable disposable_keeper = scene.getInput().registerListener(new KeeperController(ripley));
        scene.getGame().pushActorContainer(ripley.getBackpack());
        scene.follow(ripley);

//        scene.getMessageBus().subscribe( Door.DOOR_OPENED, Door -> {
//            new Loop<>( new ActionSequence<>(
//                new Wait<>(1),
//                new Invoke<>(() ->ripley.losing_energy())
//            )).scheduleFor(ripley);
//        });
//        scene.getMessageBus().subscribe( Door.DOOR_OPENED, Door -> new ContaminationDamage(ripley));
        // nezacne sa jej odoberat

        scene.getMessageBus().subscribe( Ripley.RIPLEY_DIED, Ripley -> {
            disposable_movable.dispose();
            disposable_keeper.dispose();
        });

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ripley.showRipleyState();

    }
}

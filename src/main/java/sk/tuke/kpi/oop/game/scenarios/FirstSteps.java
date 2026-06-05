package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.graphics.Overlay;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;

public class FirstSteps implements SceneListener {
    private Ripley ripley;
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = new Ripley();
        Energy energy = new Energy();
        MovableController movableController = new MovableController(ripley);
        KeeperController keeperController = new KeeperController(ripley);
//        Hammer hammer = new Hammer();
        Wrench wrench = new Wrench();
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
//        Reactor reactor = new Reactor();

        scene.getInput().registerListener(movableController);
        scene.getInput().registerListener(keeperController);
        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.addActor(ripley, 0,0);
        scene.addActor(energy, 100, 100);
//        scene.addActor(hammer, 120, 20);
        scene.addActor(wrench, 120, 120);
        scene.addActor(fireExtinguisher, 20, 120);
//        scene.addActor(reactor, 120, 120);

//        reactor.setDamage(70);
//        new Use<>(hammer).scheduleFor(reactor);

        ripley.getBackpack().add(new Hammer());
//        ripley.getBackpack().add(new Wrench());
//        ripley.getBackpack().add(new FireExtinguisher());

    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        Game game = scene.getGame();
        Overlay overlay = game.getOverlay();

        for (Actor actor : scene.getActors())
            if (actor instanceof Energy && ripley.intersects(actor))
                new Use<>((Energy) actor).scheduleFor(ripley);


        int height = game.getWindowSetup().getHeight();
        int y = height - GameApplication.STATUS_LINE_OFFSET;

        overlay.drawText(String.format("Energy: %d | Ammo: %d", ripley.getHealth().getValue(), ripley.getAmmo()), 100, y);
    }
}

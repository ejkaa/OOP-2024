package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.gamelib.inspector.InspectableScene;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;
//import sk.tuke.kpi.oop.game.scenarios.FirstSteps;
//import sk.tuke.kpi.oop.game.scenarios.MissionImpossible;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600); // nastavenie okna hry: nazov okna a jeho rozmery
        Game game = new GameApplication(windowSetup, new LwjglBackend()); // vytvorenie instancie hernej aplikacie

//        Scene scene = new World("world");
//        Scene scene = new InspectableScene(new World("world"), List.of("sk.tuke.kpi"));

//        Scene scene = new World("world");
//        game.addScene(scene);
//        scene.addListener(new FirstSteps());

//        Scene missionImpossible = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
        Scene escapeRoom = new World("escape-room", "maps/escape-room.tmx", new EscapeRoom.Factory());
        game.addScene(escapeRoom);
        escapeRoom.addListener(new EscapeRoom());

        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);
        game.start();
    }
}

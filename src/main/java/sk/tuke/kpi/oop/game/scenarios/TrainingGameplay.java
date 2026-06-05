package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;

import java.util.Map;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reactor = new Reactor();
        Cooler cooler = new Cooler(reactor);
        Computer computer = new Computer();
        DefectiveLight defectiveLight = new DefectiveLight();

        ChainBomb chainBomb1 = new ChainBomb(3);
        ChainBomb chainBomb2 = new ChainBomb(3);
        ChainBomb chainBomb3 = new ChainBomb(3);
        Helicopter helicopter = new Helicopter();

//        Mjolnir mjolnir = new Mjolnir();
//        Wrench wrench = new Wrench();
//        PowerSwitch switchR = new PowerSwitch(reactor);
//        PowerSwitch switchC = new PowerSwitch(cooler);
//        PowerSwitch switchL = new PowerSwitch(defectiveLight);

        Teleport teleport1 = new Teleport(null);
        Teleport teleport2 = new Teleport(null);
        Teleport teleport3 = new Teleport(null);

//        SmartCooler smartCooler1 = new SmartCooler(reactor);
//        SmartCooler smartCooler2 = new SmartCooler(reactor);





        Map<String, MapMarker> markers = scene.getMap().getMarkers();
        MapMarker reactorArea1 = markers.get("reactor-area-1");
//        MapMarker reactorArea2 = markers.get("reactor-area-2");
        MapMarker coolerArea1 = markers.get("cooler-area-1");
//        MapMarker coolerArea2 = markers.get("cooler-area-2");
//        MapMarker coolerArea3 = markers.get("cooler-area-3");
        MapMarker computerArea = markers.get("computer-area");



        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        scene.addActor(cooler, coolerArea1.getPosX(), coolerArea1.getPosY());
        scene.addActor(computer, computerArea.getPosX(), computerArea.getPosY());
        scene.addActor(defectiveLight, 250, 200);

        scene.addActor(chainBomb1, 120, 200);
        scene.addActor(chainBomb2, 160, 200);
        scene.addActor(chainBomb3, 200, 200);
        scene.addActor(helicopter, 50, 50);

//        scene.addActor(mjolnir, 200, 200);
//        scene.addActor(wrench, 150, 200);
//        scene.addActor(switchR, 150, 50);
//        scene.addActor(switchC, 100, 150);
//        scene.addActor(switchL, 100, 200);

        scene.addActor(teleport1, 50, 100);
        scene.addActor(teleport2, 50, 200);
        scene.addActor(teleport3, 50, 300);





        teleport1.setDestination(teleport2);
        teleport2.setDestination(teleport3);
        teleport3.setDestination(null);

        reactor.turnOn();
        reactor.addDevice(computer);
        reactor.addDevice(defectiveLight);
        cooler.turnOn();
        chainBomb1.activate();

//        new ActionSequence<>(
//            new Wait<>(5),
//            new Invoke<>(cooler::turnOn)
//        ).scheduleFor(cooler);

//        helicopter.searchAndDestroy();
//        new ActionSequence<>(
//            new Wait<>(5),
//            new Invoke<>(() -> chainBomb1.activate())
//        ).scheduleFor(cooler);

//        new When<>(
//            () -> reactor.getTemperature() >= 3000,
//            new Invoke<>(() -> reactor.repair(mjolnir))
//        ).scheduleFor(reactor);
    }
}

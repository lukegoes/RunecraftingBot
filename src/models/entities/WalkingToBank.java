package models.entities;

import models.enums.BanksAreas;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.utilities.Logger.log;

public class WalkingToBank extends Tasks{

    @Override
    public boolean accept() {
        return !Inventory.contains("Rune essence")
                && !BanksAreas.FALADOR.getArea().contains(Players.getLocal());
    }

    @Override
    public int execute() {
        GameObject portal = GameObjects.closest("Portal");

        if (portal != null) {
            log("Action -> Leaving altar.");
            if (portal.interact("Use")) {
                Sleep.sleepUntil(() -> GameObjects.closest("Portal") == null, 5000);
            } else {
                Walking.walk(portal);
            }
            return Utils.getGaussian(600, 1200);
        }

        if (Players.getLocal().isMoving() && Calculations.random(1, 20) == 1) {
            log("Action -> Moving camera.");

            int currentYaw = Camera.getYaw();
            int rotationAmount;

            if (Calculations.random(0, 2) == 0) {
                rotationAmount = Calculations.random(-60, -20);
            } else {
                rotationAmount = Calculations.random(20, 60);
            }

            Camera.rotateToYaw(currentYaw + rotationAmount);

            if (Calculations.random(1, 3) == 1) {
                Camera.rotateToPitch(Calculations.random(300, 383));
            }
        }

        if (Walking.shouldWalk(Calculations.random(3, 7))) {
            log("Action -> Walking to bank.");
            if (Walking.walk(BanksAreas.FALADOR.getArea().getRandomTile())) {
                return Utils.getGaussian(600, 1200);
            }
        }



        return Utils.getGaussian(450, 850);
    }
}

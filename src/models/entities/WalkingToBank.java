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

        if (Walking.shouldWalk(Calculations.random(3, 7))) {
            log("Action -> Walking to bank.");
            if (Walking.walk(BanksAreas.FALADOR.getArea().getRandomTile())) {
                return Utils.getGaussian(600, 1200);
            }
        }

        if (Players.getLocal().isMoving() && Calculations.random(1, 10) == 1) {
            log("Action -> Moving camera.");
            int baseYaw = Camera.getYaw();
            int humanYaw = baseYaw + Calculations.random(-15, 15);
            int humanPitch = Calculations.random(40, 90);
            Camera.rotateTo(humanYaw, humanPitch);
        }

        return Utils.getGaussian(450, 850);
    }
}

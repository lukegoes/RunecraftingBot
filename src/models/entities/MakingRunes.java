package models.entities;

import models.enums.RuinsAreas;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.Inventory;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.methods.walking.impl.Walking;
import org.dreambot.api.utilities.Sleep;
import org.dreambot.api.wrappers.interactive.GameObject;

import static org.dreambot.api.utilities.Logger.log;


public class MakingRunes extends Tasks{


    @Override
    public boolean accept() {
        return Inventory.contains("Rune essence");
    }

    @Override
    public int execute() {
        GameObject altar = GameObjects.closest(obj -> obj.getName().equals("Altar"));
        if (altar != null && altar.distance() < 20) {
            if (altar.interact("Craft-rune")) {
                log("Action -> Interacting with Altar.");
                Sleep.sleepUntil(() -> !Inventory.contains("Rune essence"), 5000);
            }
            return Utils.getGaussian(600, 900);
        }

        GameObject ruins = GameObjects.closest(obj -> obj.getName().equals("Mysterious ruins"));
        if (ruins != null && ruins.distance() < 15) {

            if (Camera.getPitch() < 65) {
                Camera.rotateToPitch(Calculations.random(65, 90));
                Sleep.sleep(Calculations.random(300, 600));
            }

            if (ruins.interact("Enter")) {
                log("Action -> Entering altar.");
                Sleep.sleepUntil(() -> GameObjects.closest("Altar") != null, 5000);
            }
            else {
                Camera.rotateToEntity(ruins);
            }
            return Utils.getGaussian(400, 800);
        }
        if (Walking.shouldWalk(6)) {
            Walking.walk(RuinsAreas.AIR.getArea());
        }

        return Utils.getGaussian(400, 800);
    }
}

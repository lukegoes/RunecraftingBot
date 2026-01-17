package models.entities;

import org.dreambot.api.Client;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;

public class Setup extends Tasks {

    private boolean initialized = false;

    @Override
    public boolean accept() {
        return !initialized && Client.isLoggedIn();
    }

    @Override
    public int execute() {
        if (Camera.getZoom() < 60) {
            Camera.setZoom(Calculations.random(20, 50));
        }

        Camera.rotateToPitch(Calculations.random(300, 383));

        initialized = true;
        return 600;
    }
}
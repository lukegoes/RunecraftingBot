package application;

import models.entities.*;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.input.Camera;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

@ScriptManifest(name = "Runecrafting Script", description = "Makes air runes!", author = "DarkWolf",
        version = 1.2, category = Category.RUNECRAFTING, image = "")
public class Main extends AbstractScript {

    private Tasks[] tasks;

    @Override
    public void onStart(){
        tasks = new Tasks[]{
                new Setup(),
                new Preparing(),
                new WalkingToBank(),
                new MakingRunes()
        };

    }

    @Override
    public int onLoop() {
        for (Tasks task : tasks) {
            if (task.accept()) {
                return task.execute();
            }
        }
        return 600;
    }
}

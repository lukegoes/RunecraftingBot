package application;

import models.entities.*;
import org.dreambot.api.Client;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@ScriptManifest(name = "Runecrafting Script", description = "Makes air runes!", author = "DarkWolf",
        version = 1.2, category = Category.RUNECRAFTING, image = "")
public class Main extends AbstractScript {

    private Tasks[] tasks;

    public static String currentStatus = "Iniciando...";
    public static int runesCrafted = 0;
    private long startTime = System.currentTimeMillis();
    private final Font fontTitle = new Font("Arial", Font.BOLD, 16);
    private final Font fontText = new Font("Arial", Font.PLAIN, 14);
    private final Font fontRunes = new Font("Arial", Font.BOLD, 15);

    @Override
    public void onStart(){
        startTime = System.currentTimeMillis();

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

    @Override
    public void onPaint(Graphics g) {
        Color bgColor = new Color(0, 0, 0, 180);
        Color borderColor = new Color(100, 149, 237);
        Color titleColor = Color.WHITE;
        Color infoColor = Color.LIGHT_GRAY;
        Color runeColor = Color.YELLOW;

        int x = 10;
        int y = 20;
        int width = 250;
        int height = 110;

        g.setColor(bgColor);
        g.fillRoundRect(x, y, width, height, 10, 10);
        g.setColor(borderColor);
        g.drawRoundRect(x, y, width, height, 10, 10);

        g.setFont(fontTitle);
        drawStringWithShadow(g, "DarkWolf Runecrafting v1.2", x + 15, y + 25, titleColor);

        long timeRunning = System.currentTimeMillis() - startTime;
        String formattedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(timeRunning),
                TimeUnit.MILLISECONDS.toMinutes(timeRunning) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeRunning)),
                TimeUnit.MILLISECONDS.toSeconds(timeRunning) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeRunning))
        );

        g.setFont(fontText);
        drawStringWithShadow(g, "Tempo: " + formattedTime, x + 15, y + 50, infoColor);

        drawStringWithShadow(g, "Status: " + currentStatus, x + 15, y + 70, infoColor);

        g.setFont(fontRunes);
        String runasText = "Runas Criadas: " + runesCrafted;

        g.setColor(new Color(100, 149, 237, 100));
        g.drawLine(x + 10, y + 80, x + width - 10, y + 80);

        drawStringWithShadow(g, runasText, x + 15, y + 98, runeColor);
    }

    private void drawStringWithShadow(Graphics g, String text, int x, int y, Color color) {
        g.setColor(Color.BLACK);
        g.drawString(text, x + 1, y + 1);
        g.setColor(color);
        g.drawString(text, x, y);
    }
}


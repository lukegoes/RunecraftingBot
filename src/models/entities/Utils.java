package models.entities;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.utilities.Sleep;

public class Utils {
    public static int getGaussian(int min, int max) {
        int mean = (min + max) / 2;
        int dev = (max - min) / 4;

        int result = (int) Calculations.nextGaussianRandom(mean, dev);

        if (result < min) return min;
        return Math.min(result, max);

    }
}
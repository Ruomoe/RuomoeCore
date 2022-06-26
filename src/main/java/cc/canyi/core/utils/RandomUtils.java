package cc.canyi.core.utils;

import lombok.Getter;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    @Getter
    private static final Random random = new Random();

    public static boolean baseProbability(double value) {
        return value > 0 && value / 100D > random.nextDouble();
    }

    public static boolean baseProbability(double value, double addon) {
        return (value + (value * (addon / 100D))) > 0 && (value + (value * (addon / 100D))) / 100D > random.nextDouble();
    }

    public static <T> T getRandomListObject(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

}

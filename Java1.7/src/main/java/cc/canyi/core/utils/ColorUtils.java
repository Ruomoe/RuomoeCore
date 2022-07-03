package cc.canyi.core.utils;

public class ColorUtils {
    public static int RGB2INT(int r, int g, int b) {
        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }

    public static int[] INT2RGB(int color) {
        return new int[] {(color & 0xff0000) >> 16, (color & 0xff00) >> 8, color & 0xff};
    }
}

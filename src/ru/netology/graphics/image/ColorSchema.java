package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {
    char[] chars = {'#', '$', '@', '%', '*', '+', '"', '-'};

    @Override
    public char convert(int color) {
        int charColor = (int) Math.round(chars.length / 255.0 * color);
        return chars[charColor];
    }
}



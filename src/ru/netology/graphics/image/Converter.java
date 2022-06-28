package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Converter implements TextGraphicsConverter {
    private int maxRatio;
    private int width;
    private int height;

    TextColorSchema schema = new ColorSchema();

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));


        int width = img.getWidth();
        int height = img.getHeight();
        int maxWidth = 100;
        int maxHeight = 100;
        int newWidth = width;
        int newHeight = height;

        if (height / width > 2 && width / height > 2) {
            double ratio = (double) img.getWidth(null) / (double) img.getWidth(null);
            throw new BadImageSizeException(maxRatio, ratio);
        }

        setMaxWidth(width);
        setMaxHeight(height);

        if (width > maxWidth) {
            newWidth = maxWidth;
            newHeight = (newWidth * height) / width;
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (newHeight * width) / height;
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);

        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);

        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);

        ImageIO.write(bwImg, "png", new File("out.png"));
        WritableRaster bwRaster = bwImg.getRaster();


        char[][] array = new char[newHeight][newWidth];
        for (int h = 0; h < bwRaster.getHeight(); h++) {
            for (int w = 0; w < bwRaster.getWidth(); w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                array[h][w] = c;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                sb = sb.append(String.valueOf(array[i][j]) + String.valueOf(array[i][j]));
            }
            sb.append("\n");
        }

        String result = sb.toString();
        return result;

    }

    @Override
    public void setMaxWidth(int width) {
        if (width < 100) {
            this.width = width;
        } else {
            this.width = 100;
        }
    }

    @Override
    public void setMaxHeight(int height) {
        if (height < 100) {
            this.height = height;
        } else {
            this.height = 100;
        }
    }

    @Override
    public void setMaxRatio(int maxRatio) {
        if (height / width < 2 && width / height < 2) {
            this.maxRatio = maxRatio;
        } else {
            System.out.println("Соотношение сторон не подходящее");

        }
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}

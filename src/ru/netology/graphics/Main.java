package ru.netology.graphics;

import ru.netology.graphics.image.Converter;
import ru.netology.graphics.image.TextGraphicsConverter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Converter(); // Создайте тут объект вашего класса конвертера

        //   GServer server = new GServer(converter); // Создаём объект сервера
        //   server.start(); // Запускаем


        String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);

    }
}

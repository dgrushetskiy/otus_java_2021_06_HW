package ru.otus;

import java.util.ArrayList;

public class App {
    public static void main(String... args) {
        // Видим класс из модуля L02-gradle2-libApi
        AppFromHW01.say();

        // Случай 1
        // в модуле L02-gradle2-libApi стоит api 'com.google.guava:guava'
        // и тут guava доступна, т.е. зависимость "протекла"
        System.out.println(com.google.common.collect.Lists.reverse(new ArrayList<>()));

        // Случай 2
        // в модуле L02-gradle2-libApi стоит implementation 'com.google.guava:guava'
        // и тут guava НЕ доступна, т.е. зависимость "изолирована"
        //System.out.println(com.google.common.collect.Lists.reverse(new ArrayList<>()));

    }
}

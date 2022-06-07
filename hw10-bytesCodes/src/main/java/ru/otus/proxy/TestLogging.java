package ru.otus.proxy;

public class TestLogging {

    @Log
    public void calculation(int x) {
        System.out.println(x);
    }

    @Log
    public void calculation(int x, int y) {
        int sum = x + y;
        System.out.println(sum);
    }

    @Log
    public void calculation(int x, int y, String z) {
        String sum = z + x + y;
        System.out.println(sum);
    }
}

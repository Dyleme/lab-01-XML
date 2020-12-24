package com.company;

public class Main {

    public static void main(String[] args) {
        WindowModel model = new WindowModel();
        WindowView win = new WindowView(model);
        WindowController controller = new WindowController(model, win);
    }
}

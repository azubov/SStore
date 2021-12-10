package ru.lanit.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ImageSet {

    private static Set<String> images = new HashSet<>();

    public static Set<String> getImages() {
        return images;
    }

    public static void addImage(String name) {
        images.add(name);
    }

    public static void addAllImages(String... imageNames) {
        images.addAll(Arrays.stream(imageNames).toList());
    }

}

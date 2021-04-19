package cn.enn.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class App {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Field locations = Model.class.getDeclaredField("locations");
        locations.setAccessible(true);


        Field modifiers = locations.getClass().getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(locations, locations.getModifiers() & ~Modifier.FINAL);

        locations.set(Model.class, new String[]{"123"});
        modifiers.setInt(locations, locations.getModifiers() & ~Modifier.FINAL);

        Model.print();
    }
}

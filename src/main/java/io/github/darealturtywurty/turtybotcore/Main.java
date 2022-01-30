package io.github.darealturtywurty.turtybotcore;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import io.github.darealturtywurty.turtybotcore.module.BotModule;

public class Main {
    private static List<BotModule> modules;

    public static List<BotModule> getModules() {
        return modules;
    }

    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, NoSuchFieldException {
        final Reflections reflections = new Reflections(
                new ConfigurationBuilder().filterInputsBy(new FilterBuilder().includePackage(""))
                        .setUrls(ClasspathHelper.forPackage("")).setScanners(Scanners.valueOf("SubTypes")));
        final List<Class<? extends BotModule>> moduleClasses = reflections.getSubTypesOf(BotModule.class).stream()
                .toList();
        final var modules = new BotModule[moduleClasses.size()];
        for (int index = 0; index < moduleClasses.size(); index++) {
            modules[index] = moduleClasses.get(index).getConstructor().newInstance();
            modules[index].getConfig().create();
            final Field instance = modules[index].getClass().getField("instance");
            instance.setAccessible(true);
            if (instance.get(modules[index]) != null)
                throw new IllegalStateException(modules[index].getClass().getSimpleName()
                        + " cannot be made since module has already been constructed under another instance!");
            instance.set(modules[index], modules[index]);
        }

        Main.modules = List.of(modules);
    }
}

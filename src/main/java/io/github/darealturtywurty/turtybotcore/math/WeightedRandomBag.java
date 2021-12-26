package io.github.darealturtywurty.turtybotcore.math;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import io.github.darealturtywurty.turtybotcore.utils.Constants;

public class WeightedRandomBag<T extends Object> {
    private final List<Entry> entries = new ArrayList<>();

    private double accumulatedWeight;

    public void addEntry(final T object, final double weight) {
        this.accumulatedWeight += weight;
        final var entry = new Entry();
        entry.object = object;
        entry.accumulatedWeight = this.accumulatedWeight;
        this.entries.add(entry);
    }

    @Nullable
    public T getRandom() {
        final double randVal = Constants.RANDOM.nextDouble() * this.accumulatedWeight;

        for (final Entry entry : this.entries) {
            if (entry.accumulatedWeight >= randVal)
                return entry.object;
        }
        return null;
    }

    private class Entry {
        double accumulatedWeight;
        T object;
    }
}
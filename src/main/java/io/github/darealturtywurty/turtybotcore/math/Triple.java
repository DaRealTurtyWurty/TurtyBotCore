package io.github.darealturtywurty.turtybotcore.math;

public class Triple<L, M, R> {
    public final L left;
    public final M middle;
    public final R right;

    public Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public static <L, M, R> Triple<L, M, R> create(L left, M middle, R right) {
        return new Triple<>(left, middle, right);
    }

    private static boolean equals(Object obj1, Object obj2) {
        return obj1 == null && obj2 == null || obj1 != null && obj1.equals(obj2);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Triple))
            return false;
        final Triple<?, ?, ?> triple = (Triple<?, ?, ?>) obj;
        return this.left.equals(triple.left) && this.middle.equals(triple.middle)
                && this.right.equals(triple.right);
    }

    @Override
    public int hashCode() {
        return (this.left == null ? 0 : this.left.hashCode())
                ^ (this.middle == null ? 0 : this.middle.hashCode())
                ^ (this.right == null ? 0 : this.right.hashCode());
    }
}
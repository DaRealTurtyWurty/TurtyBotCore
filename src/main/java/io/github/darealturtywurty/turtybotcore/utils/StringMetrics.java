package io.github.darealturtywurty.turtybotcore.utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class StringMetrics {
    private final Font font;
    private final FontRenderContext context;

    public StringMetrics(final Graphics2D graphics) {
        this.font = graphics.getFont();
        this.context = graphics.getFontRenderContext();
    }

    public Rectangle2D getBounds(final String message) {
        return this.font.getStringBounds(message, this.context);
    }

    public double getHeight(final String message) {
        final Rectangle2D bounds = getBounds(message);
        return bounds.getHeight();
    }

    public double getWidth(final String message) {
        final Rectangle2D bounds = getBounds(message);
        return bounds.getWidth();
    }
}
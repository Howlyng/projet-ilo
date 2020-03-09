package filters;

import figures.Figure;

import java.awt.*;

public class EdgeColorFilter extends FigureFilter<Paint> {
    public EdgeColorFilter(Paint paint) {
        super(paint);
    }
    @Override
    public boolean test(Figure f) {
        return f.getEdgePaint().equals(element);    }
}

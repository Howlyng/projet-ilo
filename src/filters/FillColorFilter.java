package filters;

import figures.Figure;

import java.awt.*;

public class FillColorFilter extends FigureFilter<Paint>{
    public FillColorFilter(Paint paint) {
        super(paint);
    }
    @Override
    public boolean test(Figure f) {
        return f.getFillPaint().equals(element);
    }
}

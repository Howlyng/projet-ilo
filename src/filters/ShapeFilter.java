package filters;

import figures.Figure;
import figures.enums.FigureType;

public class ShapeFilter extends FigureFilter<FigureType> {

    public ShapeFilter(FigureType element) {
        super(element);
    }

    @Override
    public boolean test(Figure f) {
        return f.getType() == element;
    }
}

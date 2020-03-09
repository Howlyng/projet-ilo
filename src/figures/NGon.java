package figures;

import figures.enums.FigureType;

import java.awt.*;
import java.awt.geom.Point2D;

public class NGon extends Figure{
    private static int nbNGon = 0;


    public NGon(BasicStroke stroke, Paint edge, Paint fill, Point2D p) {
        super(stroke, edge, fill);
    }

    @Override
    public Figure clone() {
        return null;
    }

    @Override
    public void setLastPoint(Point2D p) {

    }

    @Override
    public void normalize() {

    }

    @Override
    public Point2D getCenter() {
        return null;
    }

    @Override
    public FigureType getType() {
        return null;
    }
}

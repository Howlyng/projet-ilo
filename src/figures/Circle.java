package figures;

import figures.enums.FigureType;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Circle extends Figure {

    private static int nbCercle = 0;

    public Circle(BasicStroke stroke, Paint edge, Paint fill, Point2D centre, double rayon) {
        super(stroke, edge, fill);
        instanceNumber = ++nbCercle;
        double x = centre.getX() - rayon;
        double y = centre.getY() - rayon;
        double l = 2.0f * rayon;
        double h = l;
        shape = new Ellipse2D.Double(x,y, l, h);
    }

    public Circle (Circle circle){
        super(circle);
        Ellipse2D ellipse2D = (Ellipse2D) circle.shape;
        shape = new Ellipse2D.Double(ellipse2D.getMinX(), ellipse2D.getCenterY(), ellipse2D.getWidth(), ellipse2D.getHeight());
    }



    @Override
    public Figure clone() {
        return new Circle(this);
    }

    @Override
    public void setLastPoint(Point2D p) {
        Ellipse2D.Double ellipse = (Ellipse2D.Double) shape;
        double l = p.getX()-ellipse.x;
        double h = p.getY()-ellipse.y;
        double taille = Math.abs(l) < Math.abs(h) ? l : h;
        ellipse.width = taille;
        ellipse.height = taille;
    }

    @Override
    public void normalize() {
        Point2D centre = getCenter();
        double x = centre.getX();
        double y = centre.getY();
        Ellipse2D.Double c = (Ellipse2D.Double) shape;
        translation.translate(x, y);
        c.setFrame(c.x - x, c.y -y, c.width,c.height);

    }

    @Override
    public Point2D getCenter() {
        Ellipse2D ellipse2D = (Ellipse2D.Double) shape;
        Point2D centre = new Point2D.Double(ellipse2D.getCenterX(), ellipse2D.getCenterY());
        Point2D barycentre = new Point2D.Double();
        getTransform().transform(centre, barycentre);
        return barycentre;
    }

    @Override
    public FigureType getType() {
        return FigureType.CIRCLE;
    }
}

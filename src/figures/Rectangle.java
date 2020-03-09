package figures;

import figures.enums.FigureType;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class Rectangle extends Figure {
    private static int nbRect;
    public Rectangle(BasicStroke stroke, Paint edge, Paint fill, Point2D topLeft, Point2D bottomRight) {
        super(stroke, edge, fill);
        instanceNumber=++nbRect;
        double x = topLeft.getX();
        double y = topLeft.getY();
        double l = (bottomRight.getX()-topLeft.getX());
        double h = (bottomRight.getY()-topLeft.getY());
        shape = new Rectangle2D.Double(x,y,l,h);
    }

    protected Rectangle(BasicStroke s, Paint e, Paint f){
        super(s,e,f);
        shape = null;
    }

    public Rectangle(Rectangle r){
        super(r);
        Rectangle2D rectangle = (Rectangle2D) r.shape;
        shape = new Rectangle2D.Double(rectangle.getMinX(), rectangle.getMinY(), rectangle.getWidth(),rectangle.getHeight());
    }

    @Override
    public Figure clone() {
        return new Rectangle(this);
    }

    @Override
    public void setLastPoint(Point2D p) {
        if(shape!=null){
            Rectangle2D.Double rectangle = (Rectangle2D.Double) shape;
            double l = p.getX()-rectangle.x;
            double h = p.getY()-rectangle.y;
            rectangle.width=l;
            rectangle.height=h;
        }
        else{
            System.err.println(getClass().getSimpleName() + "setLastPoint : erreur");
        }
    }

    @Override
    public void normalize() {
        double centreX = getCenter().getX();
        double centreY = getCenter().getY();
        RectangularShape rectangularShape = (RectangularShape) shape;
        translation.translate(centreX, centreY);
        rectangularShape.setFrame(rectangularShape.getX()-centreX, rectangularShape.getY()-centreY,rectangularShape.getWidth(),rectangularShape.getHeight());
    }

    @Override
    public Point2D getCenter() {
        RectangularShape rectangularShape = (RectangularShape) shape;
        Point2D centre = new Point2D.Double(rectangularShape.getCenterX(),rectangularShape.getY());
        Point2D barycentre = new Point2D.Double();
        getTransform().transform(centre, barycentre);
        return barycentre;
    }

    @Override
    public FigureType getType() {
        return FigureType.RECTANGLE;
    }
}

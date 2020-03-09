package figures;

import figures.enums.FigureType;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.awt.geom.RoundRectangle2D;

public class RoundedRectangle extends Figure {
    private static int nbRRect = 0;
    public RoundedRectangle(BasicStroke stroke, Paint edge, Paint fill, Point2D topLeft, Point2D bottomRight, int arc) {
        super(stroke, edge, fill);
        instanceNumber=++nbRRect;
        double x = topLeft.getX();
        double y = topLeft.getY();
        double l = bottomRight.getX()-topLeft.getY();
        double h = bottomRight.getY()-topLeft.getY();
        double tailleMin = (l < h ? l : h) / 2.0f;
        double coin = (arc < tailleMin ? arc : tailleMin);
        shape = new RoundRectangle2D.Double(x,y,l,h,coin, coin);
    }

    public RoundedRectangle(RoundedRectangle roundedRectangle){
        super(roundedRectangle);
        RoundRectangle2D rectangle2D = (RoundRectangle2D) roundedRectangle.shape;
        shape = new RoundRectangle2D.Double(rectangle2D.getMinX(),rectangle2D.getMinY(),rectangle2D.getWidth(),rectangle2D.getHeight(),rectangle2D.getArcWidth(),rectangle2D.getArcHeight());
    }

    @Override
    public Figure clone(){
        return new RoundedRectangle(this);
    }

    @Override
    public void setLastPoint(Point2D p){
        RoundRectangle2D.Double r = (RoundRectangle2D.Double) shape;
        r.width = p.getX()-r.x;
        r.height=p.getY()-r.y;
    }

    public void setArc (Point2D p){
        RoundRectangle2D.Double r = (RoundRectangle2D.Double) shape;
        double arcX = p.getX();
        double arcY = p.getY();
        double x = r.getMaxX();
        double y = r.getMaxY();
        if(arcX > x){
            if (arcY >= y){
                r.arcwidth=0;
                r.archeight=0;
            }
            else {
                r.arcwidth=y-arcY;
                r.archeight=r.arcwidth;
            }
        }
        else {
            if (arcY>y){
                r.arcwidth=x-arcX;
                r.archeight=r.arcwidth;
            }
            else {
                r.archeight=0;
                r.arcwidth=0;
            }
        }
    }

    @Override
    public FigureType getType(){
        return FigureType.ROUNDED_RECTANGLE;
    }

    @Override
    public Point2D getCenter()
    {
        RectangularShape rect = (RectangularShape) shape;

        Point2D center = new Point2D.Double(rect.getCenterX(), rect.getCenterY());
        Point2D tCenter = new Point2D.Double();
        getTransform().transform(center, tCenter);

        return tCenter;
    }

    @Override
    public void normalize() {
        Point2D center = getCenter();
        double cx = center.getX();
        double cy = center.getY();
        RectangularShape rect = (RectangularShape) shape;
        translation.translate(cx, cy);
        rect.setFrame(rect.getX() - cx, rect.getY() - cy, rect.getWidth(), rect.getHeight());
    }
}

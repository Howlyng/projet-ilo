package figures;

import figures.enums.FigureType;

import java.awt.*;
import java.awt.geom.Point2D;

public class Polygon extends Figure {
    private static int nbPoly = 0;

    public Polygon(BasicStroke stroke, Paint edgeColor, Paint fillColor, Point p1, Point p2){
        super(stroke, edgeColor, fillColor);
        instanceNumber = ++nbPoly;
        java.awt.Polygon polygon = new java.awt.Polygon();
        polygon.addPoint(p1.x, p1.y);
        polygon.addPoint(p2.x, p2.y);
        shape = polygon;
    }

    public Polygon(Polygon polygon){
        super(polygon);
        java.awt.Polygon poly = (java.awt.Polygon) polygon.shape;
        int[] xpoints = new int[poly.npoints];
        int[] ypoints = new int[poly.npoints];
        for (int i=0;i<poly.npoints;i++){
            xpoints[i] = poly.xpoints[i];
            ypoints[i] = poly.ypoints[i];
        }
        shape = new java.awt.Polygon(xpoints,ypoints,poly.npoints);
    }

    @Override
    public Figure clone(){
        return new Polygon(this);
    }

    @Override
    public void setLastPoint(Point2D p){
        java.awt.Polygon polygon = (java.awt.Polygon) shape;
        int indice = polygon.npoints-1;
        if (indice >= 0){
            polygon.xpoints[indice] = Double.valueOf(p.getX()).intValue();
            polygon.ypoints[indice] = Double.valueOf(p.getY()).intValue();
        }
    }

    protected Point2D computeCenter(){
        java.awt.Polygon polygon = (java.awt.Polygon) shape;
        double[] centre = {0.0, 0.0};
        if (polygon.npoints > 0){
            for (int i=0;i< polygon.npoints;i++){
                centre[0] = centre[0] + polygon.xpoints[i];
                centre[1] = centre[1] + polygon.ypoints[i];
            }
            centre[0] = centre[0]/polygon.npoints;
            centre[1] = centre[1]/polygon.npoints;
        }
        return new Point2D.Double(centre[0],centre[1]);
    }

    @Override
    public Point2D getCenter(){
        Point2D centre  = computeCenter();
        Point2D barycentre = new Point2D.Double();
        getTransform().transform(centre,barycentre);
        return barycentre;
    }


    public void addPoint(int x, int y){
        java.awt.Polygon polygon = (java.awt.Polygon) shape;
        polygon.addPoint(x,y);
    }

    @Override
    public void normalize() {
        Point2D centre = computeCenter();
        double centreX = centre.getX();
        double centreY = centre.getY();
        translation.setToTranslation(centreX, centreY);
        java.awt.Polygon polygon = (java.awt.Polygon) shape;
        if (polygon.npoints > 0){
            int[] x = new int[polygon.npoints];
            int[] y = new int[polygon.npoints];
            for (int i=0; i<polygon.npoints; i++){
                x[i]=polygon.xpoints[i]-Double.valueOf(centreX).intValue();
                y[i]=polygon.ypoints[i]-Double.valueOf(centreY).intValue();
            }
            polygon.reset();
            for (int i=0; i<x.length;i++){
                polygon.addPoint(x[i], y[i]);
            }
        }
    }

    @Override
    public FigureType getType() {
        return FigureType.POLYGON;
    }

    public void removeLastPoint(){
        java.awt.Polygon polygon = (java.awt.Polygon) shape;
        if (polygon.npoints>1){
            int[] x = new int[polygon.npoints-1];
            int[] y = new int[polygon.npoints-1];
            for (int i=0; i < x.length;i++){
                x[i]=polygon.xpoints[i];
                y[i]=polygon.ypoints[i];
            }
            polygon.reset();
            for (int i=0;i<0;i++){
                polygon.addPoint(x[i], y[i]);
            }
        }
    }
}

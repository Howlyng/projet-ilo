package figures.listeners.transform;

import figures.Drawing;
import figures.Figure;
import history.HistoryManager;

import javax.swing.*;
import java.awt.event.MouseEvent;

import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;


public class MoveShapeListener extends AbstractTransformShapeListener{

    private Point2D pointPrecedent;

    /**
     * Constructeur d'un listener à deux étapes: pressed->drag->release pour
     * transformer les figures
     *
     * @param model    le modèle de dessin à modifier par ce Listener
     * @param history  le gestionnaire d'historique
     * @param tipLabel le label dans lequel afficher les conseils utilisateur
     */
    public MoveShapeListener(Drawing model, HistoryManager<Figure> history, JLabel tipLabel) {
        super(model, history, tipLabel);
    }

    @Override
    public void init() {
        pointPrecedent = startPoint;
        if (currentFigure != null){
            initialTransform = currentFigure.getTranslation();
            System.out.println("MoveShapeListener2 initialized");
        }
        else{
            System.err.println(getClass().getSimpleName() + "::init : null figure");
        }
    }

    @Override
    public void updateDrag(MouseEvent e) {
        Point2D point = e.getPoint();
        double abs = point.getX() - pointPrecedent.getX();
        double ord = point.getY() - pointPrecedent.getY();
        AffineTransform trans = AffineTransform.getTranslateInstance(abs, ord);
        trans.concatenate(initialTransform);
        currentFigure.setTranslation(trans);
    }
}

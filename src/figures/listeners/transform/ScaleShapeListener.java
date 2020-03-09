package figures.listeners.transform;

import figures.Drawing;
import figures.Figure;
import history.HistoryManager;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ScaleShapeListener extends AbstractTransformShapeListener{
    private Double distance1;
    private Double distance2;
    /**
     * Constructeur d'un listener à deux étapes: pressed->drag->release pour
     * transformer les figures
     *
     * @param model    le modèle de dessin à modifier par ce Listener
     * @param history  le gestionnaire d'historique
     * @param tipLabel le label dans lequel afficher les conseils utilisateur
     */
    public ScaleShapeListener(Drawing model, HistoryManager<Figure> history, JLabel tipLabel) {
        super(model, history, tipLabel);
        keyMask = InputEvent.ALT_MASK;
    }

    @Override
    public void init() {
        if ((currentFigure != null) && (center != null)){
            distance1 = center.distance(startPoint);
            distance2 = distance1;
            initialTransform = currentFigure.getScale();
        }
        else{
            System.err.println("erreur (ScaleShape)");
        }
    }

    @Override
    public void updateDrag(MouseEvent e) {
        Point2D currentPoint = e.getPoint();
        if ((currentFigure != null) && (center != null)){
            distance2 = center.distance(currentPoint);
            double scale = distance2 / distance1;
            AffineTransform scaleT = AffineTransform.getScaleInstance(scale, scale);
            scaleT.concatenate(initialTransform);
            currentFigure.setScale(scaleT);
        }
        else{
            System.err.println(getClass().getSimpleName() + "::updateDrag : figure ou centre null");
        }
    }
}

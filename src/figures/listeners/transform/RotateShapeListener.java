package figures.listeners.transform;

import figures.Drawing;
import figures.Figure;
import figures.listeners.creation.AbstractCreationListener;
import history.HistoryManager;
import utils.Vector2D;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class RotateShapeListener extends AbstractTransformShapeListener {
    private Vector2D vecteur1;
    private Vector2D vecteur2;

    /**
     * Constructeur d'un listener à deux étapes: pressed->drag->release pour
     * transformer les figures
     *
     * @param model    le modèle de dessin à modifier par ce Listener
     * @param history  le gestionnaire d'historique
     * @param tipLabel le label dans lequel afficher les conseils utilisateur
     */
    public RotateShapeListener(Drawing model, HistoryManager<Figure> history, JLabel tipLabel) {
        super(model, history, tipLabel);
        keyMask = InputEvent.SHIFT_MASK;
    }

    @Override
    public void init() {
        if ((currentFigure != null) && (center != null)){
            vecteur1 = new Vector2D(center, startPoint);
            vecteur2 = new Vector2D(vecteur1);
            initialTransform = currentFigure.getRotation();
        }
        else{
            System.err.println(getClass().getSimpleName() + "erreur");
        }
    }

    @Override
    public void updateDrag(MouseEvent e) {
        Point2D currentPoint = e.getPoint();
        vecteur2.setEnd(currentPoint);
        if (currentFigure != null) {
            double angle = vecteur1.angle(vecteur2);
            AffineTransform rotate = AffineTransform.getRotateInstance(angle);
            rotate.concatenate(initialTransform);
            currentFigure.setRotation(rotate);
        }
        else {
            System.err.println(getClass().getSimpleName() + "updateDrag : figure null");
        }
    }
}

package figures.listeners.creation;

import figures.Drawing;
import figures.Figure;
import history.HistoryManager;



import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.JLabel;
import java.awt.Point;

import figures.Polygon;

public class PolygonCreationListener extends AbstractCreationListener {
    /**
     * Constructeur protégé (destiné à être utilisé par les classes filles)
     *
     * @param model     le modèle de dessin à modifier par ce creationListener
     * @param history   le gestionnaire d'historique pour les Undo/Redo
     * @param infoLabel le label dans lequel afficher les conseils d'utilisation
     * @param nbSteps   le nombres d'étapes de création de la figure
     */
    public PolygonCreationListener(Drawing model, HistoryManager<Figure> history, JLabel infoLabel) {
        super(model, history, infoLabel, 2);
        tips[0] = new String("Clic gauche pour commencer le polygone");
        tips[1] = new String("clic gauche pour ajouter un point | clic droit pour créer");
        updateTip();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        if (currentStep == 0){
            if (e.getButton() == MouseEvent.BUTTON1){
                startAction(e);
            }
        }
        else{
            Polygon poly = (Polygon) currentFigure;
            switch (e.getButton())
            {
                case MouseEvent.BUTTON1:
                    poly.addPoint(p.x, p.y);
                    break;
                case MouseEvent.BUTTON2:
                    poly.removeLastPoint();
                    break;
                case MouseEvent.BUTTON3:
                    endAction(e);
                    break;
            }
        }

        drawingModel.update();
        updateTip();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentStep > 0){
            if (currentFigure != null){
                currentFigure.setLastPoint(e.getPoint());
            }
            else{
                System.err.println(getClass().getSimpleName() + "erreur");
            }
            drawingModel.update();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}

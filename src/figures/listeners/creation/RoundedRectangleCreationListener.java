package figures.listeners.creation;

import figures.Drawing;
import figures.Figure;
import figures.RoundedRectangle;
import history.HistoryManager;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class RoundedRectangleCreationListener extends AbstractCreationListener {
    /**
     * Constructeur protégé (destiné à être utilisé par les classes filles)
     *
     * @param model     le modèle de dessin à modifier par ce creationListener
     * @param history   le gestionnaire d'historique pour les Undo/Redo
     * @param infoLabel le label dans lequel afficher les conseils d'utilisation
     * @param nbSteps   le nombres d'étapes de création de la figure
     */
    public RoundedRectangleCreationListener(Drawing model, HistoryManager<Figure> history, JLabel infoLabel) {
        super(model, history, infoLabel, 3);
        tips[0] = new String("Bouton gauche + glisser pour commencer le regtangle");
        tips[1] = new String("Relâchez pour créer le rectangle");
        tips[2] = new String("Clic gauche pour définir l'arrondi des coins");
        updateTip();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 2)){
            endAction(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 0)){
            startAction(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if ((e.getButton() == MouseEvent.BUTTON1) && (currentStep == 1)){
            nextStep();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentStep == 1){
            currentFigure.setLastPoint(e.getPoint());
            drawingModel.update();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (currentStep == 2){
            RoundedRectangle rect = (RoundedRectangle) currentFigure;
            rect.setArc(e.getPoint());
            drawingModel.update();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}

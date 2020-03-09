package figures.listeners;

import figures.Drawing;
import figures.Figure;
import history.HistoryManager;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class SelectionFigureListener extends AbstractFigureListener{
    /**
     * Constructeur protégé (destiné à être utilisé par les classes filles)
     *
     * @param model     le modèle de dessin à modifier par ce listener
     * @param history   le gestionnaire d'historique pour créer des sauvegardes
     *                  de l'état courant des figures avant toute modification des figures
     * @param infoLabel le label dans lequel afficher les conseils d'utilisation
     * @param nbSteps   le nombres d'étapes de l'action à réaliser
     */
    public SelectionFigureListener(Drawing model, HistoryManager<Figure> history, JLabel infoLabel) {
        super(model, history, infoLabel, 1);
        tips[0] = new String("Cliquez pour sélectionner ou déselectionner une figure");
        updateTip();
    }

    @Override
    public void startAction(MouseEvent e) {

    }

    @Override
    public void endAction(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        currentFigure = drawingModel.getFigureAt(e.getPoint());
        if(currentFigure != null)        {
            currentFigure.setSelected(!currentFigure.isSelected());
            drawingModel.updateSelection();
        }
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

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }
}

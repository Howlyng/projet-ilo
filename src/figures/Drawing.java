package figures;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Stream;

import figures.enums.FigureType;
import figures.enums.LineType;
import filters.FigureFilter;
import filters.FigureFilters;
import history.Memento;
import history.Originator;
import utils.PaintFactory;
import utils.Signature;
import utils.StrokeFactory;

/**
 * Classe contenant l'ensemble des figures à dessiner (LE MODELE)
 * @author davidroussel
 */
public class Drawing extends Observable implements Originator<Figure>, Signature
{
	/**
	 * Liste des figures à dessiner (protected pour que les classes du même
	 * package puissent y accéder)
	 */
	protected Vector<Figure> figures;

	/**
	 * Liste triée des indices (uniques) des figures sélectionnées de
	 * {@link #figures}.
	 * On peut savoir si une {@link Figure} est sélectionnée en l'interrogeant
	 * avec sa méthode {@link Figure#isSelected()}, mais on gardera les indices
	 * des figures sélectionnées dans {@link #selectionIndex} pour aller plus
	 * vite sur les opérations qui concernent uniquement les figures
	 * sélectionnées
	 */
	protected SortedSet<Integer> selectionIndex;

	/**
	 * Figure située sous le curseur.
	 * Déterminé par {@link #getFigureAt(Point2D)}
	 */
	private Figure selectedFigure;

	/**
	 * Le type de figure à créer (pour la prochaine figure)
	 */
	private FigureType type;

	/**
	 * La couleur de remplissage courante (pour la prochaine figure)
	 */
	private Paint fillPaint;

	/**
	 * La couleur de trait courante (pour la prochaine figure)
	 */
	private Paint edgePaint;

	/**
	 * La largeur de trait courante (pour la prochaine figure)
	 */
	private float edgeWidth;

	/**
	 * Le type de trait courant (sans trait, trait plein, trait pointillé,
	 * pour la prochaine figure)
	 */
	private LineType edgeType;

	/**
	 * Le type de trait en fonction de {@link #type} et
	 * {@link #edgeWidth}
	 */
	private BasicStroke stroke;

	/**
	 * Etat de filtrage des figures dans le flux de figures fournit par
	 * {@link #stream()}
	 * Lorsque {@link #filtering} est true le dessin des figures est filtré
	 * par l'ensemble des filtres présents dans {@link #shapeFilters},
	 * {@link #fillColorFilter}, {@link #edgeColorFilter} et
	 * {@link #lineFilters}.
	 * Lorsque {@link #filtering} est false, toutes les figures sont fournies
	 * dans le flux des figures.
	 * @see #stream()
	 */
	private boolean filtering;

	/**
	 * Filtres à appliquer au flux des figures pour sélectionner les types
	 * de figures à afficher
	 * @see #stream()
	 */
	private FigureFilters<FigureType> shapeFilters;

	/**
	 * Filtre à appliquer au flux des figures pour sélectionner les figures
	 * ayant une couleur particulière de remplissage
	 * @see FillColorFilter
	 */
	private FigureFilter<Paint> fillColorFilter;

	/**
	 * Filtre à appliquer au flux des figures pour sélectionner les figures
	 * ayant une couleur particulière de trait
	 * @see EdgeColorFilter
	 */
	private FigureFilter<Paint> edgeColorFilter;

	/**
	 * Filtres à applique au flux des figures pour sélectionner les figures
	 * ayant un type particulier de lignes
	 * @see LineFilter
	 */
	private FigureFilters<LineType> lineFilters;

	/**
	 * Constructeur de modèle de dessin
	 */
	public Drawing()
	{
		figures = new Vector<Figure>();
		selectionIndex = new TreeSet<Integer>(Integer::compare);
		shapeFilters = new FigureFilters<FigureType>();
		lineFilters = new FigureFilters<LineType>();
		edgeType = LineType.SOLID;
		stroke = StrokeFactory.getStroke(edgeType, edgeWidth);
		filtering = false;
		fillPaint = null;
		edgePaint = null;
		edgeWidth = 1.0f;
		selectedFigure = null;
		System.out.println("Dessin créé.");
	}

	/**
	 * Nettoyage avant destruction
	 */
	@Override
	protected void finalize()
	{
		figures.clear();
		figures = null;
		selectionIndex.clear();
		selectionIndex = null;
		fillPaint = null;
		edgePaint = null;
		stroke = null;
		edgeType = null;
		shapeFilters.clear();
		shapeFilters = null;
		lineFilters.clear();
		lineFilters = null;
	}

	/**
	 * Mise à jour du ou des {@link Observer} qui observent ce modèle. On place
	 * le modèle dans un état "changé" puis on notifie les observateurs.
	 */
	public void update()
	{
		setChanged();
		notifyObservers(); // pour que les observateurs soient mis à jour
	}

	/**
	 * Accesseur du type de figure à générer
	 * @return le type de figure sélectionné
	 */
	public FigureType getFigureType()
	{
		return type;
	}

	/**
	 * Mise en place d'un nouveau type de figure à générer
	 * @param type le nouveau type de figure
	 */
	public void setFigureType(FigureType type)
	{
		this.type = type;
	}

	/**
	 * Accesseur de la couleur de remplissage courante des figures
	 * @return la couleur de remplissage courante des figures
	 */
	public Paint getFillpaint()
	{
		return fillPaint;
	}

	/**
	 * Mise en place d'une nouvelle couleur de remplissage
	 * @param fillPaint la nouvelle couleur de remplissage
	 */
	public void setFillPaint(Paint fillPaint)
	{
		this.fillPaint = fillPaint;
	}

	/**
	 * Accesseur de la couleur de trait courante des figures
	 * @return la couleur de remplissage courante des figures
	 */
	public Paint getEdgePaint()
	{
		return edgePaint;
	}

	/**
	 * Mise en place d'une nouvelle couleur de trait
	 * @param edgePaint la nouvelle couleur de trait
	 */
	public void setEdgePaint(Paint edgePaint)
	{
		this.edgePaint = edgePaint;
	}

	/**
	 * Accesseur du trait courant des figures
	 * @return le trait courant des figures
	 */
	public BasicStroke getStroke()
	{
		return stroke;
	}

	/**
	 * Accesseur de la largeur de ligne
	 * @return la largeur de ligne courante
	 */
	public float getEdgeWidth()
	{
		return edgeWidth;
	}

	/**
	 * Mise en place d'un nouvelle épaisseur de trait
	 * @param width la nouvelle épaisseur de trait
	 */
	public void setEdgeWidth(float width)
	{
		edgeWidth = width;
		stroke = StrokeFactory.getStroke(edgeType, edgeWidth);
	}

	/**
	 * Accesseur du type de trait
	 * @return le type de trait
	 */
	public LineType getEdgeType()
	{
		return edgeType;
	}

	/**
	 * Mise en place d'un nouvel état de ligne pointillée
	 * @param type le nouveau type de ligne
	 */
	public void setEdgeType(LineType type)
	{
		edgeType = type;
		stroke = StrokeFactory.getStroke(edgeType, edgeWidth);
	}

	/**
	 * Initialisation d'une figure de type {@link #type} au point p et ajout de
	 * cette figure à la liste des {@link #figures}
	 * @param p le point où initialiser la figure
	 * @return la nouvelle figure créée à x et y avec les paramètres courants
	 */
	public Figure initiateFigure(Point2D p) {
		fillPaint = PaintFactory.getPaint(fillPaint);
		edgePaint = PaintFactory.getPaint(edgePaint);
		stroke = StrokeFactory.getStroke(edgeType, edgeWidth);

		Figure newFigure = type.getFigure(stroke, edgePaint, fillPaint, p);

		if (newFigure != null) {
			figures.add(newFigure);
		}

		/* Notification des observers */

		update();
		return newFigure;
	}


	/**
	 * Obtention de la dernière figure (implicitement celle qui est en cours de
	 * dessin)
	 * @return la dernière figure du dessin
	 */
	public Figure getLastFigure()
	{
		if (!figures.isEmpty()){
			return figures.get(figures.size()-1);
		}
		else{
			System.out.println("Pas de dernière figure.");
			return null;
		}
	}

	/**
	 * Obtention de la dernière figure contenant le point p.
	 * @param p le point sous lequel on cherche une figure
	 * @return une référence vers la dernière figure contenant le point p ou à
	 * défaut null.
	 */
	public Figure getFigureAt(Point2D p)
	{
		selectedFigure = null;
		stream().forEach((Figure figure) -> {
			if (figure.contains(p)){
				selectedFigure = figure;
			}
		});
		return selectedFigure;
	}

	/**
	 * Retrait de la dernière figure.
	 * Utile pour retirer une figure de taille nulle lorsque l'on clique
	 * dans la zone de dessin
	 * @post le modèle de dessin a été mis à jour (update)
	 */
	public void removeLastFigure()
	{
		if(!figures.isEmpty()){
			figures.remove(figures.size()-1);
			update();
		}
	}

	/**
	 * Effacement de toutes les figures (sera déclenché par une action clear)
	 * @post le modèle de dessin a été mis à jour
	 */
	public void clear()
	{
		if(!figures.isEmpty()){
			figures.clear();
			update();
		}
	}

	/**
	 * Accesseur de l'état de filtrage
	 * @return l'état courant de filtrage
	 */
	public boolean getFiltering()
	{
		return filtering;
	}

	/**
	 * Changement d'état du filtrage
	 * @param filtering le nouveau statut de filtrage
	 * @post le modèle de dessin a été mis à jour
	 */
	public void setFiltering(boolean filtering)
	{
		this.filtering = filtering;
		update();
	}

	/**
	 * Ajout d'un filtre pour filtrer les types de figures
	 * @param filter le filtre à ajouter
	 * @return true si le filtre n'était pas déjà présent dans l'ensemble des
	 * filtres fitrant les types de figures, false sinon
	 * @post si le filtre a été ajouté, une mise à jour est déclenchée
	 */
	public boolean addShapeFilter(FigureFilter<FigureType> filter)
	{
		boolean isok = shapeFilters.add(filter);
		if(isok){
			update();
		}
		return isok;
	}

	/**
	 * Retrait d'un filtre filtrant les types de figures
	 * @param filter le filtre à retirer
	 * @return true si le filtre faisait partie des filtres filtrant les types
	 * de figure et a été retiré, false sinon.
	 * @post si le filtre a été retiré, une mise à jour est déclenchée
	 */
	public boolean removeShapeFilter(FigureFilter<FigureType> filter)
	{
		boolean isremoved = shapeFilters.remove(filter);
		if(isremoved){
			update();
		}
		return isremoved;
	}

	/**
	 * Mise en place du filtre de couleur de remplissage
	 * @param filter le filtre de couleur de remplissage à appliquer
	 * @post le {@link #fillColorFilter} est mis en place et une mise à jour
	 * est déclenchée
	 */
	public void setFillColorFilter(FigureFilter<Paint> filter)
	{
		fillColorFilter = filter;
		update();
	}

	/**
	 * Mise en place du filtre de couleur de trait
	 * @param filter le filtre de couleur de trait à appliquer
	 * @post le #edgeColorFilter est mis en place et une mise à jour
	 * est déclenchée
	 */
	public void setEdgeColorFilter(FigureFilter<Paint> filter)
	{
		edgeColorFilter = filter;
		update();
	}

	/**
	 * Ajout d'un filtre pour filtrer les types de ligne des figures
	 * @param filter le filtre à ajouter
	 * @return true si le filtre n'était pas déjà présent dans l'ensemble des
	 * filtres fitrant les types de lignes, false sinon
	 * @post si le filtre a été ajouté, une mise à jour est déclenchée
	 */
	public boolean addLineFilter(FigureFilter<LineType> filter)
	{
		boolean isadded = lineFilters.add(filter);
		if(isadded){
			update();
		}
		return isadded;

	}

	/**
	 * Retrait d'un filtre filtrant les types de lignes
	 * @param filter le filtre à retirer
	 * @return true si le filtre faisait partie des filtres filtrant les types
	 * de lignes et a été retiré, false sinon.
	 * @post si le filtre a éré retiré, une mise à jour est déclenchée
	 */
	public boolean removeLineFilter(FigureFilter<LineType> filter)
	{
		boolean isremoved = lineFilters.add(filter);
		if(isremoved){
			update();
		}
		return isremoved;
	}

	/**
	 * Remise à l'état non sélectionné de toutes les figures
	 */
	public void clearSelection()
	{
		for (Iterator<Figure> el = figures.iterator(); el.hasNext();)
		{
			Figure figure = el.next();
			if (figure.isSelected()){
				figure.setSelected(false);
			}
		}
	}

	/**
	 * Mise à jour des indices des figures sélectionnées dans
	 * {@link #selectionIndex} en parcourant l'ensemble des {@link #figures}
	 */
	public void updateSelection()
	{
		selectionIndex.clear();
		stream().forEach((Figure figure) -> {
			if(figure.isSelected()){
				int indice = figures.indexOf(figure);
				selectionIndex.add(new Integer(indice));
			}
	});
		System.out.println(getClassName() + "::" + getMethodName()
		    + " Update Selection = " + selectionIndex);
		update();
	}

	/**
	 * Indique s'il existe des figures sélectionnées
	 * @return true s'il y a des figures sélectionnées
	 */
	public boolean hasSelection()
	{
		return selectionIndex.size()>= 1;
	}

	/**
	 * Destruction des figures sélectionnées.
	 * Et incidemment nettoyage de {@link #selectionIndex}
	 * @post Si au moins une figure a été retirée une mise à jour est déclenchée
	 */
	public void deleteSelected()
	{
		while (hasSelection()){
			Integer indice = selectionIndex.last();
			figures.removeElementAt(indice.intValue());
			selectionIndex.remove(indice);
		}
		clearSelection();
		update();
	}

	/**
	 * Applique un style particulier aux figure sélectionnées
	 * @param fill la couleur de remplissage à applique aux figures sélectionnées
	 * @param edge la couleur de trait à appliquer aux figures sélectionnées
	 * @param stroke le style de trait à appliquer aux figures sélectionnées
	 */
	public void applyStyleToSelected(Paint fill, Paint edge, BasicStroke stroke)
	{
		for (Iterator<Integer> figuresSel = selectionIndex.iterator(); figuresSel.hasNext();)
		{
			try{
				Figure figure = figures.get(figuresSel.next());
				if(fill != null){
					figure.setFillPaint(fill);
				}
				if (edge != null){
					figure.setEdgePaint(edge);
				}
				if(stroke != null){
					figure.setStroke(stroke);
				}
			}
			catch(ArrayIndexOutOfBoundsException e){
				System.err.println(getClass().getSimpleName() + "applyStyleToSelected : erreur");
		}
		}
	}

	/**
	 * Déplacement des figures sélectionnées en haut de la liste des figures.
	 * En conservant l'ordre des figures sélectionnées
	 * @post Si des figures ont été déplacées le modèle doit être mis à jour
	 */
	public void moveSelectedUp()
	{
		Vector<Figure> collection = new Vector<>();
		for(Iterator<Integer> it = selectionIndex.iterator(); it.hasNext();){
			collection.add(figures.get(it.next()));
		}
		for(int i=0; i<figures.size();i++){
			if(!selectionIndex.contains(Integer.valueOf(i))){
				collection.add(figures.get(i));
			}
		}
		figures.clear();
		figures = collection;
		updateSelection();
	}

	/**
	 * Déplacement des figures sélectionnées en bas de la liste des figures.
	 * En conservant l'ordre des figures sélectionnées
	 */
	public void moveSelectedDown()
	{
		Vector<Figure> collection = new Vector<>();
		for(int i=0; i<figures.size();i++){
			if(!selectionIndex.contains(Integer.valueOf(i))){
				collection.add(figures.get(i));
			}
		}
		for(Iterator<Integer> it = selectionIndex.iterator(); it.hasNext();){
			collection.add(figures.get(it.next()));
		}
		figures.clear();
		figures = collection;
		updateSelection();
	}

	/**
	 * Accès aux figures dans un stream afin que l'on puisse y appliquer
	 * de filtres
	 * @return le flux des figures éventuellement filtrés par les différents
	 * filtres
	 */
	public Stream<Figure> stream()
	{
		Stream<Figure> figuresStream = figures.stream();
		if (filtering)
		{
			if(shapeFilters.size() > 0){
				figuresStream = figuresStream.filter(shapeFilters);
			}

			if(fillColorFilter != null){
				figuresStream = figuresStream.filter(fillColorFilter);
			}

			if(edgeColorFilter != null){
				figuresStream = figuresStream.filter(edgeColorFilter);
			}

			if(lineFilters.size() > 0){
				figuresStream = figuresStream.filter(lineFilters);
			}
		}

		return figuresStream;
	}

	/* (non-Javadoc)
	 * @see history.Originator#createMemento()
	 */
	@Override
	public Memento<Figure> createMemento()
	{
		return new Memento<Figure>(figures);
	}

	/* (non-Javadoc)
	 * @see history.Originator#setMemento(history.Memento)
	 */
	@Override
	public void setMemento(Memento<Figure> memento)
	{
		if(memento!=null){
			List<Figure> sauvegarde = memento.getState();
			figures.clear();
			for(Figure figure : sauvegarde){
				figures.add(figure.clone());
			}
			update();
		}
		else{
			System.err.println("setMemento : erreur");
		}
	}

}


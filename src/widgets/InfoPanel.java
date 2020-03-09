package widgets;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;

import figures.Figure;
import figures.enums.FigureType;
import figures.enums.LineType;
import utils.IconFactory;
import utils.PaintFactory;

public class InfoPanel extends JPanel
{
	/**
	 * Serializable class must have a serial version UID
	 */
	private static final long serialVersionUID = -5637980381468626303L;

	/**
	 * Une chaine vide pour remplir les champs lorsque la souris n'est au dessus
	 * d'aucune figure
	 */
	private static final String emptyString = new String();

	/**
	 * Une icône vide pour remplir les chanmps avec icône lorsque la souris
	 * n'est au dessus d'aucune figure
	 */
	private static final ImageIcon emptyIcon = IconFactory.getIcon("None");

	/**
	 * Le formatteur à utiliser pour formater les coordonnés
	 */
	private final static DecimalFormat coordFormat = new DecimalFormat("000");

	/**
	 * La map contenant les différentes icônes des types de figures
	 */
	private Map<FigureType, ImageIcon> figureIcons;

	/**
	 * Map contenant les icônes relatives aux différentes couleurs (de contour
	 * ou de remplissage)
	 */
	private Map<Paint, ImageIcon> paintIcons;

	/**
	 * Map contenant les icônes relatives au différents types de traits de
	 * contour
	 */
	private Map<LineType, ImageIcon> lineTypeIcons;
	private JLabel lblFigureName;
	private JLabel lblTypeicon;
	private JLabel lblFillcolor;
	private JLabel lblEdgecolor;
	private JLabel lblStroketype;
	private JLabel lblTlx;
	private JLabel lblTly;
	private JLabel lblBrx;
	private JLabel lblBry;
	private JLabel lblDx;
	private JLabel lblDy;
	private JLabel lblCx;
	private JLabel lblCy;






	/**
	 * Create the panel.
	 */
	public InfoPanel()
	{
		// --------------------------------------------------------------------
		// Initialisation des maps
		// --------------------------------------------------------------------
		figureIcons = new HashMap<FigureType, ImageIcon>();
		for (int i = 0; i < FigureType.NbFigureTypes; i++)
		{
			FigureType type = FigureType.fromInteger(i);
			figureIcons.put(type, IconFactory.getIcon(type.toString()));
		}

		paintIcons = new HashMap<Paint, ImageIcon>();
		String[] colorStrings = { "Black", "Blue", "Cyan", "Green", "Magenta",
		    "None", "Orange", "Others", "Red", "White", "Yellow" };

		for (int i = 0; i < colorStrings.length; i++)
		{
			Paint paint = PaintFactory.getPaint(colorStrings[i]);
			if (paint != null)
			{
				paintIcons.put(paint, IconFactory.getIcon(colorStrings[i]));
			}
		}

		lineTypeIcons = new HashMap<LineType, ImageIcon>();
		for (int i = 0; i < LineType.NbLineTypes; i++)
		{
			LineType type = LineType.fromInteger(i);
			lineTypeIcons.put(type, IconFactory.getIcon(type.toString()));
		}

		// --------------------------------------------------------------------
		// Création de l'UI (j'ai demandé de l'aide pour cette partie)
		// --------------------------------------------------------------------
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {60, 50, 50};
		gridBagLayout.rowHeights = new int[] {20, 20, 20, 20, 15, 15, 15, 15, 15};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		lblFigureName = new JLabel("Figure");
		JLabel lblType = new JLabel("type");
		lblTypeicon = new JLabel(IconFactory.getIcon("Ellipse"));
		JLabel lblFill = new JLabel("fill");
		lblFillcolor = new JLabel(IconFactory.getIcon("Black"));
		JLabel lblEdge = new JLabel("stroke");
		lblEdgecolor = new JLabel(IconFactory.getIcon("Black"));
		lblEdgecolor = new JLabel(IconFactory.getIcon("Black"));
		lblStroketype = new JLabel(IconFactory.getIcon("Solid"));
		JLabel lblX = new JLabel("x");
		JLabel lblY = new JLabel("y");
		JLabel lblTopLeft = new JLabel("top left");
		lblTlx = new JLabel("0");
		lblTly = new JLabel("0");
		JLabel lblBottomRight = new JLabel("bottom right");
		lblBrx = new JLabel("0");
		lblBry = new JLabel("0");
		JLabel lblDimensions = new JLabel("dimensions");
		lblDx = new JLabel("0");
		lblDy = new JLabel("0");
		JLabel lblCenter = new JLabel("center");
		lblCx = new JLabel("0");
		lblCy = new JLabel("0");

		//positionnement des labels

		lblFigureName.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblFigureName = new GridBagConstraints();
		gbc_lblFigureName.insets = new Insets(5, 5, 5, 0);
		gbc_lblFigureName.gridwidth = 5;
		gbc_lblFigureName.gridx = 0;
		gbc_lblFigureName.gridy = 0;

		GridBagConstraints gbc_lblType = new GridBagConstraints();
		gbc_lblType.anchor = GridBagConstraints.CENTER;
		gbc_lblType.gridx = 0;
		gbc_lblType.gridy = 1;

		lblTypeicon.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTypeicon = new GridBagConstraints();
		gbc_lblTypeicon.gridx = 2;
		gbc_lblTypeicon.gridy = 1;

		GridBagConstraints gbc_lblFill = new GridBagConstraints();
		gbc_lblFill.anchor = GridBagConstraints.CENTER;
		gbc_lblFill.gridx = 0;
		gbc_lblFill.gridy = 2;

		GridBagConstraints gbc_lblFillcolor = new GridBagConstraints();
		gbc_lblFillcolor.anchor = GridBagConstraints.CENTER;
		gbc_lblFillcolor.gridx = 2;
		gbc_lblFillcolor.gridy = 2;

		GridBagConstraints gbc_lblEdge = new GridBagConstraints();
		gbc_lblEdge.anchor = GridBagConstraints.WEST;
		gbc_lblEdge.gridx = 0;
		gbc_lblEdge.gridy = 3;

		lblEdgecolor.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEdgecolor = new GridBagConstraints();
		gbc_lblEdgecolor.gridx = 1;
		gbc_lblEdgecolor.gridy = 3;

		lblStroketype.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblStroketype = new GridBagConstraints();
		gbc_lblStroketype.gridx = 3;
		gbc_lblStroketype.gridy = 3;

		GridBagConstraints gbc_lblX = new GridBagConstraints();
		gbc_lblX.gridx = 1;
		gbc_lblX.gridy = 4;

		GridBagConstraints gbc_lblY = new GridBagConstraints();
		gbc_lblY.gridx = 3;
		gbc_lblY.gridy = 4;

		GridBagConstraints gbc_lblTlx = new GridBagConstraints();
		gbc_lblTlx.gridx = 1;
		gbc_lblTlx.gridy = 5;

		GridBagConstraints gbc_lblTly = new GridBagConstraints();
		gbc_lblTly.gridx = 3;
		gbc_lblTly.gridy = 5;

		GridBagConstraints gbc_lblBrx = new GridBagConstraints();
		gbc_lblBrx.gridx = 1;
		gbc_lblBrx.gridy = 6;

		GridBagConstraints gbc_lblBry = new GridBagConstraints();
		gbc_lblBry.gridx = 3;
		gbc_lblBry.gridy = 6;

		GridBagConstraints gbc_lblDx = new GridBagConstraints();
		gbc_lblDx.gridx = 1;
		gbc_lblDx.gridy = 7;

		GridBagConstraints gbc_lblDy = new GridBagConstraints();
		gbc_lblDy.gridx = 3;
		gbc_lblDy.gridy = 7;

		GridBagConstraints gbc_lblCx = new GridBagConstraints();
		gbc_lblCx.gridx = 1;
		gbc_lblCx.gridy = 8;

		GridBagConstraints gbc_lblCy = new GridBagConstraints();
		gbc_lblCy.gridx = 3;
		gbc_lblCy.gridy = 8;

		add(lblFigureName, gbc_lblFigureName);
		add(lblType, gbc_lblType);
		add(lblTypeicon, gbc_lblTypeicon);
		add(lblFillcolor,gbc_lblFillcolor);
		add(lblEdgecolor,gbc_lblEdgecolor);
		add(lblFill, gbc_lblFill);
		add(lblBry,gbc_lblBry);
		add(lblEdge,gbc_lblEdge);
		add(lblStroketype,gbc_lblStroketype);
		add(lblX, gbc_lblX);
		add(lblY,gbc_lblY);
		add(lblTlx,gbc_lblTlx);
		add(lblTly,gbc_lblTly);
		add(lblBrx,gbc_lblBrx);
		add(lblDx,gbc_lblDx);
		add(lblDy,gbc_lblDy);
		add(lblCx,gbc_lblCx);
		add(lblCy,gbc_lblCy);


		resetLabels();

	}

	/**
	 * Mise à jour de tous les labels avec les informations de figure
	 * @param figure la figure dont il faut extraire les informations
	 */
	public void updateLabels(Figure figure)
	{
		//Info de la figure : nom et icone
		lblFigureName.setText(figure.getClassName());
		lblTypeicon.setIcon(figureIcons.get( figure.getType() ));

		//icone de couleur de remplissage
		ImageIcon fill_color_icon = paintIcons.get(figure.getFillPaint());
		if ( fill_color_icon == null) {
			fill_color_icon = IconFactory.getIcon("Others");
		}
		lblFillcolor.setIcon(fill_color_icon);

		//icone de couleur du trait
		ImageIcon edge_color_icon = paintIcons.get(figure.getEdgePaint());
		if (edge_color_icon == null) {
			edge_color_icon = IconFactory.getIcon("Others");
		}
		lblEdgecolor.setIcon(edge_color_icon);

		//icone de type de trait
		BasicStroke stroke = figure.getStroke();
		ImageIcon line_type_icon = null;
		if (stroke == null)
		{
			line_type_icon = lineTypeIcons.get(LineType.NONE);
		}
		else
		{
			float[] dashArray = stroke.getDashArray();
			if (dashArray == null)
			{
				line_type_icon = lineTypeIcons.get(LineType.SOLID);
			}
			else
			{
				line_type_icon = lineTypeIcons.get(LineType.DASHED);
			}
		}
		lblStroketype.setIcon(line_type_icon);
	}

	/**
	 * Effacement de tous les labels
	 */
	public void resetLabels()
	{
		lblFigureName.setText(emptyString);
		lblTypeicon.setIcon(emptyIcon);
		lblFillcolor.setIcon(emptyIcon);
		lblEdgecolor.setIcon(emptyIcon);
		lblStroketype.setIcon(emptyIcon);
		lblTlx.setText(emptyString);
		lblTly.setText(emptyString);
		lblBrx.setText(emptyString);
		lblBry.setText(emptyString);
		lblDx.setText(emptyString);
		lblDy.setText(emptyString);
		lblCx.setText(emptyString);
		lblCy.setText(emptyString);
	}
}

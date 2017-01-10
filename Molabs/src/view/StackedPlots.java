package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.text.DateFormat;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import view.ExamplePanel;
import de.erichseifert.gral.graphics.DrawableContainer;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.layout.TableLayout;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.areas.AreaRenderer;
import de.erichseifert.gral.plots.areas.DefaultAreaRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

public class StackedPlots extends JPanel {

	@SuppressWarnings("unchecked")
	public StackedPlots(DataTable data) {
		
		super(new BorderLayout());
		setPreferredSize(new Dimension(200, 180));
		setBackground(Color.WHITE);
		
		
		


		// Create and format lower plot
		XYPlot plot = new XYPlot(data);
		Color color = new Color( 55, 170, 200);
		PointRenderer points = plot.getPointRenderers(data).get(0);
		points.setColor(color);
		points.setShape(new Ellipse2D.Double(-3, -3, 6, 6));
		LineRenderer lineLower = new DefaultLineRenderer2D();
		lineLower.setStroke(new BasicStroke(2f));
		lineLower.setGap(1.0);
		lineLower.setColor(color);
		plot.setLineRenderers(data, lineLower);
		AreaRenderer areaUpper = new DefaultAreaRenderer2D();
		areaUpper.setColor(GraphicsUtils.deriveWithAlpha(color, 64));
		plot.setAreaRenderers(data, areaUpper);
		plot.setInsets(new Insets2D.Double(20.0, 50.0, 45.0, 20.0));
		
		
		plot.getAxisRenderer(XYPlot.AXIS_X).setLabelDistance(0);
		plot.getAxisRenderer(XYPlot.AXIS_X).setTickLabelDistance(0);
		plot.getAxisRenderer(XYPlot.AXIS_X).getLabel().setText("Time");
		plot.getAxisRenderer(XYPlot.AXIS_Y).setLabelDistance(1);
		plot.getAxisRenderer(XYPlot.AXIS_Y).setTickLabelDistance(0.5);
		plot.getAxisRenderer(XYPlot.AXIS_Y).getLabel().setText("Concentration (mg/L)");
		DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		plot.getAxisRenderer(XYPlot.AXIS_X).setTickLabelFormat(dateFormat);
		
		DrawableContainer plots = new DrawableContainer(new TableLayout(1));
		plots.add(plot);

		InteractivePanel panel = new InteractivePanel(plots);
		add(panel);
	}

}

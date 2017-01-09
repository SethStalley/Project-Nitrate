package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
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
	/** Version id for serialization. */
	private static final long serialVersionUID = 6832343098989019088L;

	/** Instance to generate random data values. */
	private static final Random random = new Random();

	@SuppressWarnings("unchecked")
	public StackedPlots() {
		
		super(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		setBackground(Color.WHITE);
		
		// Generate data
		DataTable data = new DataTable(Double.class, Double.class);
		double x=0.0, y=0.0;
		for (x=0.0; x<100.0; x+=2.0) {
			y += 10.0*random.nextGaussian();
			data.add(x, Math.abs(y));
		}

		// Create and format upper plot
		XYPlot plotUpper = new XYPlot(data);
		Color colorUpper = new Color( 55, 170, 200);
		plotUpper.setPointRenderers(data, null);
		LineRenderer lineUpper = new DefaultLineRenderer2D();
		lineUpper.setColor(colorUpper);
		plotUpper.setLineRenderers(data, lineUpper);
		AreaRenderer areaUpper = new DefaultAreaRenderer2D();
		areaUpper.setColor(GraphicsUtils.deriveWithAlpha(colorUpper, 64));
		plotUpper.setAreaRenderers(data, areaUpper);
		plotUpper.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));

		// Create and format lower plot
		XYPlot plotLower = new XYPlot(data);
		Color colorLower = new Color( 55, 170, 200);
		PointRenderer pointsLower = plotLower.getPointRenderers(data).get(0);
		pointsLower.setColor(colorLower);
		pointsLower.setShape(new Ellipse2D.Double(-3, -3, 6, 6));
		LineRenderer lineLower = new DefaultLineRenderer2D();
		lineLower.setStroke(new BasicStroke(2f));
		lineLower.setGap(1.0);
		lineLower.setColor(colorLower);
		plotLower.setLineRenderers(data, lineLower);
		plotLower.setInsets(new Insets2D.Double(20.0, 50.0, 40.0, 20.0));

		DrawableContainer plots = new DrawableContainer(new TableLayout(1));
		plots.add(plotUpper);
		plots.add(plotLower);

		// Connect the two plots, i.e. user (mouse) actions affect both plots
		plotUpper.getNavigator().connect(plotLower.getNavigator());

		InteractivePanel panel = new InteractivePanel(plots);
		add(panel);
	}

}

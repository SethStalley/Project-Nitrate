package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.LinearGradientPaint;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.Location;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.BarPlot.BarRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;

public class ConcentrationTimeGraph extends JPanel {
	public ConcentrationTimeGraph() {
		// Create example data
		DataTable data = new DataTable(Double.class, Integer.class, String.class);
		data.add(0.1,  1, "January");
		data.add(0.2,  3, "February");
		data.add(0.3, -2, "March");
		data.add(0.4,  6, "April");
		data.add(0.5, -4, "May");
		data.add(0.6,  8, "June");
		data.add(0.7,  9, "July");
		data.add(0.8, 11, "August");

		// Create new bar plot
		BarPlot plot = new BarPlot(data);

		// Format plot
		plot.setInsets(new Insets2D.Double(40.0, 40.0, 40.0, 40.0));
		plot.setBarWidth(0.075);

		// Format bars
		BarRenderer pointRenderer = (BarRenderer) plot.getPointRenderers(data).get(0);
		pointRenderer.setColor(
			new LinearGradientPaint(0f,0f, 0f,1f,
					new float[] { 0.0f, 1.0f },
					new Color[] { new Color( 55, 170, 200), GraphicsUtils.deriveBrighter(new Color( 55, 170, 200)) }
			)
		);
		pointRenderer.setBorderStroke(new BasicStroke(3f));
		pointRenderer.setBorderColor(
			new LinearGradientPaint(0f,0f, 0f,1f,
					new float[] { 0.0f, 1.0f },
					new Color[] { GraphicsUtils.deriveBrighter(new Color( 55, 170, 200)), new Color( 55, 170, 200) }
			)
		);
		pointRenderer.setValueVisible(true);
		pointRenderer.setValueColumn(2);
		pointRenderer.setValueLocation(Location.CENTER);
		pointRenderer.setValueColor(GraphicsUtils.deriveDarker(new Color( 55, 170, 200)));
		pointRenderer.setValueFont(Font.decode(null).deriveFont(Font.BOLD));
		
		add(new InteractivePanel(plot));

	}
}

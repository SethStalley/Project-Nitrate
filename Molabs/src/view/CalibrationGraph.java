package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.erichseifert.gral.data.DataTable;
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
import model.Calibration;
import values.Strings;

public class CalibrationGraph extends JPanel{

	public CalibrationGraph(Calibration calibration) {
		// receives (x,y)  (concentration, absorbance)
		super(new BorderLayout());
		setPreferredSize(new Dimension(488, 261));
		setBackground(Color.WHITE);
		
		// Generate data
		DataTable data = new DataTable(Double.class, Double.class);
		DataTable data2 = new DataTable(Double.class, Double.class);
		if (calibration != null){
			for(Double[] c : calibration.getXYValues()){
				data.add(c[0],c[1]);
			}
			
			Double y1 = (calibration.getSlope() * Strings.XVALUEMIN) + calibration.getIntercept();
			Double y2 = (calibration.getSlope() * Strings.XVALUEMAX) + calibration.getIntercept();
			data2.add(Strings.XVALUEMIN, y1);// first point of linear ecuation
			data2.add(Strings.XVALUEMAX, y2);// second point of linear ecuation
			
		}
		else{
			data.add(0.0,0.0);// add data by default
			data.add(1.0,1.0);
			data2.add(0.0,0.0);
			data2.add(1.0,1.0);
		}

		// Create and format upper plot
		XYPlot plotUpper = new XYPlot(data,data2);
		plotUpper.setInsets(new Insets2D.Double(0.0, 55.0, 55.0, 30.0));

		
		// axis
		plotUpper.getAxisRenderer(XYPlot.AXIS_X).getLabel().setText("Concentration (mg/L)");
		plotUpper.getAxisRenderer(XYPlot.AXIS_Y).getLabel().setText("ABS (U.A.)");
		
		
		
		LineRenderer lines = new DefaultLineRenderer2D();
        Color color = new Color(1.0f, 0.3f, 0.0f);
        lines.setColor(color); 
        plotUpper.setLineRenderers(data2, lines);
        




		InteractivePanel panel = new InteractivePanel(plotUpper);
		add(panel);
	}

}

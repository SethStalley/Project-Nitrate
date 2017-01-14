package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO; 
import java.awt.*; 
import java.awt.image.BufferedImage; 
import java.io.File; 
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.internal.bind.util.ISO8601Utils;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.graphics.Drawable;
import de.erichseifert.gral.graphics.DrawableContainer;
import de.erichseifert.gral.graphics.Insets2D;
import de.erichseifert.gral.graphics.layout.TableLayout;
import de.erichseifert.gral.io.plots.DrawableWriter;
import de.erichseifert.gral.io.plots.DrawableWriterFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.areas.AreaRenderer;
import de.erichseifert.gral.plots.areas.DefaultAreaRenderer2D;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.GraphicsUtils;
import model.Calibration;
import values.OptionErrorMessage;
import values.Strings;


public class CalibrationGraph extends JPanel{

	public CalibrationGraph(Calibration calibration) {
		// receives (x,y)  (concentration, absorbance)
		super(new BorderLayout());
		setPreferredSize(new Dimension(48, 245));
		setBackground(Color.BLUE);
		
		// Generate data
		DataTable data = new DataTable(Double.class, Double.class);
		DataTable data2 = new DataTable(Double.class, Double.class);
		if (calibration != null){
			for(Double[] c : calibration.getXYValues()){
				data.add(c[0],c[1]);
			}
			
			Double[] xLimitValues = this.getXLimitValues(calibration.getXYValues());
			
			Double y1 = (calibration.getSlope() * xLimitValues[0]) + calibration.getIntercept();
			Double y2 = (calibration.getSlope() * xLimitValues[1]) + calibration.getIntercept();
			data2.add(xLimitValues[0], y1);// first point of linear ecuation
			data2.add( xLimitValues[1], y2);// second point of linear ecuation
			
		}
		else{
			data.add(0.0,0.0);// add data by default
			data.add(1.0,1.0);
			data2.add(0.0,0.0);
		}

		// Create and format upper plot
		XYPlot plot = new XYPlot(data,data2);
		plot.setInsets(new Insets2D.Double(10.0, 65.0, 60.0, 30.0));
		plot.setBackground(Color.WHITE);
		
		if (calibration != null){

			LineRenderer lines = new DefaultLineRenderer2D();
	        Color color = new Color(1.0f, 0.3f, 0.0f);
	        lines.setColor(color); 
	        plot.setLineRenderers(data2, lines);
	        plot.setPointRenderers(data2, null);
		}
		else{
			plot.setPointRenderers(data, null);
			plot.setPointRenderers(data2, null);
		}

		
		// axis
		plot.getAxisRenderer(XYPlot.AXIS_X).getLabel().setText("Concentration (mg/L)");
		plot.getAxisRenderer(XYPlot.AXIS_Y).getLabel().setText("ABS (U.A.)");
	

		InteractivePanel panel = new InteractivePanel(plot);
		panel.setBackground(Color.BLACK);
		JPopupMenu pop = new JPopupMenu();
		JMenuItem item = new JMenuItem("Export Graph");
		item.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save(plot);
				
			}
		});
		pop.add(item);
		panel.setComponentPopupMenu(pop);
		add(panel);
	}
	
	private Double[] getXLimitValues(ArrayList<Double[]> points){
		Double xMin = Strings.XVALUEMIN;
		Double xMax = Strings.XVALUEMAX;
		Double[] currentPoint;
		
		for (int i = 0 ; i < points.size() ; i++){
			currentPoint = points.get(i);
			if(currentPoint[0] <= xMin){
				xMin = currentPoint[0];
			}
			if(currentPoint[0] >= xMax){
				xMax = currentPoint[0];
			}
		}
		
		
		Double[] result = {xMin + (xMin * Strings.EXTRA_PORCENTAGE), xMax + (xMax * Strings.EXTRA_PORCENTAGE)};
		return result;
	}
	
	private void save(Drawable plot){
		JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("PNG", "png");
        FileNameExtensionFilter filter2 = new FileNameExtensionFilter("JPG", "JPG");
        chooser.addChoosableFileFilter(filter1);
        chooser.addChoosableFileFilter(filter2);
        chooser.setDialogTitle("Export Image");
        chooser.setAcceptAllFileFilterUsed(false);
        int option = chooser.showSaveDialog(null);
        
		if(option == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			try{
				DrawableWriter writer;
				if(chooser.getFileFilter().equals(filter2)){
					writer = DrawableWriterFactory.getInstance().get("image/jpeg");
					if (!file.toString().contains(".jpg") )
						file = new File(file.getAbsolutePath().concat(".jpg"));
				}else{
					writer = DrawableWriterFactory.getInstance().get("image/png");
					if (!file.toString().contains(".png") )
						file = new File(file.getAbsolutePath().concat(".png"));
				}
				int result = JOptionPane.YES_OPTION;
				if(file.exists())
					result = JOptionPane.showOptionDialog(null, "File already exists, overwrite?", "Warning",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
							null, OptionErrorMessage.YES_NO_ENGLISH, OptionErrorMessage.YES_NO_ENGLISH[0]);
				
				if(result == JOptionPane.YES_OPTION){
					FileOutputStream fileStream = new FileOutputStream(file);
					writer.write(plot,fileStream, plot.getWidth(), plot.getHeight());
					fileStream.close();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}


}

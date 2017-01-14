package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

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
import values.OptionErrorMessage;

public class ConcentrationTimeGraph extends JPanel {

	@SuppressWarnings("unchecked")
	public ConcentrationTimeGraph(DataTable data) {
		
		super(new BorderLayout());
		setPreferredSize(new Dimension(200, 180));
		setBackground(Color.WHITE);
		boolean init = false;
		
		if(data == null){
			data = new DataTable(Long.class, Double.class);
			data.add(System.currentTimeMillis(),new Double(0));
			data.add(System.currentTimeMillis()-10000,new Double(1));
			init = true;
		}
		


		// Create and format lower plot
		XYPlot plot = new XYPlot(data);
		if(!init){
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
		}else{
			plot.setLineRenderers(data, null);
			plot.setAreaRenderers(data, null);
		}
		
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
		plot.setBackground(Color.WHITE);
		plots.add(plot);

		InteractivePanel panel = new InteractivePanel(plots);
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

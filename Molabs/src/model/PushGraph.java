package model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import controller.Controller;
import controller.DB;
import values.Strings;
import view.CalibrationTable;

public class PushGraph extends Thread{
	

	private Controller controller;
	private CalibrationTable calibrationTable;
	private Boolean run;
	private Calibration currentCalibration;
	private boolean isObserving;

	public PushGraph(Controller controller, CalibrationTable calibrationTable) {
		this.controller = controller;
		this.calibrationTable = calibrationTable;
		run = true;
		isObserving = false;
		
	}
	
	public void run(){
		while(run){
			try{
				TimeUnit.SECONDS.sleep(Strings.PUSH_GRAPH_SLEEP);
				Date key = calibrationTable.getActiveCalibration();
				if (key != null){
					currentCalibration = controller.getCalibrationData(key);
					//System.out.println("Push graph calibration with : " + currentCalibration.getDate());
					
					DB.getInstance().updateGraph(currentCalibration.getXYValues(), "CalibrationGraph", String.valueOf(currentCalibration.getSlope()),
							String.valueOf(currentCalibration.getIntercept()), String.valueOf(currentCalibration.getPearson())
								, currentCalibration.getWavelength());
					
					if(isObserving)
						DB.getInstance().updateGraph(controller.getConcentrationGraphData(), "ConcenVsTime", "", "", "", ""); 
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}	
	}
	
	public void setIsObserving(boolean value){
		this.isObserving = value;
	}
	
	

}

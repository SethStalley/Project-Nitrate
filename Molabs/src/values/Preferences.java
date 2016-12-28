package values;

/*
 * Singleton class that stores user preferences during runtime and on load
 */
public class Preferences {

	private static Preferences instance = null;
	private String liveFolderPath;
	
	public Preferences() {
		this.liveFolderPath = "";
	}
	
	public static Preferences getInstance() {
      if(instance == null) {
         instance = new Preferences();
      }
      return instance;
   }
	
	public void setLiveFolderPath(String path) {
		this.liveFolderPath = path;
	}
	
	public String getLiveFolderPath() {
		return this.liveFolderPath;
	}
	
}

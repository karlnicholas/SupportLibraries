package examples;

import org.openstates.classes.MetadataClass;
import org.openstates.data.MetadataOverview;

/**
 * <pre>
 * This program is a very simple demonstration of this RESTful Client API.
 * It requires a ResourceBundle named OpenStates. This means a file
 * named OpenStates.properties must be found in your class path, such
 * as your resources directory. This OpenStates client reads two 
 * keys from the resource bundle, one of which is mandatory.
 * These are in the form of key=value, one on each line.
 * The keys are:
 * 
 * apikey    (Mandatory) get from OpenStates.org
 * cache     (Optional) such as c:/tmp/OpenStatesCache
 * 
 * For Example:
 * apikey=123easyasabcbabyyouandme
 * cache=c:/tmp/openstates
 * </pre>
 *
 */
public class Example {
	
	public static void main(String... args) throws Exception {
		// create a class to query
		MetadataClass metadataClass = new MetadataClass();
		
		// if you want to turn caching off, uncomment this line
//		OpenStates.setCache(false);
		
		// Metadata overview ... for all states
		MetadataOverview overview = metadataClass.overview();
		
		// print the abbreviation for the first state 
		System.out.println("The abbreviation for " + overview.get(0).name + " is " + overview.get(0).abbreviation);
	}

}

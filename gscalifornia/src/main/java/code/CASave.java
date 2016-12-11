package code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.logging.Logger;

import codesparser.Code;
import codesparser.CodeRange;
import codesparser.CodesInterface;
import codesparser.IteratorHandler;
import codesparser.Section;
import codesparser.SectionNumber;

public class CASave {
	private static final Logger logger = Logger.getLogger(CASave.class.getName());
	private static final String DEBUGFILE = null;	//"fam";
//	private static long globalsectioncount = 0;

    private int position;

	public List<File> createSerializedStatutes(File codesDir, File xmlcodes) throws Exception {
		List<File> outputFiles = new ArrayList<File>();

		CodesInterface parserInterface = new CACodes();

		File files[] = codesDir.listFiles(new FileFilter(){

			public boolean accept(File pathname) {
				if ( pathname.isDirectory()) return false;
				if (pathname.getName().toString().contains("constitution"))
					return false;
				if ( DEBUGFILE != null ) { 
					if (!pathname.getName().toString().contains(DEBUGFILE)) return false;
				}
				return true;
			}});
		
		for ( File file: files ) {
			logger.info( "Processing " + file);
			outputFiles.add(processFile(parserInterface, file, xmlcodes));
		}
		return outputFiles;
	}
		
	private File processFile(CodesInterface parserInterface, File file, File xmlcodes) throws Exception {
		CodeParser parser = new CodeParser();
		Code c = parser.parse(parserInterface, "ISO_8859_1", file);

/*
// debug code
		Section section = c.findReference( new SectionNumber("469") ).returnSection();
		File codeDetail = new File("c:/users/karl/code/pen/02001-03000/2635-2643");
		SectionParser.parseSectionFile(codeDetail, section);
// end debug code
*/

		IteratorHandler myHandler = new MyIteratorHandler(file);
		c.iterateSections(myHandler);

		File outputFile = new File(xmlcodes.toString() + "/" + c.getTitle()+".ser");
		OutputStream os = new FileOutputStream(outputFile);
		ObjectOutputStream oos = new ObjectOutputStream(os);

		oos.writeObject(c);
		
		oos.close();

		return outputFile;
		// System.out.println(c.getTitle());
	}

	public class MyIteratorHandler implements IteratorHandler {
		private String abvr;
		private String inpath;
		private String encoding;
		
		public MyIteratorHandler(File file) {
			position = 1;
			abvr = file.getName().substring(0, file.getName().indexOf('_'));
			inpath = file.getParent().toString();
			encoding = "ISO_8859_1";
		}
		
		public boolean handleSection(Section section) throws Exception {
//			SectionRange range = section.getSectionRange();
//			if (range != null) {
			CodeRange range = section.getCodeRange();
			if (range != null) {
				String strRange = range.getsNumber().getSectionNumber();
				String firstInt = new String();
				for (int i = 0, il = strRange.length(); i < il; ++i) {
					char ch = strRange.charAt(i);
					if (Character.isDigit(ch)) {
						firstInt = firstInt.concat("" + ch);
					} else {
						break;
					}
				}
				int num = Integer.parseInt(firstInt);
				num = ((num - 1) / 1000) * 1000;
				String subdir = String.format("%05d-%05d", num + 1, num + 1000);
				if ( range.geteNumber() != null && range.geteNumber().getSectionNumber() != null ) strRange = strRange + "-" + range.geteNumber();
				String strPath = new String(inpath + "/" + abvr + "/" + subdir + "/" + strRange);
				File codeDetail = new File(strPath);
				ArrayList<String> sections = SectionParser.parseSectionFile(encoding, codeDetail, section);
				// update CodeRange positions
				range.getsNumber().setPosition(position);
				appendParagraph(sections, section);
				// update CodeRange positions
				range.geteNumber().setPosition(position-1);			
			}
			return true;
		}
	}

	private void appendParagraph(
			ArrayList<String> sections,
			Section section 
		) {
//			System.out.println(sections.size());

			for ( String sect: sections ) {
//				int slen = sect.length();
//				System.out.println(sect.substring(0, slen>20?20:slen ) + " ...");
//				Element eParagraph = xmlDoc.createElement(CodeReference.SECTIONTEXT);
//				Really, the only difference is that we don't actually put any text into the XML 
//				eParagraph.appendChild(xmlDoc.createCDATASection(sect)); 
				
				SectionNumber PNumber = SectionParser.getSectionNumber(position, sect);
//				eParagraph.setAttribute(CodeReference.SECTIONNUMBER, PNumber.toString());
//				eParagraph.setAttribute(CodeReference.POSITION, Integer.toString(position));
				section.getSectionNumbers().add(PNumber);
				position++;
				// System.out.println( sect.substring(0, 20) + " ...");
//				eSection.appendChild(eParagraph);

//				globalsectioncount++;
			}

//			return eSection;

		}
	
	/*
	 * Reqired to run this to create xml files in the resources folder that describe the code hierarchy 
	 */
	public static void main(String... args) throws Exception {
		
		final class Run {
			public void run() throws Exception {


				File codesDir = new File("c:/users/karln/code");

				File xmlcodes = new File("c:/users/karln/op/SupportLibraries/gscalifornia/src/main/resources/CaliforniaStatutes");
				
				List<File> files = new CASave().createSerializedStatutes(codesDir, xmlcodes );
				
				File filePath = new File(xmlcodes.toString() + "/files" );
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
				for(File file: files) {
					bw.write(file.getName());
					bw.newLine();
				}
				bw.close();
				
			}
		}

		Run run = new Run();
		run.run();
	}

}

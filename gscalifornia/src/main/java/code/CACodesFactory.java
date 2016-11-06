package code;

import java.util.logging.Logger;

import codesparser.CodesInterface;

public class CACodesFactory {
	Logger logger = Logger.getLogger(CACodesFactory.class.getName());

	private static CodesInterface codesInterface = null;
    private CACodesFactory(){}
    private static class SingletonHelper {
        private static final CACodesFactory INSTANCE = new CACodesFactory();
    }
    public static CACodesFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }

	public synchronized CodesInterface getCodesInterface(boolean loadXMLCodes)  {
		if ( codesInterface == null ) {
			codesInterface = new CACodes();
			if ( loadXMLCodes ) {
				codesInterface.loadCodes();
			}
		}
		return codesInterface;
	}

}

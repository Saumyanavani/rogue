//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package src;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Test {
    public Test() {
    }

    public static void main(String[] args                 ) {
        String fileName = null;
        switch (args.length) {
            case 1:
                /******************************************************************
                 * note that the relative file path may depend on what IDE you are
                 * using.  You might needs to add to the beginning of the filename,
                 * e.g., filename = "src/xmlfiles/" + args[0]; worked for me in
                 * netbeans, what is here works for me from the command line in
                 * linux.
                 ******************************************************************/
                fileName = args[0];
                break;
            default:
                System.out.println("java Test <xmlfilename>");
                return;
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            // just copy this
            SAXParser saxParser = saxParserFactory.newSAXParser();
            // just copy this
            DungeonXMLHandler handler = new DungeonXMLHandler();
            // just copy this.  This will parse the xml file given by fileName
            saxParser.parse(new File(fileName), handler);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }
    }
}

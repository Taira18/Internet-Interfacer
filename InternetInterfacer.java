import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * This class provides the scaffolding to send an http request to the
 * Google Maps API, get a packet of XML data in return, and parse
 * that data into a DOM document (a tree-like structure).
 *
 * @author Michael Ida
 */
public class InternetInterfacer {

	public static void main(String[] args) throws IOException,
	ParserConfigurationException, SAXException {

		/*
		 * Set up the parameters to send a http query
		 */
		String base = "https://maps.googleapis.com/maps/api/directions/xml?";
		String origin = "origin=4680+Kalanianaole+Highway+Honolulu+96821";
		String dest = "destination=Kahala+Mall+Honolulu";
		String extras = "mode=walking";
		String key = "key=AIzaSyAbPoFI7ul8T8h_khn3r1LPBBJYatDSPIQ";
		String urlString = base + "&" + origin + "&" + dest + "&" +
				extras + "&" + key;
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("GET");

		/*
		 * Set up a buffered reader to receive the response
		 */
		BufferedReader in = new BufferedReader(
				new InputStreamReader(httpCon.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		/*
		 * Convert the response into a string and trim off leading
		 * characters which would throw the parser off
		 */
		String responseMessage = response.toString();
		responseMessage = responseMessage.trim().replaceFirst("^([\\W]+)<","<");

		/*
		 * Pass the XML string through the XML parser and convert it into
		 * a DOM document (a tree-like structure)
		 */
		DocumentBuilder db =
				DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc =
				db.parse(new InputSource(new StringReader(responseMessage)));
		/*
		 * This is where you begin.  Figure out how the Document data structure
		 * works and how to find and extract the information you want from it.
		 * Use that information to output something useful and interesting to
		 * the end-user.  The document 'doc' contains all of the data from
		 * the XML parser, and this is the object that you'll want to work
		 * with.
		 */
		Node boot = doc.getDocumentElement();
		NodeList blist = doc.getElementsByTagName("text");
		NodeList xlist = doc.getElementsByTagName("html_instructions");
		for (int i = 0; i < blist.getLength(); i++) {
            Node j = blist.item(i);
            Node k = xlist.item(i);

            if (k != null) {
                String jj = j.getTextContent();
                jj = jj.replaceAll("[<](/)?div[^>]*[>]", "");
                String kk = k.getTextContent();
                kk = kk.replaceAll("[<](/)?div[^>]*[>]", "");
                String eee = jj.replaceAll("<b>", "");
                String rrr = kk.replaceAll("<b>", "");
                String eeee = eee.replaceAll("[<](/)?div[^>][>]", "");
                String rrrr = rrr.replaceAll("[<](/)?div[^>][>]", "");

                System.out.println(eeee.replaceAll("</b>", ""));
                System.out.println(rrrr.replaceAll("</b>", ""));
		}
		}
	}
}
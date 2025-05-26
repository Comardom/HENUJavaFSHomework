package util;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;

import java.io.InputStream;
import java.net.URI;

public class SvgLoader
{
    private static final SVGUniverse universe = new SVGUniverse();

    public static SVGDiagram loadSvg(String path)
    {
        InputStream is = SvgLoader.class.getResourceAsStream(path);

        if (is == null)
        {
            System.err.println("SVG not found: " + path);
            return null;
        }

        try
        {
            URI uri = universe.loadSVG(is, path); // path 用作唯一 ID
            return universe.getDiagram(uri);
        }
        catch (Exception e)
        {
            System.err.println("Failed to load SVG: " + path);
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
            return null;
        }
    }
}

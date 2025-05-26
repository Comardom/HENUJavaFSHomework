package external;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class SvgUseExpander
{
    public static void expandUseElements(String inputSvgPath, String outputSvgPath) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new File(inputSvgPath));
        Element root = doc.getDocumentElement();

        NodeList useNodes = root.getElementsByTagName("use");
        // 注意：getElementsByTagName 返回实时列表，遍历时可能要倒序处理避免修改影响
        for (int i = useNodes.getLength() - 1; i >= 0; i--)
        {
            Element use = (Element) useNodes.item(i);
            // 取 href 属性，兼容 inkscape:href 或 xlink:href
            String href = use.getAttribute("href");
            if (href == null || href.isEmpty())
            {
                href = use.getAttribute("xlink:href");
            }
            if (href == null || href.isEmpty())
            {
                href = use.getAttribute("inkscape:href");
            }
            if (href == null || !href.startsWith("#")) continue;

            String refId = href.substring(1);
            // 根据id查找被引用元素
            Element refElement = findElementById(doc, refId);
            if (refElement == null) continue;

            // 克隆被引用元素（深拷贝）
            Node cloned = refElement.cloneNode(true);

            // 复制use的部分属性（如 transform、style）给克隆元素
            copyAttributes(use, (Element) cloned, new String[]{"transform", "style", "class"});

            // 用克隆元素替换use
            Node parent = use.getParentNode();
            parent.replaceChild(cloned, use);
        }

        // 保存修改后的SVG
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(outputSvgPath));
        transformer.transform(source, result);
    }

    // 根据id属性在文档中查找元素
    private static Element findElementById(Document doc, String id)
    {
        NodeList all = doc.getElementsByTagName("*");
        for (int i = 0; i < all.getLength(); i++)
        {
            Element e = (Element) all.item(i);
            if (id.equals(e.getAttribute("id")))
            {
                return e;
            }
        }
        return null;
    }

    // 复制指定属性，如果use里有则复制给目标元素
    private static void copyAttributes(Element source, Element target, String[] attrNames)
    {
        for (String attr : attrNames)
        {
            if (source.hasAttribute(attr))
            {
                target.setAttribute(attr, source.getAttribute(attr));
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        String input = "src//img/Boss.svg";
        String output = "src/img/Boss_expanded.svg";
        expandUseElements(input, output);
        System.out.println("SVG use元素展开完毕，保存为：" + output);
    }
}

package com.microcore;

import junit.framework.TestCase;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @author LeiZhenYang
 * @date 2018.10.26
 */
public class Dom4jXpathTest extends TestCase {

    public void testXpath() throws Exception {
        select();
    }

    private static void select() throws Exception {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read("src/main/resources/logback-spring.xml");
        List<Node> list = document.selectNodes("//appender");
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i).getText());
    }

}

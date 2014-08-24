package org.tym.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.tym.Param;
import org.tym.entity.Board;
import org.tym.entity.Section;

/**
 * 分区Section和版面Board的xml文件的读写
 * @author njutym
 *
 */
public class DocUtil {
	/*
	 * 获取所有Section
	 */
	public static List<Section> getSections() {
		List<Section> sectionList = null;
		
		File file = new File("xml/Sections.xml");
		if (!file.exists()) {
			System.out.println("File Not Found: " + "xml/Sections.xml");
			return null;
		}
		
		sectionList = new ArrayList<Section>();
		SAXReader reader = new SAXReader();
		Document document = null;
		Section sec = null;
		try {
			document = reader.read(file);				
			List<?> sections = document.getRootElement().selectNodes("Section");
	        for(Object obj: sections) {
	        	Element section = (Element)obj;
	        	//System.out.println(section.attribute("ID").getText() + "\t" + section.getText());
	        	sec = new Section(Integer.parseInt(section.attribute("ID").getText()), section.getText());
	        	sectionList.add(sec);
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return sectionList;
	}
	
	/*
	 * 根据SectionID获取Boards
	 */
	public static List<Board> getBoardsBySection(Section sec) {
		List<Board> boardList = null;
		
		File file = new File("xml/Boards.xml");
		if (!file.exists()) {
			System.out.println("File Not Found: " + "xml/Boards.xml");
			return null;
		}
		
		boardList = new ArrayList<Board>();
		SAXReader reader = new SAXReader();
		Document document = null;
		Board bd = null;
		try {
			document = reader.read(file);				
			List<?> boards = document.getRootElement().selectNodes("Board[@sectionID='" + sec.getId() + "']");
	        for(Object obj: boards) {
	        	Element board = (Element)obj;
	        	//System.out.println(board.attribute("ID").getText() + "\t" + board.attribute("sectionID").getText() + "\t" + 
	        			//board.attribute("category").getText() + "\t" + board.attribute("chineseName").getText() + "\t" + board.getText());
	        	bd = new Board(Integer.parseInt(board.attribute("ID").getText()), 
	        					Integer.parseInt(board.attribute("sectionID").getText()), 
	        					board.getText(),
	        					board.attribute("chineseName").getText() ,
	        					board.attribute("category").getText()
	        					);
	        	boardList.add(bd);
	        }
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
		return boardList;
	}
	
	/*
	 * 访问网站，创建boards.xml文件
	 * 运行一次
	 */
	public static void createSectionXML() {
		int currentBoardID = 0;
		Document xml = null;
		Element boardsNode = null;
		try {
			xml = DocumentHelper.parseText("<Boards></Boards>");
			boardsNode = xml.getDocument().getRootElement();
			System.out.println(boardsNode.getName());
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		org.jsoup.nodes.Document doc = null;
		for (int sectionIndex = 0; sectionIndex <= 12; sectionIndex++) {
			try {
				doc = Jsoup.connect(Param.SectionPrefix + sectionIndex).get();				
				//System.out.println(doc);
				
				org.jsoup.select.Elements trs = doc.select("table").get(0).select("tr");
				//System.out.println(trs.size());
				for (int j = 1; j < trs.size(); j++) {
					org.jsoup.select.Elements tds = trs.get(j).select("td");
					Element board = boardsNode.addElement("Board");
					board.addAttribute("ID", String.valueOf(currentBoardID));
					board.addAttribute("sectionID", String.valueOf(sectionIndex));
					board.addAttribute("category", tds.get(4).text());
					board.addAttribute("chineseName", tds.get(5).text().substring(2));
					board.addText(tds.get(2).text());
					
					currentBoardID++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		writeXML(xml, "xml/Boards.xml");
	}
	
	/*
	 * 将XMLDocument写入到指定xml文件
	 */
	public static void writeXML(Document doc, String path) {
		// Pretty Print Format for XML
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        File dstFile = new File(path);
        if (dstFile.exists()) {
        	// System.out.println(dstFile + " exists but don't worry");
        	dstFile.delete();
        }
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(dstFile), format);
	        writer.write(doc);
	        writer.flush();
	        writer.close();
		} catch (IOException e) {
			System.out.println("Create Output File Failed");
			e.printStackTrace();
		}
	}
}

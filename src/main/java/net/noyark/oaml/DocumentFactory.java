/*Culesberry technolegy Co. Ltd. (c) 2019-2020
 * 
 * Stating that the software,the software belongs Gulesberry
 * noyark open source group,noyark has all the power to interpret
 * and copyright information for the software prohibit organizations
 * and individuals conduct their business practices and illegal practices,
 * projects of: magiclu,Chinese name *Changcun Lu*.The software has nothing
 * to do with current politics,free software is the purpose of noyark
 * 
 * noyark-system info:
 * 	****************************************************
 * 											www.noyark.net
 *		 ****************************************************
 * 
 */

package net.noyark.oaml;

import java.util.List;

import net.noyark.oaml.tree.Document;
import net.noyark.oaml.utils.CollectionFactory;

/**
 * <P>
 * This is the factory class used to generate the Document 
 * container.
 * @author magiclu550
 * @since JDK1.8
 * @since Oaml001
 */

public final class DocumentFactory {
	
	private DocumentFactory() {
	}
	
	/**
	 * This method can get a <code>Document</code> object 
	 * @return the Document object
	 */
	
	public static Document getDocument() {
		return new NodeFactory();
	}
	
	/**
	 * <P>
	 * This method can predetect the shape
	 * of the string information in the oaml
	 * document ,and will eventually return a
	 * string or other object based on the synax
	 * @param document the oaml text
	 * @return the string of oaml or object of oaml
	 */
	
	public static Object load(String document) {
		if(document.matches("\\[[\\s\\S]+\\]")) {
			String[] strings = document.split(",");
			List<String> list = CollectionFactory.getList(String.class);
			for(String str:strings) {
				list.add(str);
			}
			return list;
		}else if(document.startsWith("#")){
			return "";
		}else if(document.matches("\\([\\s\\S]+\\)")){
			return "Object object";
		}else{
			return document.replace("\\n","\\\\n");
		}
	}
}

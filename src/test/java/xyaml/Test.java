package xyaml;

import java.io.IOException;

import net.noyark.oaml.ConfigQueryer;
import net.noyark.oaml.DocumentFactory;
import net.noyark.oaml.ValueMapping;
import net.noyark.oaml.simple.OamlConfig;


public class Test {
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		OamlConfig config = ConfigQueryer.getInstance("a.oml", false);
		config.setEncoding("GBK");
		config.put("select.in.console","$ab1");
		Object o = DocumentFactory.setVar(config.get("select.in.console").toString(),new ValueMapping("ab","11"));
		config.save();
		System.out.println(o);
		config.save();
		config.close();
	}
}

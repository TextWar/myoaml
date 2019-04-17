package xyaml;

import java.io.IOException;

import net.noyark.oaml.ConfigQueryer;
import net.noyark.oaml.simple.OamlConfig;


public class Test {
	public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {
		OamlConfig config = ConfigQueryer.getInstance("a.oml", false);
		config.setEncoding("GBK");
		config.put("select.in.console",new int[2]);
		config.save();
		config.close();
	}
}

package xyaml;

import net.noyark.oaml.annotations.Config;
import net.noyark.oaml.annotations.Node;
import net.noyark.oaml.annotations.Root;

@Config("select.oaml")
public class ReflectTest {
	@Root("a")
	public int a = 0;
	@Node("a.b.c")
	public int b = 1;
	@Node("a.b.f")
	public int[] a1 = {1,2,3};
}

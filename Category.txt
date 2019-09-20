package bucketlist;

import java.util.*;

public class Category {
	String style;
	String code;
	
	void read(Scanner scan) {
		style = scan.next();
		code = scan.next();
	}
	void print() {
		System.out.printf("%s %s%n", style, code);
	}
	
}

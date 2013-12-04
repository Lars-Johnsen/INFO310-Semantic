package converting;

import gui.View;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database db = Database.getInstance();
		db.sysoDB();
		db.deleteOutofDate();
		System.out.println("DELETE");
		db.sysoDB();
		View view = new View();
		
	}
}

package fdi.ucm.server.importparser.mosaico;

import java.util.ArrayList;
import fdi.ucm.server.importparser.sql.CollectionSQL;
import fdi.ucm.server.importparser.sql.CollectionSQLInfering;
import fdi.ucm.server.importparser.sql.MySQLConnectionMySQL;
import fdi.ucm.server.modelComplete.collection.CompleteCollection;

public class CollectionMOSAICO_EXTRACT {

	public static CompleteCollection Apply(String user, String password) {
		CollectionMOSAICO_EXTRACT main=new CollectionMOSAICO_EXTRACT();
		System.out.println("Extractor de la informacion");
		return main.apply(user, password);
	}

	private CompleteCollection apply(String user, String password) {
		
		CollectionSQL SQLparser=new CollectionSQL();
		ArrayList<String> Log=new ArrayList<String>();
		
		ArrayList<String> DateEntrada = new ArrayList<String>();
		
		DateEntrada.add("localhost");
		DateEntrada.add("simulacion_mosaico");
		DateEntrada.add("3306");
		DateEntrada.add(user);
		DateEntrada.add(password);
		DateEntrada.add("false");
		DateEntrada.add("false");
		
		
		String Database = DateEntrada.get(1);
		MySQLConnectionMySQL SQL= MySQLConnectionMySQL.getInstance(DateEntrada.get(0),Database,Integer.parseInt(DateEntrada.get(2)),DateEntrada.get(3),DateEntrada.get(4));

		Boolean inferRelations=false;
		
		try {
			inferRelations=Boolean.parseBoolean(DateEntrada.get(5));
		} catch (Exception e) {
			
		}
	
		Boolean RelationsPublicPrivate=false;
		
		try {
			RelationsPublicPrivate=Boolean.parseBoolean(DateEntrada.get(6));
		} catch (Exception e) {
			
		}
		
		
		if (inferRelations)
			{
			SQLparser= new CollectionSQLInfering();
			((CollectionSQLInfering)SQLparser).publicPrivateAtribute(RelationsPublicPrivate);
			}
		
	SQLparser.setMySQLInstance(SQL);
	SQLparser.ProcessAttributes();
		
		for (String string : Log) {
			System.err.println(string);
		}
		

		
		return SQLparser.getColeccion();
	}

}

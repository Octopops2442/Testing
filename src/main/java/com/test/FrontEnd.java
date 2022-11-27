//
//public class FrontEnd {
//	public static void main(String[] args) {
//    String input = args[0];
//		try {
//		  System.out.println("Parsing " + input);
//      		parser p = new parser(new IdLexer(new FileReader(input)));
//			Program result = (Program)(p.parse().value);
//			// System.out.println("Printing program :\n");
//			// for(Instr i: result.instrList.iList){
//			// 	System.out.println("");
//			// 	System.out.println(i.toString());
//			// }
//
//			TypeChecker typeChecker = new TypeChecker(result);
//			typeChecker.TypeCheck();
//			// System.out.println(result);
//		}
//		catch(FileNotFoundException e) {
//      System.out.println("File not found!");
//			System.exit(1);
//    }
//		catch(Exception e) {
//      System.out.println("Unknown error!");
//			e.printStackTrace();
//			System.exit(1);
//    }
//	}
//}


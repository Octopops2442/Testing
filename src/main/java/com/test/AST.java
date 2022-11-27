package com.test;

import java.util.ArrayList;


class Type {
	public final String name;
  
	  public Type(String name) {
	  this.name = name;
	  }
  
	public boolean equals(Object type) {
	  
		if(!(type instanceof Type)) {
			return false;
		  }

		  return this.name.equals(((Type)type).name);
	  }
  
	  public String toString() {
		return this.name;
	  }

  }
  
  class IntType extends Type {
	
	public IntType() {
	  super("int");
	  }

	}
  
  class BoolType extends Type {

	public BoolType() {
	  super("bool");
	  }

}
  
class VoidType extends Type {

	public VoidType() {
	  super("Void");
	}

}

// ----------------------------------------------------------------------------------------

class Program{
	public InstrList instrList;

	public Program(InstrList il) {
		this.instrList = il;
	}

	public String toString() {
		return instrList.toString();
		// return " Program :\n" + this.iList.toString() + "\n";
	}

}

// ----------------------------------------------------------------------------------------

class InstrList{
	public ArrayList<Instr> iList;
	
	public InstrList(Instr i){
		this.iList = new ArrayList<>();
		
		if(i!=null){
			this.iList.add(i);
		}
		else this.iList.add(i);
	}

	public void addInstr(Instr i){
		this.iList.add(i);
	}

	public String toString() {
		String ret = "";

		for (Instr instr : iList) {
			
			if(instr instanceof Expr){
				ret = ret + instr.toString() +";\n"; 
			}
			else if(instr instanceof Assign){
				ret = ret + instr.toString() +";\n"; 
			}
			else if(instr instanceof Declaration){
				ret = ret + instr.toString() +";\n"; 
			}
			else {
				ret = ret + instr.toString() +"\n"; 
			}
		}

		return "\n"+ret+"\n";
	}

}

// ----------------------------------------------------------------------------------------

class ExprList{
	public ArrayList<Expr> exprList;

	public ExprList(Expr e){
		this.exprList = new ArrayList<>();
		this.exprList.add(e);
	}

	public void pushExpr(Expr e){
		this.exprList.add(e);
	}

	public String toString() {
		String ret = "";

		for (Expr expr : exprList) {
			ret = ret + expr.toString() +" , ";
		}

		return "("+ret+")";
	}

}

// ----------------------------------------------------------------------------------------

interface Instr{
}

interface Expr extends Instr{
  public abstract int evaluate();

}

class RetExpr implements Instr{
	public Expr expr;

	RetExpr(Expr e){
		this.expr = e;
	}

	@Override
	public String toString() {
		return "return "+ expr.toString()+" ";
	}

}

class AddExpr implements Expr {
  public final Expr left;
  public final Expr right;

  public AddExpr(Expr left, Expr right) {
		this.left = left;
		this.right = right;
	}

	public int evaluate() { 
		return this.left.evaluate() + this.right.evaluate(); 
	}

	public String toString() { 
		return this.left.toString() + " + " + this.right.toString(); 
	}

}

class SubExpr implements Expr {
	public final Expr left;
	public final Expr right;
  
	public SubExpr(Expr left, Expr right) {
		  this.left = left;
		  this.right = right;
	  }
  
	  public int evaluate() { 
		  return this.left.evaluate() - this.right.evaluate(); 
		}

	  public String toString() {
		   return this.left.toString() + " - " + this.right.toString(); 
		}

}
  

class MulExpr implements Expr {
    public final Expr left;
	public final Expr right;

  public MulExpr(Expr left, Expr right) {
    this.left = left;
		this.right = right;
	}

	public int evaluate() { 
		return this.left.evaluate() *+ this.right.evaluate();
	 }

	 public String toString() {
		 return this.left.toString() + " * " + this.right.toString(); 
		}

}

class NegExpr implements Expr {
  Expr expr;

  public NegExpr(Expr expr) {
	  this.expr = expr;
  }

	public int evaluate() { 
		return this.expr.evaluate(); 
	}
	
	public String toString() { 
		return "-(" + this.expr.toString() + ")";
	 }

}

class NumExpr implements Expr {
  public final Integer value;
	
  public NumExpr(Integer value) {
	  this.value = value;
  }

	public int evaluate() { 
		return this.value; 
	}
	
	public String toString() { 
		return this.value.toString(); 
	}
  
}

class IdExpr implements Expr {
	public final String name;
	
	public IdExpr(String name) {
		this.name = name;
	}
	
  public int evaluate() { 
		return 0; 
	}
	
	public String toString() {
		 return this.name; 
	}

}

class BoolConstExpr implements Expr {
	public final Boolean value;

	public BoolConstExpr(Boolean value) {
		this.value = value;
	}
  
	  public int evaluate() { 
		  return this.value?1:0; 
		}

	  public String toString() { 
		  return this.value.toString(); 
		}
	
  }
  

// ----------------------------------------------------------------------------------------

class EqualsBoolExpr implements Expr{
	public Expr e1,e2;
	
	EqualsBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public int evaluate(){
		return e1.evaluate() == e2.evaluate() ? 1 : 0 ;
	}

	public String toString() {
		String ret = e1.toString() + " == " + e2.toString() +" ";
		return ret;
	}

}

class LEqualsBoolExpr implements Expr{
	public Expr e1,e2;
	
	LEqualsBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public int evaluate(){
		return e1.evaluate() <= e2.evaluate() ? 1 : 0;
	}

	public String toString() {
		String ret = e1.toString() + " <= " + e2.toString() +" ";
		return ret;
	} 

}

class GEqualsBoolExpr implements Expr{
	public Expr e1,e2;
	
	GEqualsBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public int evaluate(){
		return e1.evaluate() >= e2.evaluate() ? 1 : 0;
	}

	public String toString() {
		String ret = e1.toString() + " >= " + e2.toString() +" ";
		return ret;
	}

}

class GreaterBoolExpr implements Expr{
	public Expr e1,e2;
	
	GreaterBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public int evaluate(){
		return e1.evaluate() < e2.evaluate() ? 1 : 0;
	}

	public String toString() {
		String ret = e1.toString() + " > " + e2.toString() +" ";
		return ret;
	}

}

class LesserBoolExpr implements Expr{
	public Expr e1,e2;
	
	LesserBoolExpr(Expr e1,Expr e2){
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public int evaluate(){
		return e1.evaluate() > e2.evaluate() ? 1 : 0;
	}

	@Override
	public String toString() {
		String ret = e1.toString() + " < " + e2.toString() +" ";
		return ret;
	}

}

// ----------------------------------------------------------------------------------------

class Assign implements Instr{
	public IdExpr id;
	public Expr expr;
	
	public Assign(String id1, Expr e1){
		this.id= new IdExpr(id1);
		this.expr=e1;
	}
	
	public String toString(){
		return this.id.toString() + " = " + this.expr.toString();
	}
	
	public int evaluate() {
		return 0;
	}

}

// ----------------------------------------------------------------------------------------

class FCall implements Expr{
	public String fname;
	public ExprList args;
	
	public FCall(String id,ExprList el){
		this.fname = id;
		this.args = el;
	}
	
	public String toString() {
		String ret = fname + "(" +args.toString() +")\n";
		return ret;
	}
	
	public int evaluate(){
		return 0;
	}

}

// -----------------------------------------------------------------------------------------


class Declaration implements Instr{
	public final String varname;
	  public final Type type;
  
	  public Declaration(String varname, Type type) {
	  	this.varname = varname;
		this.type = type;
	  }
  
	  public String toString() {
		return this.type.toString() + " " + this.varname;
	  }
  }
  
class DeclarationList {
	public ArrayList<Declaration> dList;
	
	public DeclarationList(Declaration d){
		this.dList = new ArrayList<>();
		dList.add(d);
	}
	
	public void addDecl(Declaration d){
		this.dList.add(d);
	}
	
	public String toString() {
		String ret = "";

		for (Declaration decl : dList) {
			ret = ret + decl.toString();
			ret = ret + ", ";
		}

		return ret;
	}

}

// ----------------------------------------------------------------------------------------

class IfStatement implements Expr{
	public Block b1,b2;
	public Expr e1;

	IfStatement(Expr e1,Block b1,Block b2){
		this.b1 = b1;
		this.b2 = b2;
		this.e1 = e1;
	}

	public String toString() {
		// TODO Auto-generated method stub
		String ret = "";
		ret += "if(" + e1.toString() +") "+ b1.toString();

		if(b2!=null){
			ret = ret + "\nelse "+ b2.toString()+"\n";
		}

		return ret;
	}

	public int evaluate() {
		// TODO Auto-generated method stub
		return b1.evaluate();
	}

}

// ----------------------------------------------------------------------------------------

class WhileExpr implements Expr {
	public Expr e;
	public Block b;

	WhileExpr(Expr e,Block b){
		this.e = e;
		this.b = b;
	}

	public int evaluate() {
		return 0;
	}

	public String toString() {
		String ret = "while(" + this.e.toString() +") "+ this.b.toString();
		return ret;
	}
}

// ----------------------------------------------------------------------------------------

class FunctionDeclaration implements Instr{
	public Type type;
	public IdExpr id;
	public Block bl;
	public DeclarationList dl;
	
	FunctionDeclaration(Type type, IdExpr id, DeclarationList dl, Block bl){
		this.type = type ;
		this.id = id;
		this.bl = bl;
		this.dl = dl;
	}
	
	public String toString(){
		return type.toString() +" " +id.toString() +" ("+ dl.toString() +") "+ bl.toString();
	}

}

class Block {
	public InstrList ilist ;
	public Type returnType; 

	Block(InstrList i){
		this.ilist = i;
		this.returnType = new VoidType(); 
	}

	public String toString(){
		return "{" + ilist.toString() + "}";
	}

	public int evaluate(){
		return 0;
	}

}

class  UseLessClass implements Instr{

}
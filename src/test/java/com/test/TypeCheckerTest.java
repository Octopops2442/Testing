package com.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class TypeCheckerTest {
    private TypeChecker t;

    @BeforeEach
    void Setup() {
        t = new TypeChecker(null);
    }

    @Test
    void typeCheckEdgeCoverage() {
        try {
            //1,2,3,5,7,9,11
            InstrList instrList = new InstrList(new UseLessClass());
            final Program p = new Program(instrList);
            t.program = p;
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheck());

            //1 2 3 4 2 12
            instrList = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration("variable", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            Program p1 = new Program(instrList);
            t.program= p1;
            Assertions.assertEquals(new VoidType(),t.TypeCheck());

            // 1 2 3 5 6 2 12
            instrList = new InstrList(new NumExpr(10));
            p1 = new Program(instrList);
            t.program = p1;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 7 8 2 12
            instrList = new InstrList( new Declaration("var", new IntType()));
            p1 = new Program( instrList);
            t.program = p1;
            Assertions.assertEquals(new VoidType(),t.TypeCheck());

            // 1 2 3 5 7 9 10 2 12  INFEASIBLE REQUIREMENT
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void typeCheckPrimeCoverage() {
        try {
            // 1 2 3 5 7 9 10 2 3 5 7 9 11  INFEASIBLE REQUIREMENT

            // 1 2 3 5 7 9 10 2 3 4 2 12 INFEASIBLE REQUIREMENT

            // 1 2 3 5 7 9 10 2 3 5 6 2 12 INFEASIBLE REQUIREMENT

            //1,2,3,5,7,9,11
            InstrList instrList = new InstrList(new UseLessClass());
            final Program p = new Program(instrList);
            t.program = p;
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheck());

            // 1 2 3 5 7 9 10 2 3 5 7 8 2 12 INFEASIBLE REQUIREMENT

            // 1 2 3 5 7 8 2 3 5 7 9 11
            instrList  = new InstrList(new Declaration("x", new IntType()));
            instrList.addInstr(new UseLessClass());
            final Program p1 = new Program(instrList);
            t.program = p1;
            Assertions.assertThrows(Exception.class, ()->t.TypeCheck());

            // 1 2 3 5 7 8 2 3 5 7 9 10 2 12
            instrList.iList.clear();
            instrList.addInstr(new Declaration("xy", new IntType()));
            instrList.addInstr(new Assign("xy", new NumExpr(10)));
            Program p2 = new Program(instrList);
            t.program = p2;
            Assertions.assertEquals(new VoidType(),t.TypeCheck() );

            // 1 2 3 5 7 9 10 2 3 5 7 9 10 2 12 INFEASIBLE REQUIREMENT

            // 1 2 3 5 6 2 3 5 7 9 11
            instrList  = new InstrList(new NumExpr(10));
            instrList.addInstr(new UseLessClass());
            final Program p3 = new Program(instrList);
            t.program = p3;
            Assertions.assertThrows(Exception.class, ()->t.TypeCheck());

            // 1 2 3 4 2 3 5 7 9 10 2 12 INFEASIBLE REQUIREMENT

            // 1 2 3 4 2 3 5 7 9 11
            instrList = new InstrList( new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            instrList.addInstr(new UseLessClass());
            final  Program p4 = new Program(instrList);
            t.program = p4 ;
            Assertions.assertThrows(Exception.class, ()->t.TypeCheck());

            // 1 2 3 5 6 2 3 5 7 9 10 2 12 INFEASIBLE REQUIREMENT

            // 1 2 3 5 7 8 2 3 4 2 12
            instrList = new InstrList(new Declaration("x", new IntType()));
            instrList.addInstr(new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            Program p5 = new Program(instrList);
            t.program = p5;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 7 8 2 3 5 6 2 12
            instrList = new InstrList(new Declaration("x", new IntType()));
            instrList.addInstr(new NumExpr(10));
            Program p6 = new Program(instrList);
            t.program = p6;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 7 8 2 3 5 7 8 2 12
            instrList = new InstrList(new Declaration("x", new IntType()));
            instrList.addInstr(new Declaration("y", new IntType()));
            Program p7 = new Program( instrList);
            t.program= p7;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 6 2 3 5 7 8 2 12
            instrList= new InstrList( new NumExpr(10));
            instrList.addInstr(new Declaration("y", new IntType()));
            Program p8 =new Program(instrList);
            t.program = p8;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 4 2 3 5 7 8 2 12
            instrList = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            instrList.addInstr(new Declaration("y", new IntType()));
            Program p9 =new Program(instrList);
            t.program = p9;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 6 2 3 4 2 12
            instrList= new InstrList( new NumExpr(10));
            instrList.addInstr(new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            Program p10 = new Program(instrList);
            t.program = p10;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 5 6 2 3 5 6 2 12
            instrList= new InstrList( new NumExpr(10));
            instrList.addInstr(new NumExpr(10));
            Program p11 = new Program(instrList);
            t.program = p11;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 4 2 3 5 6 2 12
            instrList = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            instrList.addInstr(new NumExpr(10));
            Program p12 = new Program(instrList);
            t.program = p12;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 3 4 2 3 4 2 12
            instrList = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("id"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            instrList.addInstr(new FunctionDeclaration(new IntType(), new IdExpr("id2"), new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            Program p13 = new Program( instrList);
            t.program = p13;
            Assertions.assertEquals(new VoidType(), t.TypeCheck());

            // 1 2 12 INFEASIBLE REQUIREMENTS
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Test
    void typeCheckFunctionDeclarationEdgeCoverage() {
        try {
            final FunctionDeclaration fd = new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration("x", new IntType())), new Block(new InstrList(new RetExpr(new BoolConstExpr(false)))));

            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), new DeclarationList(new Declaration("x", new IntType()))));
            Assertions.assertThrows(Exception.class, () -> t.TypeCheckFunctionDeclaration(fd) );

            final FunctionDeclaration fd1 = new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration("x", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(1)))));

            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), new DeclarationList(new Declaration("x", new IntType()))));
            Assertions.assertEquals(new VoidType(), t.TypeCheckFunctionDeclaration(fd1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void typeCheckFunctionDeclarationPrimeCoverage() {
        try {
            //1,2,4,5,6,9
            final FunctionDeclaration fd1 = new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration(null, null)), new Block(new InstrList(new RetExpr(new NumExpr(1)))));

            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), new DeclarationList(new Declaration(null, null))));
            Assertions.assertEquals(new VoidType(), t.TypeCheckFunctionDeclaration(fd1));

            //1,2,3,2,4,5,6,7,6,9
            final FunctionDeclaration fd2 = new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration("x", new IntType())), new Block(new InstrList(new RetExpr(new NumExpr(1)))));

            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), new DeclarationList(new Declaration("x", new IntType()))));
            Assertions.assertEquals(new VoidType(), t.TypeCheckFunctionDeclaration(fd2));

            //1,2,3,2,4,5,6,9 infeasable requirement
            //1,2,4,5,8
            final FunctionDeclaration fd3 = new FunctionDeclaration(new IntType(), new IdExpr("func"), new DeclarationList(new Declaration(null, null)), new Block(new InstrList(new RetExpr(new BoolConstExpr(false)))));

            Assertions.assertThrows(Exception.class, () -> t.TypeCheckFunctionDeclaration(fd3));

            //1,2,3,2,3,2,4,5,8
            DeclarationList diclist = new DeclarationList(new Declaration("x", new IntType()));

            diclist.addDecl(new Declaration("y", new IntType()));

            final FunctionDeclaration fd4 = new FunctionDeclaration(new IntType(), new IdExpr("func"), diclist, new Block(new InstrList(new RetExpr(new BoolConstExpr(false)))));

            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), diclist));
            Assertions.assertThrows(Exception.class, () -> t.TypeCheckFunctionDeclaration(fd4));

            //1,2,4,5,6,7,6,7,6,9 Infeasable requirement
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void typeCheckExpr() {
        try {
            // 1 2 3 4 5 7 9 11 13 15 17 19 21 23 25 27 29 31 INFEASIBLE REQUIREMENTS

            // 1 2 3 4 5 7 9 11 13 15 17 19 21 23 25 27 29 30 32
            Assertions.assertEquals(new VoidType(), t.TypeCheckExpr(new WhileExpr(new BoolConstExpr(false),new Block(new InstrList(new NumExpr(4))))));

            // 1 2 3 4 5 7 9 11 13 15 17 19 21 23 25 27 28 32
            Assertions.assertEquals(new VoidType(), t.TypeCheckExpr(new IfStatement(new BoolConstExpr(false), new Block(new InstrList( new NumExpr(4))), null)));

            // 1 2 3 4 5 7 9 11 13 15 17 19 21 23 25 26 32
            DeclarationList diclist = new DeclarationList(new Declaration("x", new IntType()));
            t.funcEnv.put("func", new FunctionEntry("func", new IntType(), diclist));
            Assertions.assertEquals(new IntType(),t.TypeCheckExpr(new FCall("func",  new ExprList(new NumExpr(49)))));

            // 1 2 3 4 5 7 9 11 13 15 17 19 21 23 24 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new GreaterBoolExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 5 7 9 11 13 15 17 19 21 22 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new LesserBoolExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 5 7 9 11 13 15 17 19 20 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new GEqualsBoolExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 5 7 9 11 13 15 17 18 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new LEqualsBoolExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 5 7 9 11 13 15 16 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new EqualsBoolExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 5 7 9 11 13 14 32
            t.typeEnv.put("s",new LinkedList<>());
            t.typeEnv.get("s").addLast(new IntType());
            Assertions.assertEquals(new IntType(), t.TypeCheckExpr(new IdExpr("s")));

            // 1 2 3 4 5 7 9 11 12 32
            Assertions.assertEquals(new BoolType(), t.TypeCheckExpr(new BoolConstExpr(false)));

            // 1 2 3 4 5 7 9 10 32
            Assertions.assertEquals(new IntType(), t.TypeCheckExpr(new NumExpr(29)));

            // 1 2 3 4 5 7 8 32
            Assertions.assertEquals(new IntType(), t.TypeCheckExpr(new NegExpr(new NumExpr(43))));

            // 1 2 3 4 5 6 32
            Assertions.assertEquals(new IntType(), t.TypeCheckExpr(new MulExpr(new NumExpr(3), new NumExpr(4))));

            // 1 2 3 4 32
            Assertions.assertEquals(new IntType(), t.TypeCheckExpr(new SubExpr(new NumExpr(4), new NumExpr(54))));
            // 1 2 3 32
            Assertions.assertEquals(new IntType(),t.TypeCheckExpr(new AddExpr(new NumExpr(4), new NumExpr(4))));


        } catch (Exception e) {

        }
    }

    @Test
    void typeCheckAddExpr() {
        try {
            AddExpr addExpr = new AddExpr(new NumExpr(10), new NumExpr(10));
            Assertions.assertEquals(new IntType(), t.TypeCheckAddExpr(addExpr));
            // Exception path
            final AddExpr addExpr1 = new AddExpr(new NumExpr(10), new BoolConstExpr(false));
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckAddExpr(addExpr1));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckSubExpr() {
        try {
            SubExpr subExpr = new SubExpr(new NumExpr(10), new NumExpr(10));
            Assertions.assertEquals(new IntType(), t.TypeCheckSubExpr(subExpr));
            // Exception path
            final SubExpr subExpr1 = new SubExpr(new NumExpr(10), new BoolConstExpr(false));
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckSubExpr(subExpr1));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckMulExpr() {
        try {
            MulExpr mulExpr = new MulExpr(new NumExpr(10), new NumExpr(10));
            Assertions.assertEquals(new IntType(), t.TypeCheckMulExpr(mulExpr));
            // Exception path
            final MulExpr mulExpr1 = new MulExpr(new NumExpr(10), new BoolConstExpr(false));
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckMulExpr(mulExpr1));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckIdExpr() {
        try {
            IdExpr idExpr = new IdExpr("a");
            // Exception path
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckIdExpr(idExpr));

            // Normal Path
            t.typeEnv.put("a",new LinkedList<>());
            t.typeEnv.get("a").addLast(new IntType());
            Assertions.assertEquals(new IntType(), t.TypeCheckIdExpr(idExpr));

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckLEqualsBoolExpr() {
        try {
            Expr e = new LEqualsBoolExpr(new NumExpr(10), new BoolConstExpr(true));

            // Exception path
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckLEqualsBoolExpr((LEqualsBoolExpr)e ));

            //Normal path
            final Expr e2 = new LEqualsBoolExpr(new NumExpr(10), new NumExpr(1));
            Assertions.assertEquals(new BoolType(), t.TypeCheckLEqualsBoolExpr((LEqualsBoolExpr) e2));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckGEqualsBoolExpr() {
        try {
            Expr e = new GEqualsBoolExpr(new NumExpr(10), new BoolConstExpr(true));

            // Exception path
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckGEqualsBoolExpr((GEqualsBoolExpr)e ));

            //Normal path
            final Expr e2 = new GEqualsBoolExpr(new NumExpr(10), new NumExpr(1));
            Assertions.assertEquals(new BoolType(), t.TypeCheckGEqualsBoolExpr((GEqualsBoolExpr) e2));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckLessersBoolExpr() {
        try {
            Expr e = new LesserBoolExpr(new NumExpr(10), new BoolConstExpr(true));

            // Exception path
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckLessersBoolExpr((LesserBoolExpr) e ));

            //Normal path
            final Expr e2 = new LesserBoolExpr(new NumExpr(10), new NumExpr(1));
            Assertions.assertEquals(new BoolType(), t.TypeCheckLessersBoolExpr((LesserBoolExpr) e2));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckGreaterBoolExpr() {
        try {
            Expr e = new GreaterBoolExpr(new NumExpr(10), new BoolConstExpr(true));

            // Exception path
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckGreaterBoolExpr((GreaterBoolExpr) e ));

            //Normal path
            final Expr e2 = new LesserBoolExpr(new NumExpr(10), new NumExpr(1));
            Assertions.assertEquals(new BoolType(), t.TypeCheckGreaterBoolExpr((GreaterBoolExpr) e2));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckFunctionCallEdgePaths() {
        try{
            // throw Exception
            FCall fc = new FCall("f", new ExprList(new BoolConstExpr(false)));
            FunctionEntry fEntry = new FunctionEntry("f", new IntType(), new DeclarationList(new Declaration("arg1",new IntType())));
            t.funcEnv.put("f", fEntry);
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckFunctionCall(fc));

            // Normal path with one loop execution
            fEntry = new FunctionEntry("f", new IntType(), new DeclarationList(new Declaration("arg1",new BoolType())));
            FCall fc2 = new FCall("f", new ExprList(new BoolConstExpr(false)));
            t.funcEnv.put("f", fEntry);
            Assertions.assertEquals(new IntType(), t.TypeCheckFunctionCall(fc2));


        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckFunctionCallPrimePaths() {
        try{
//            // throw Exception
            final FCall fc = new FCall("f", new ExprList(new BoolConstExpr(false)));
            FunctionEntry fEntry = new FunctionEntry("f", new IntType(), new DeclarationList(new Declaration("arg1",new IntType())));
            t.funcEnv.put("f", fEntry);
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckFunctionCall(fc));

            //No arguments function Call
//            FCall fc1 = new FCall("f", new ExprList(null));
//            fEntry = new FunctionEntry("f", new IntType(), new DeclarationList(null));
//            t.funcEnv.put("f", fEntry);
//            Assertions.assertEquals(new IntType(), t.TypeCheckFunctionCall(fc1));

            // Throw exception with two arguments with second mismatched
            ExprList elist = new ExprList( new BoolConstExpr(true));
            elist.pushExpr(new BoolConstExpr(true));
            final FCall fc2 = new FCall("f", elist);
            DeclarationList dicList = new DeclarationList(new Declaration("a", new BoolType()));
            dicList.addDecl(new Declaration( "b", new IntType()));
            fEntry = new FunctionEntry("f", new IntType(), dicList);
            t.funcEnv.put("f", fEntry);
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckFunctionCall(fc2));

            // Normal Execution with two arguments
            dicList = new DeclarationList(new Declaration("a", new BoolType()));
            dicList.addDecl(new Declaration("b",new BoolType()));
            fEntry = new FunctionEntry("f", new IntType(), dicList);
            t.funcEnv.put("f", fEntry);
            Assertions.assertEquals(new IntType(), t.TypeCheckFunctionCall(fc2));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckBlockEdge() {
        try{
            // 1,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,21,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,3,31,3,32
            InstrList iList = new InstrList(new IfStatement(new BoolConstExpr(true), new Block(new InstrList(new NumExpr(29))),null));
            Block b = new Block(iList);
            Assertions.assertEquals(new VoidType(), t.TypeCheckBlock(b));

            // 1,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,25
            InstrList il = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("f"),new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(null))));
            final Block b4 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b4));

            // 1,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,3,32 INFEASIBLE REQUIREMENT
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    void typeCheckBlockPrime() {
        try {
            // 1 2 4 6 10 16 2 4 6 9 14 18 2 4 6 9 14 17 23 25
            InstrList il = new InstrList( new WhileExpr(new BoolConstExpr(true), new Block( new InstrList(new NumExpr(10)))));
            il.addInstr(new Declaration("var", new IntType()));
            il.addInstr(new FunctionDeclaration(new IntType(), new IdExpr("f"), new DeclarationList(new Declaration("a", new IntType())),new Block(new InstrList(new NumExpr(19))) ));
            final Block b = new Block( il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b));

            // 1 2 4 6 10 16 2 4 6 10 16 19 21 2 4 6 10 16 19 20 2 3 31 33 31 33 3 32 INFEASIBLE REQUIREMENT

            // 1 2 4 6 9 14 17 23 26 27 29 2 4 6 9 14 17 23 26 27 28 2 4 6 9 14 17 24 2 4 6 9 14 17 23 26 27 29 30 INFEASIBLE REQUIREMENT

            // 1 2 4 5 7 8 12 2 4 5 7 8 11 2 4 5 7 8 12 13
            il = new InstrList( new IfStatement(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new NumExpr(10)))), null));
            il.addInstr(new IfStatement(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new BoolConstExpr(false)))), null));
            final Block b1 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b1));

            // 1,2,4,5,7,2,4,5,7,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,5,7,8,12,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,9,14,17,23,26,27,29,2,4,6,9,14,17,23,26,27,29,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,4,6,10,16,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,3,31,3,32 INFEASIBLE REQUIREMENT

            // 1,2,3,31,3,31,3,31,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,10,16,19,20,2,3,32 INFEASIBLE REQUIREMENT

            // 1 2 3 32 INFEASIBLE REQUIREMENT

            // 1,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,4,6,9,15,2,4,5,7,8,11,2,4,6,10,16,19,20,2,4,6,9,14,17,23,25
            il = new InstrList(new NumExpr( 10));
//            new InstrList( new WhileExpr(new BoolConstExpr(true), new Block( new InstrList(new NumExpr(10)))));
            il.addInstr(new NumExpr(10));
            il.addInstr(new IfStatement(new BoolConstExpr(true),new Block(new InstrList(new RetExpr(new NumExpr(10)))),null));
            il.addInstr(new WhileExpr(new BoolConstExpr(true),new Block(new InstrList(new RetExpr(new BoolConstExpr(true))))));
            final  Block b2 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b2));

            // 1,2,4,5,7,8,11,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,10,16,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,4,6,9,14,17,24,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,21,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,9,14,17,23,26,27,29,2,4,6,10,16,19,20,22
            il = new InstrList(new RetExpr(new BoolConstExpr(true)));
            il.addInstr(new RetExpr(new BoolConstExpr(false)));
            il.addInstr(new WhileExpr(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new NumExpr(10))))));
            final Block b3 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b3));

            // 1,2,4,5,7,2,4,6,9,14,18,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,6,9,14,18,2,4,6,9,14,17,24,2,4,6,9,15,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,4,6,9,14,17,24,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,4,6,9,14,17,24,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,4,6,10,16,19,21,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,9,14,17,24,2,4,6,10,16,19,21,2,4,5,7,8,11,2,4,6,9,14,17,23,25 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,4,6,9,14,17,23,25 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,10,16,19,21,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,4,6,9,14,17,24,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,21,2,4,6,10,16,19,21,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,10,16,19,20,2,4,6,9,14,18,2,4,6,10,16,19,21,2,4,6,9,14,17,24,2,4,5,7,8,11,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,9,14,17,23,26,27,28,2,4,6,9,15,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,6,9,15,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            //1,2,4,5,7,8,11,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,25
            il = new InstrList(new FunctionDeclaration(new IntType(), new IdExpr("f"),new DeclarationList(new Declaration("a", new IntType())), new Block(new InstrList(null))));
            final Block b4 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b4));

            // 1,2,4,6,10,16,19,20,2,4,6,9,15,2,3,32

            // 1,2,4,6,10,16,19,21,2,4,6,9,15,2,3,32

            // 1,2,4,6,9,15,2,4,6,9,14,17,23,25
            il = new InstrList(new NumExpr(19));
            il.addInstr(new FunctionDeclaration(null, null, null, null));
            final Block b5 = new Block( il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b5));

            // 1,2,4,6,9,15,2,4,6,10,16,19,21,2,3,32

            // 1,2,4,5,7,8,11,2,4,6,9,14,18,2,3,32

            // 1,2,4,5,7,8,12,2,4,6,9,14,18,2,3,32

            // 1,2,4,5,7,2,4,6,9,14,17,23,25
            il = new InstrList(new IfStatement(new BoolConstExpr(true), new Block(new InstrList(new NumExpr(1))),null));
            il.addInstr(new FunctionDeclaration(null, null, null, null));
            final Block b6 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b6));

            // 1,2,4,6,9,14,18,2,4,5,7,8,11,2,3,32

            // 1,2,4,6,9,15,2,4,6,10,16,19,20,22

            // 1,2,4,6,10,16,2,4,6,9,14,17,23,25
            il = new InstrList(new WhileExpr(new BoolConstExpr(true), new Block(new InstrList(new NumExpr(19)))));
            il.addInstr(new FunctionDeclaration(null, null, null, null));
            final  Block b7 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b7));

            // 1,2,4,6,10,16,19,21,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,10,16,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,5,7,8,12,2,4,6,10,16,19,21,2,4,6,9,14,17,23,25 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,9,15,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,6,9,14,17,24,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,9,14,18,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,4,6,10,16,19,21,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,4,6,9,14,17,24,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,5,7,8,12,13  INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,15,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,9,14,17,23,25
            il = new InstrList( new RetExpr(new BoolConstExpr(true)));
            il.addInstr(new FunctionDeclaration(null, null, null , null));
            final Block b8 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b8));

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,9,14,17,23,25 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,9,14,17,24,2,3,32  INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,4,5,7,8,12,2,4,6,9,14,17,23,26,27,28,2,4,6,10,16,19,21,2,4,6,9,14,17,23,26,27,28,2,4,5,7,8,11,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,12,2,4,6,9,14,17,23,25INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,4,6,10,16,19,20,22 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,24,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,18,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,21,2,4,5,7,8,12,13
            il = new InstrList(new WhileExpr(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new BoolConstExpr(false))))));
            il.addInstr(new IfStatement(new BoolConstExpr(true), new Block( new InstrList(new RetExpr(new NumExpr(10)))), null));
            final Block b9 = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(b9));

            // 1,2,4,6,10,16,19,20,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,4,6,10,16,19,20,2,4,6,9,14,17,23,26,27,28,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,3,31,33,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,6,10,16,19,21,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,5,7,8,11,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,5,7,8,11,2,4,6,9,14,17,23,26,27,28,2,3,32 INFEASIBLE REQUIREMENT

            // 1,2,4,6,9,14,17,23,26,27,28,2,4,6,10,16,19,20,22
            il = new InstrList( new RetExpr(new BoolConstExpr(false)));
            il.addInstr(new WhileExpr(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new NumExpr(1))))));
            final Block b10 = new Block(il);
            Assertions.assertThrows(Exception.class,()-> t.TypeCheckBlock(b10));

            // 1,2,4,6,9,14,17,23,26,27,29,2,4,5,7,8,12,13 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,20,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT

            // 1,2,4,6,10,16,19,21,2,4,6,9,14,17,23,26,27,29,30
            il = new InstrList(new WhileExpr(new BoolConstExpr(true), new Block(new InstrList(new RetExpr(new NumExpr(8))))));
            il.addInstr(new RetExpr(new BoolConstExpr(false)));
            final Block bl = new Block(il);
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckBlock(bl));

            // 1,2,4,5,7,8,12,2,4,6,9,14,17,23,26,27,29,30 INFEASIBLE REQUIREMENT
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckDeclaration() {
        try {
            Declaration d = new Declaration( "vname",new IntType() );

            // for one path
            Assertions.assertEquals(new VoidType(), t.TypeCheckDeclaration(d));

            t.typeEnv.put(d.varname,new LinkedList<>());
            t.typeEnv.get(d.varname).addLast(d.type);
            // for second path
            Assertions.assertEquals(new VoidType(), t.TypeCheckDeclaration(d));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void typeCheckAssignment() {
        Assign a = new Assign("a", new NumExpr(10));

        //1st Exception path
        Assertions.assertThrows(Exception.class, () -> t.TypeCheckAssignment(a));

        //2nd Exception path
        Declaration d = new Declaration( "a",new BoolType() );
        t.typeEnv.put(d.varname,new LinkedList<>());
        t.typeEnv.get(d.varname).addLast(d.type);
        Assertions.assertThrows(Exception.class,()-> t.TypeCheckAssignment(a));

        //3rd Path
        t.typeEnv.get(d.varname).addLast(new IntType());
//        Assertions.assertThrows(Exception.class, ()->t.TypeCheckAssignment(a));
        try {
            Assertions.assertEquals(new VoidType(), t.TypeCheckAssignment(a));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckReturn() {
        try {
            t.checkingFunction = true;
            t.functionType = new IntType();
            final RetExpr r = new RetExpr(new BoolConstExpr(true));

            // Exception Path
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckReturn(r));

            // Normal path
            t.functionType = new BoolType();
            Assertions.assertEquals(new BoolType(),t.TypeCheckReturn(r));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckIfStatement() {
        try {
            // valid ifstmt with only 1 block
            IfStatement i2 = new IfStatement(new BoolConstExpr(true),
                    new Block(new InstrList(new NumExpr(120))),
                    null);
            Assertions.assertEquals(new VoidType(), t.TypeCheckIfStatement(i2));

            // valid ifstmt with 2 blocks
            IfStatement i = new IfStatement(new BoolConstExpr(true),
                    new Block(new InstrList(new NumExpr(120))),
                    new Block(new InstrList(new NumExpr(1234))));

            Assertions.assertEquals(new VoidType(), t.TypeCheckIfStatement(i));

            // invalid with 1 block
            final IfStatement i3 = new IfStatement(new AddExpr(new NumExpr(19), new NumExpr(20)),
                    new Block(new InstrList(new NumExpr(20))), null);
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckIfStatement(i3));

            // invalid with 2 block
            final IfStatement i4 = new IfStatement(new AddExpr(new NumExpr(19), new NumExpr(20)),
                    new Block(new InstrList(new NumExpr(20))), new Block(new InstrList(new NumExpr(29))));
            Assertions.assertThrows(Exception.class, ()->t.TypeCheckIfStatement(i4));

            // valid with 1 block with int return type
            i= new IfStatement(new BoolConstExpr(true),
                    new Block(new InstrList(new RetExpr(new NumExpr(10)))), null);
            Assertions.assertEquals(new IntType(), t.TypeCheckIfStatement(i));

            // valid with 2 blocks with int return type for block 1
            i= new IfStatement(new BoolConstExpr(true),
                    new Block(new InstrList(new RetExpr(new NumExpr(10)))),
                    new Block(new InstrList(new NumExpr(29))));
            Assertions.assertEquals(new IntType(), t.TypeCheckIfStatement(i));

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    void typeCheckWhile() {
        try {
            WhileExpr w = new WhileExpr(new BoolConstExpr(true),new Block(new InstrList(new NumExpr(19))));

            // Normal execution path
            Assertions.assertEquals(new VoidType(), t.TypeCheckWhile(w));

            // Exception path
            final WhileExpr wf = new WhileExpr( new AddExpr(new NumExpr(10), new NumExpr(10)),new Block(new InstrList(new NumExpr(19))) );
            Assertions.assertThrows(Exception.class, ()-> t.TypeCheckWhile(wf));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
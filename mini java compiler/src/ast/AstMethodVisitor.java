package ast;

import java.util.Collections;
import java.util.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// A 
// B extend A
// C extend B


// A    B    C 

public class AstMethodVisitor implements Visitor {
    //list of class names
    private String currMethod;
    private String currClass;
    private List<String> extendList = new ArrayList<String>();
    private List<String> superList = new ArrayList<String>();
    // old name
    private String oldName = new String();
    // new name
    private String newName = new String();
    //line number
    private int lineNumber;
    private String curClass;
    private Program prog;

    // constructor for ASTMethodVisitor
    public AstMethodVisitor(String class_name, String ogName, String newName, int original_line) {
        this.curClass = class_name;
        this.extendList.add(class_name);
        this.superList.add(class_name);
        this.oldName = ogName;
        this.newName = newName;
        this.lineNumber = original_line;
    }

    private void visitBinaryExpr(BinaryExpr e) {
        e.e1().accept(this);
        e.e2().accept(this);
    }

    @Override
    public void visit(Program program) {
        // TODO Auto-generated method stub
        this.prog = program;
        program.mainClass().accept(this);
        for (var classdecl : program.classDecls()) {
            if (this.extendList.contains(classdecl.name())) classdecl.accept(this);
            else if (this.extendList.contains(classdecl.superName())) {
                this.extendList.add(classdecl.name());
                //classdecl.accept(this);
            }
        }

        Collections.reverse(program.classDecls());
        for (var classdecl : program.classDecls()) {
            if (this.superList.contains(classdecl.name()) && classdecl.superName() != null) {
                superList.add(classdecl.superName());
            }


        }
        Collections.reverse(program.classDecls());
        for (var classdecl : program.classDecls()) {
            currClass = classdecl.name();
            classdecl.accept(this);
        }


    }

    @Override
    public void visit(ClassDecl classDecl) {
        RelationVisitor rv = new RelationVisitor(superList, classDecl.superName(), this.newName);
        rv.visit(this.prog);
        for (var methodDecl : classDecl.methoddecls()) {
            if ((superList.contains(currClass) || extendList.contains(currClass)) && (methodDecl.name().equals(oldName))) {
                methodDecl.setName(newName);
            } else if (rv.IsRelated() && methodDecl.name().equals(oldName)) {
                methodDecl.setName(newName);
            }
            currMethod = methodDecl.name();
            methodDecl.accept(this);
        }
    }

    @Override
    public void visit(MainClass mainClass) {
        // TODO Auto-generated method stub
        mainClass.mainStatement().accept(this);
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        // TODO Auto-generated method stub
        for (var stmt : methodDecl.body()) {
            stmt.accept(this);
        }
        methodDecl.ret().accept(this);
    }

    @Override
    public void visit(FormalArg formalArg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(VarDecl varDecl) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        // TODO Auto-generated method stub
        for (var s : blockStatement.statements()) {
            s.accept(this);
        }
    }

    @Override
    public void visit(IfStatement ifStatement) {
        // TODO Auto-generated method stub
        ifStatement.cond().accept(this);

        ifStatement.thencase().accept(this);

        ifStatement.elsecase().accept(this);
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        // TODO Auto-generated method stub
        whileStatement.cond().accept(this);

        whileStatement.body().accept(this);
    }

    @Override
    public void visit(SysoutStatement sysoutStatement) {
        // TODO Auto-generated method stub
        sysoutStatement.arg().accept(this);
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        // TODO Auto-generated method stub
        assignStatement.rv().accept(this);
    }

    @Override
    public void visit(AssignArrayStatement assignArrayStatement) {
        // TODO Auto-generated method stub

        assignArrayStatement.index().accept(this);
        assignArrayStatement.rv().accept(this);
    }

    @Override
    public void visit(AndExpr e) {
        // TODO Auto-generated method stub
        visitBinaryExpr(e);
    }

    @Override
    public void visit(LtExpr e) {
        // TODO Auto-generated method stub
        visitBinaryExpr(e);
        ;
    }

    @Override
    public void visit(AddExpr e) {
        // TODO Auto-generated method stub
        visitBinaryExpr(e);
        ;
    }

    @Override
    public void visit(SubtractExpr e) {
        // TODO Auto-generated method stub
        visitBinaryExpr(e);
        ;
    }

    @Override
    public void visit(MultExpr e) {
        // TODO Auto-generated method stub
        visitBinaryExpr(e);
        ;
    }

    @Override
    public void visit(ArrayAccessExpr e) {
        // TODO Auto-generated method stub
        e.arrayExpr().accept(this);
        e.indexExpr().accept(this);
    }

    @Override
    public void visit(ArrayLengthExpr e) {
        // TODO Auto-generated method stub
        e.arrayExpr().accept(this);
    }

    @Override
    public void visit(MethodCallExpr e) {
        // TODO Auto-generated method stub
        Expr exp = e.ownerExpr();
        if (exp instanceof NewObjectExpr) {//this ref-id new-obj
            NewObjectExpr e1 = (NewObjectExpr) e.ownerExpr();
            if (e.methodId().equals(oldName) && (superList.contains(e1.classId()) || extendList.contains(e1.classId()))) {
                e.setMethodId(newName);
            }
        } else if (exp instanceof ThisExpr) {
            if (e.methodId().equals(oldName) && (superList.contains(currClass) || extendList.contains(currClass))) {
                e.setMethodId(newName);
            }
        } else { // if it's an identifier
            IdentifierExpr e3 = (IdentifierExpr) e.ownerExpr();

            IdentifierKindVisitor ikv = new IdentifierKindVisitor(currClass, currMethod, e3.id());
            ikv.visit(prog);
            if (e.methodId().equals(oldName) && (superList.contains(ikv.getType()) || extendList.contains(ikv.getType()))) {
                e.setMethodId(newName);
            }
        }
    }

    //class A{ int x}
    // class B extend A{}
    // A a =
    @Override
    public void visit(IntegerLiteralExpr e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(TrueExpr e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(FalseExpr e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IdentifierExpr e) {
        // TODO Auto-generated method stub

    }

    //class A{ run }

    @Override
    public void visit(ThisExpr e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(NewIntArrayExpr e) {
        // TODO Auto-generated method stub
        // int run;
        // class A{ run }
        e.lengthExpr().accept(this);
    }

    @Override
    public void visit(NewObjectExpr e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(NotExpr e) {
        // TODO Auto-generated method stub
        e.e().accept(this);
    }

    @Override
    public void visit(IntAstType t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(BoolAstType t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IntArrayAstType t) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(RefType t) {
        // TODO Auto-generated method stub

    }

}




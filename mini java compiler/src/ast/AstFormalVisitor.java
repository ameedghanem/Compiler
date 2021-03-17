package ast;

import java.util.ArrayList;
import java.util.List;


public class AstFormalVisitor implements Visitor {
	
    private int lnumber;
    private String methName;
	private String ogName=new String();
	private String newName=new String();
    private String className;
	
	public AstFormalVisitor(String className, String ogName,String newName, String methName, int lnumber) {
        this.methName = methName;
		this.ogName=ogName;
		this.newName=newName;
        this.lnumber = lnumber;
        this.className = className;
	}
	private void visitBinaryExpr(BinaryExpr e) {
        e.e1().accept(this);
        
        e.e2().accept(this);
    }
	
	

    @Override
    public void visit(Program program) {
        program.mainClass().accept(this);
        for (ClassDecl classdecl : program.classDecls()) {
            if(classdecl.name().equals(className))
                classdecl.accept(this);
        }
    }

    @Override
    public void visit(ClassDecl classDecl) {
        for (var methodDecl : classDecl.methoddecls()) {
            if(methodDecl.name().equals(methName))
                methodDecl.accept(this);
        }
    }

    @Override
    public void visit(MainClass mainClass) {
        mainClass.mainStatement().accept(this);
        
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        boolean found = false;
        for (var formal : methodDecl.formals()) {
            if (formal.name().equals(ogName) && formal.lineNumber == lnumber) {
            	formal.setName(this.newName);
                found = true;
            	break;
            }
        }
        if(found){
            for(var stmt: methodDecl.body()){
                stmt.accept(this);
            }
        }
        methodDecl.ret().accept(this);
    }

    @Override
    public void visit(FormalArg formalArg) {
        formalArg.accept(this);
    }

    @Override
    public void visit(VarDecl varDecl) {
        varDecl.accept(this);
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        for (var s : blockStatement.statements()) {
            s.accept(this);
        }
    }

    @Override
    public void visit(IfStatement ifStatement) {
        
        ifStatement.cond().accept(this);
        
        ifStatement.thencase().accept(this);
        
        ifStatement.elsecase().accept(this);
        
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        
        whileStatement.cond().accept(this);
        
        whileStatement.body().accept(this);
        
    }

    @Override
    public void visit(SysoutStatement sysoutStatement) {
        
        sysoutStatement.arg().accept(this);
        
    }

    @Override
    public void visit(AssignStatement assignStatement) {
        
        if (assignStatement.lv().equals(ogName)) assignStatement.setLv(newName); 
        
        assignStatement.rv().accept(this);
     
    }

    @Override
    public void visit(AssignArrayStatement assignArrayStatement) {
        
        if (assignArrayStatement.lv().equals(ogName)) assignArrayStatement.setLv(newName);
        
        assignArrayStatement.index().accept(this);
        
        
        assignArrayStatement.rv().accept(this);
        
    }

    @Override
    public void visit(AndExpr e) {
        visitBinaryExpr(e);
    }

    @Override
    public void visit(LtExpr e) {
        visitBinaryExpr(e);;
    }

    @Override
    public void visit(AddExpr e) {
        visitBinaryExpr(e);;
    }

    @Override
    public void visit(SubtractExpr e) {
        visitBinaryExpr(e);
    }

    @Override
    public void visit(MultExpr e) {
        visitBinaryExpr(e);
    }

    @Override
    public void visit(ArrayAccessExpr e) {
        
        e.arrayExpr().accept(this);
        
        e.indexExpr().accept(this);
        
    }

    @Override
    public void visit(ArrayLengthExpr e) {
    	
        e.arrayExpr().accept(this);
        
    }

    @Override
    public void visit(MethodCallExpr e) {
        e.ownerExpr().accept(this);
        
        

        
        for (Expr arg : e.actuals()) {
            arg.accept(this);
            
        }
        
    }

    @Override
    public void visit(IntegerLiteralExpr e) {
        
    }

    @Override
    public void visit(TrueExpr e) {
        
    }

    @Override
    public void visit(FalseExpr e) {
        
    }

    @Override
    public void visit(IdentifierExpr e) {
    	if (e.id().equals(ogName)) e.setId(newName);
        e.id();
    }

    public void visit(ThisExpr e) {
       
    }

    @Override
    public void visit(NewIntArrayExpr e) {
        
        e.lengthExpr().accept(this);
        
    }

    @Override
    public void visit(NewObjectExpr e) {
    }

    @Override
    public void visit(NotExpr e) {
        
        e.e().accept(this);
        
    }

    @Override
    public void visit(IntAstType t) {
    }

    @Override
    public void visit(BoolAstType t) {
        
    }

    @Override
    public void visit(IntArrayAstType t) {
        
    }

    @Override
    public void visit(RefType t) {
        
    }

	
}

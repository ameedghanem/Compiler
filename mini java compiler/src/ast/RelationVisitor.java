package ast;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class RelationVisitor implements Visitor {
	
	private List<String> classList=new ArrayList<String>();
	private String sName;
	private boolean isRelated;
	private String methName;	
	int index; 
	public RelationVisitor(List<String> classList ,String sName, String methName) {
		this.classList = classList;
		this.sName=sName;
		this.methName = methName;
		this.isRelated = false;
	}
	public boolean IsRelated() {
		return this.isRelated;
	}
    @Override
    public void visit(Program program) {
        program.mainClass().accept(this);
        List<ClassDecl> new_classdecls = new ArrayList<ClassDecl>(program.classDecls());
        Collections.reverse(new_classdecls);
        for(ClassDecl classdecl : new_classdecls) {
        	index = this.classList.indexOf(sName);
        	if(classdecl.name().equals(sName)) {
        		classdecl.accept(this);
        		if(index < classList.size()-1)
        			sName = classList.get(index+1);
        	}
        	
        }
    }

    @Override
    public void visit(ClassDecl classDecl) {
        for (var methodDecl : classDecl.methoddecls()) {
        	if(methodDecl.name().equals(methName))
        		this.isRelated = true;
            
        }
        
    }

    @Override
    public void visit(MainClass mainClass) {
        
        mainClass.mainStatement().accept(this);
        
    }

    @Override
    public void visit(MethodDecl methodDecl) {
    	
    }

    @Override
    public void visit(FormalArg formalArg) {
        
    }

    @Override
    public void visit(VarDecl varDecl) {
        
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
    }

    @Override
    public void visit(AssignArrayStatement assignArrayStatement) {
        
    }

    @Override
    public void visit(AndExpr e) {
    }

    @Override
    public void visit(LtExpr e) {
    }

    @Override
    public void visit(AddExpr e) {
    }

    @Override
    public void visit(SubtractExpr e) {
    }

    @Override
    public void visit(MultExpr e) {
    }

    @Override
    public void visit(ArrayAccessExpr e) {
        
        
    }

    @Override
    public void visit(ArrayLengthExpr e) {
        
    }

    @Override
    public void visit(MethodCallExpr e) {
        
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
    }

    public void visit(ThisExpr e) {
       
    }

    @Override
    public void visit(NewIntArrayExpr e) {     
        
    }

    @Override
    public void visit(NewObjectExpr e) {
        
    }

    @Override
    public void visit(NotExpr e) {
        
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

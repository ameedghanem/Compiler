package ast;

public class VarKindVisitor implements Visitor {
	
	
	private String ogLine = new String();
	private String type = new String();
	private String clsName = new String();
	private String methName = new String();
    private String myClass;

	
	public VarKindVisitor(String ogLine) {
        
        this.ogLine=ogLine;
    }
	
	public String getType() {
        return type;
    }
	public String clsName() {
		return clsName;
	}
	public String methName() {
		return methName;
	}
	

    @Override
    public void visit(Program program) {
        
    
        for (ClassDecl classdecl : program.classDecls()) {
            classdecl.accept(this);
            
        }
    }

    @Override
    public void visit(ClassDecl classDecl) {
        
        
        
        myClass = classDecl.name();
        for (var fieldDecl : classDecl.fields()) {
        	if (fieldDecl.lineNumber==Integer.parseInt(ogLine)) {
        		type="field";
        		clsName=classDecl.name();
        	}
            
        }
        for(var methodDecl: classDecl.methoddecls()){
            methodDecl.accept(this);
        }
        
        
    }

    @Override
    public void visit(MainClass mainClass) {
        
        mainClass.mainStatement().accept(this);
       
        
    }

    @Override
    public void visit(MethodDecl methodDecl) {
        
        for (var formal : methodDecl.formals()) {
            
        	if (formal.lineNumber==Integer.parseInt(ogLine)) {
        		type="formal";
        		methName=methodDecl.name();
                clsName=myClass;
        	}
            
        }
       

        for (var varDecl : methodDecl.vardecls()) {
        	if (varDecl.lineNumber==Integer.parseInt(ogLine)) {
        		type="varDecl";
        		methName=methodDecl.name();
                clsName=myClass;
        		
        	}
        }
        

        
    }

    @Override
    public void visit(FormalArg formalArg) {
       
        
    }

    @Override
    public void visit(VarDecl varDecl) {
    	
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        
    }

    @Override
    public void visit(IfStatement ifStatement) {
        
    }

    @Override
    public void visit(WhileStatement whileStatement) {
        
    }

    @Override
    public void visit(SysoutStatement sysoutStatement) {
       
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

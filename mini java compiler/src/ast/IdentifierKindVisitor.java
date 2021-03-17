package ast;

public class IdentifierKindVisitor implements Visitor {
		
		
		private String type = new String();
		private String clsName = new String();
		private String methName = new String();
		private String id_name;
		
		public IdentifierKindVisitor(String clsName,String methName,String id_name) {
	        this.clsName = clsName;
	        this.methName = methName;
	        this.id_name = id_name;
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
	        program.mainClass().accept(this);
	        for (ClassDecl classdecl : program.classDecls()) {
	       
	            if(classdecl.name().equals(clsName)) {
	            	/*if(classdecl.name().equals("Tree")){
	            		System.out.println("in Tree(1)");
	            		for(var meth: classdecl.methoddecls())
	            			System.out.println("in Tree: " + meth.name());
	            	}*/
	            	classdecl.accept(this);
	            }
	            
	        }
	    }

	    @Override
	    public void visit(ClassDecl classDecl) {
	        
	        for(var field: classDecl.fields()){
	    		if(field.name().equals(this.id_name)) {
	        		field.accept(this);
	        	}
	    	}
	    	
	        for(var methodDecl: classDecl.methoddecls()){
	        	if(methodDecl.name().equals(methName)) {
	        		methodDecl.accept(this);
	        	}
	        }
	        
	        
	    }

	    @Override
	    public void visit(MainClass mainClass) {
	        
	        mainClass.mainStatement().accept(this);
	       
	        
	    }

	    @Override
	    public void visit(MethodDecl methodDecl) {
	    	for(var formal: methodDecl.formals()){
	    		if(formal.name().equals(this.id_name)) {
	        		formal.accept(this);
	        	}
	    	}
	        for (var varDecl : methodDecl.vardecls()) {
	        	/*if(methName.equals("accept") && varDecl.name().equals("v")){
	    			System.out.println("I'm in!");
	    		}*/
	        	if(varDecl.name().equals(this.id_name)) {
	        		varDecl.accept(this);
	        	}
	        }
	        methodDecl.ret().accept(this);
	        

	        
	    }

	    @Override
	    public void visit(FormalArg formalArg) {
	       
	        formalArg.type().accept(this);
	    }

	    @Override
	    public void visit(VarDecl varDecl) {
	    	if(varDecl.type() instanceof RefType){
	    		this.type = ((RefType)(varDecl.type())).id();
	    	}
	    	//if(methName.equals("accept"))
	    	//varDecl.type().accept(this);
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
	        this.type = t.id();
	    }

		
	}

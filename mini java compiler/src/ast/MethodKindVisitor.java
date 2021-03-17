package ast;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class MethodKindVisitor implements Visitor{
	
	private String oldLine = new String();
	private String clsName = new String();
	private String methName = new String();
	private List<String> classList=new ArrayList<String>();
	public MethodKindVisitor(String oldLine) {
        this.oldLine=oldLine;
    }
	public String clsName() {
		return clsName;
	}
	public String methName() {
		return methName;
	}
	
	
	// [A , B , c , D]
	@Override
	public void visit(Program program) {
		// TODO Auto-generated method stub
		for (ClassDecl classdecl : program.classDecls() ) {
	        	classdecl.accept(this);
	        }
	}

	@Override
	public void visit(ClassDecl classDecl) {
		// TODO Auto-generated method stub
		for(var methodDecl: classDecl.methoddecls()) {
			if(methodDecl.lineNumber == Integer.parseInt(oldLine)){
				clsName = classDecl.name();

			}
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
		if(methodDecl.lineNumber==Integer.parseInt(oldLine)) {
			methName = methodDecl.name();
		}
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
		
	}

	@Override
	public void visit(IfStatement ifStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SysoutStatement sysoutStatement) {
		// TODO Auto-generated method stub
		sysoutStatement.arg().accept(this);
	}

	@Override
	public void visit(AssignStatement assignStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssignArrayStatement assignArrayStatement) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AndExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LtExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AddExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SubtractExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MultExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayAccessExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ArrayLengthExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MethodCallExpr e) {
		// TODO Auto-generated method stub
	}

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

	@Override
	public void visit(ThisExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewIntArrayExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewObjectExpr e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotExpr e) {
		// TODO Auto-generated method stub
		
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

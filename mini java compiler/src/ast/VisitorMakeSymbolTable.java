package ast;
import java.util.*;
public class VisitorMakeSymbolTable implements Visitor {
	//declare types
	String INT = "ast.IntAstType";
	String BOOL = "ast.BoolAstType";
	String ARRAY = "ast.IntArrayAstType";
	String REF = "ast.RefType";
	//define name_Class,ParentClass
	String name_Class = "";
	String ParentClass = "";


	//define name_Method
	String method_name = "";

	LinkedHashMap<String,String> formals = new LinkedHashMap<>();
	LinkedHashMap<String,String> locals = new LinkedHashMap<>();
	//here to determine if we from class in varDecl or from methods
	boolean isMain = false;

	//init SymbolTable
	SymbolTable symbolTable = new SymbolTable();


	public SymbolTable getSymTable(){
		return symbolTable;
	}
	@Override
	public void visit(Program program){
		// TODO Auto-generated method stub
		program.mainClass().accept(this);
		for(ClassDecl classdecl : program.classDecls()) {
			classdecl.accept(this);
		}
	}
	@Override
	public void visit(ClassDecl classDecl) {
		// TODO Auto-generated method stub
		LinkedHashMap<String,SymbolTable.MethodSymTable> methods_with_decls_formals = new LinkedHashMap<>();
		LinkedHashMap<String,String> fields = new LinkedHashMap<>();
		isMain = false;
		name_Class = classDecl.name();
		ParentClass = classDecl.superName();
		for(var fieldDecl:classDecl.fields()) {
			fieldDecl.accept(this);
			if(fieldDecl.type().toString().contains(INT))
				fields.put(fieldDecl.name(),"int");
			if(fieldDecl.type().toString().contains(BOOL))
				fields.put(fieldDecl.name(),"boolean");
			if(fieldDecl.type().toString().contains(ARRAY))
				fields.put(fieldDecl.name(),"int[]");
			if(fieldDecl.type().toString().contains(REF)){
				RefType ref = (RefType)fieldDecl.type();
				fields.put(fieldDecl.name(),ref.id());
			}
			
		}
		LinkedHashMap<String,String> fields_temp = new LinkedHashMap<>(fields);
		for(var methodDecl:classDecl.methoddecls()){
			for(var formalDecl:methodDecl.formals()){
				if(formalDecl.type().toString().contains(INT))
					formals.put(formalDecl.name(),"int");
				if(formalDecl.type().toString().contains(BOOL))
					formals.put(formalDecl.name(),"boolean");
				if(formalDecl.type().toString().contains(ARRAY))
					formals.put(formalDecl.name(),"int[]");
				if(formalDecl.type().toString().contains(REF)){
					RefType ref = (RefType)formalDecl.type();
					formals.put(formalDecl.name(),ref.id());
				}
			}
			for(var varDecl:methodDecl.vardecls()){
				if(varDecl.type().toString().contains(INT))
					locals.put(varDecl.name(),"int");
				if(varDecl.type().toString().contains(BOOL))
					locals.put(varDecl.name(),"boolean");
				if(varDecl.type().toString().contains(ARRAY))
					locals.put(varDecl.name(),"int[]");
				if(varDecl.type().toString().contains(REF)){
					RefType ref = (RefType)varDecl.type();
					locals.put(varDecl.name(),ref.id());
				}

			}
			SymbolTable.MethodSymTable mt_sym = new SymbolTable.MethodSymTable();
			mt_sym.methodName = methodDecl.name();
			if(methodDecl.returnType().toString().contains(INT))
				mt_sym.returnType = "int";
			if(methodDecl.returnType().toString().contains(BOOL))
				mt_sym.returnType = "boolean";
			if(methodDecl.returnType().toString().contains(ARRAY))
				mt_sym.returnType = "int[]";
			if(methodDecl.returnType().toString().contains(REF)){
				RefType ref = (RefType)methodDecl.returnType();
				mt_sym.returnType = ref.id();
			}
			mt_sym.parent = null;
			LinkedHashMap<String,String> locals_temp = new LinkedHashMap<>(locals);
			LinkedHashMap<String,String> formals_temp = new LinkedHashMap<>(formals);
			mt_sym.SetFormals(formals_temp);
			mt_sym.SetLocals(locals_temp);
			methods_with_decls_formals.put(methodDecl.name(),mt_sym);
			locals.clear();
			formals.clear();
		}
		symbolTable.AddMethods_plus_field(name_Class,ParentClass,false,methods_with_decls_formals,fields_temp);
	}

	@Override
	public void visit(MainClass mainClass) {
		// TODO Auto-generated method stub
		name_Class = mainClass.name();
		isMain = true;

		SymbolTable.ClassSymTable main = new SymbolTable.ClassSymTable();
		main.className = name_Class;
		main.parentClass = null;
		main.mainClass = isMain;
		main.fields = null;
		main.methods = null;
		symbolTable.classes.put(name_Class, main);

		mainClass.mainStatement().accept(this);
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(FormalArg formalArg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDecl varDecl) {
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

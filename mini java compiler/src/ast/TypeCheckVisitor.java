package ast;
import java.io.*;
import java.util.*;

public class TypeCheckVisitor implements Visitor {
	private String currentClassName;
    private String currentFunctionName;
    private Boolean classVar;
    private Boolean functionVar;
    private String fileName;
    private File fileptr;
    private String currentVarType;
    public SymbolTable symbolTable;
    public TypeCheckVisitor(String filename,SymbolTable symbolTable){
    	this.symbolTable = symbolTable;
    	this.fileName = filename;
        try {
            
            this.fileptr = new File(fileName);
            if (!this.fileptr.exists()) {
                this.fileptr.createNewFile();
            }
            
            else {
                PrintWriter writer = new PrintWriter(this.fileptr);
                writer.print("");
                writer.close();
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
    void writeBuffer(String buffer) {
        try{
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(buffer);
            myWriter.close();
        }catch(Exception hh){
        }
    }

    private boolean check_child_type(String type1, String type2) {
        SymbolTable.ClassSymTable classSymTable1 = this.symbolTable.classes.get(type1);
        SymbolTable.ClassSymTable classSymTable2 = this.symbolTable.classes.get(type2);
        /*if(classSymTable2 == null || classSymTable1 == null){
            return false;
        }*/
        if (classSymTable2.parentClass != null) {
            while (classSymTable2.parentClass != null) {
                SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(classSymTable2.parentClass);
                //System.out.println("parent cls name = " + parentClass.className + " , clsName = " + classSymTable1.className);
                if (parentClass.mainClass) {
                    break;
                }
                if (parentClass.className.equals(classSymTable1.className)) {
                    return true;
                }
                classSymTable2 = parentClass;
            }
        }
        return false;
    }

	@Override
	public void visit(Program program) {
		// TODO Auto-generated method stub
		program.mainClass().accept(this);
		for (ClassDecl classdecl : program.classDecls()) {
            classdecl.accept(this);
        }
	}

	@Override
	public void visit(ClassDecl classDecl) {
		// TODO Auto-generated method stub
		String className;
        className = classDecl.name();
        // Check if class was declared before
        if (symbolTable.classes.containsKey(className)) {
            writeBuffer("ERROR");//writeBuffer("ERROR66");
            System.exit(-1);
        }
        // Store class in the symbol table
        symbolTable.classes.put(className, new SymbolTable.ClassSymTable());
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(className);
        curClass.className = className;

        if(classDecl.superName() != null){
            if(!symbolTable.classes.containsKey(classDecl.superName()) || classDecl.superName().equals(className)){
                writeBuffer("ERROR");//writeBuffer("ERROR74");
                System.exit(-1);
            }
            if(symbolTable.classes.get(classDecl.superName()) != null){
                if(symbolTable.classes.get(classDecl.superName()).mainClass){
                    writeBuffer("ERROR");//writeBuffer("ERROR74");
                    System.exit(-1);
                }

            }
        }
        curClass.parentClass = classDecl.superName();
        curClass.mainClass = false;
        //if(!symbolTable.contains(curClass.parentClass))
        // Set up visitor's fields to be aware where to check in the symbol table
        this.currentClassName = className;
        this.classVar = true;
        this.currentFunctionName = null;
        this.functionVar = false;
        // Visit VarDeclaration
        for (var fieldDecl : classDecl.fields()) {
            fieldDecl.accept(this);
        }
        for (var methodDecl : classDecl.methoddecls()) {
            methodDecl.accept(this);
        }
	}

	@Override
	public void visit(MainClass mainClass) {
		// TODO Auto-generated method stub
		String mainClassName = mainClass.name();
        // Check if class was declared before
        if (symbolTable.classes.containsKey(mainClassName)) {
        	writeBuffer("ERROR");//writeBuffer("ERROR100");
            System.exit(-1);
        }
        // Store class in the symbol table
        symbolTable.classes.put(mainClassName, new SymbolTable.ClassSymTable());
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(mainClassName);
        curClass.className = mainClassName;
        curClass.parentClass = null;
        curClass.mainClass = true;
        // Store main method in class symbol table
        curClass.methods.put("main", new SymbolTable.MethodSymTable());
        SymbolTable.MethodSymTable curMethod = curClass.methods.get("main");
        curMethod.methodName = "main";
        curMethod.returnType = "void";
        // Visit main parameter and store it to symbol table
        String type = "String[]";
        String param = mainClass.argsName();
        curMethod.formals.put(param, type);
        // Set up visitor's fields to be aware where to check in the symbol table
        this.currentClassName = mainClassName;
        this.classVar = false;
        this.currentFunctionName = "main";
        this.functionVar = true;
        mainClass.mainStatement().accept(this);
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		// TODO Auto-generated method stub
		methodDecl.returnType().accept(this);
		String type = this.currentVarType;
        String methodName = methodDecl.name();
        if(methodName.equals("this")){
            writeBuffer("ERROR");//writeBuffer("ERROR133");
            System.exit(-1);
        }
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
        if (curClass.methods.containsKey(methodName)) {
            writeBuffer("ERROR");//writeBuffer("ERROR138");
            System.exit(-1);
        }
        // Store method to the symbol table
        curClass.methods.put(methodName, new SymbolTable.MethodSymTable());
        SymbolTable.MethodSymTable curMethod = curClass.methods.get(methodName);
        curMethod.methodName = methodName;
        curMethod.returnType = type;
        //curMethod.override = false;
        // Set up visitor's fields to be aware where to check in the symbol table
        this.classVar = false;
        this.currentFunctionName = methodName;
        this.functionVar = false;
        // Visit ParameterList
        for (var formal : methodDecl.formals()) {
            formal.accept(this);
        }
        // If the method's class is extended of another class
        /*while (curClass.parentClass != null) {
            SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(curClass.parentClass);
            // If the method with the same name was declared in parent class
            // Then OVERRIDE is only allowed and NOT overloading
            if (parentClass.methods.containsKey(methodName)) {
                // Now must be compared return types, and parameters.
                SymbolTable.MethodSymTable parentMethod = parentClass.methods.get(methodName);

                if((curMethod.returnType.equals("int") || curMethod.returnType.equals("int[]") 
                    || curMethod.returnType.equals("boolean")) && (parentMethod.returnType.equals("int") || parentMethod.returnType.equals("int[]") 
                    || parentMethod.returnType.equals("boolean"))){
                    if(!parentMethod.returnType.equals(curMethod.returnType)){
                        writeBuffer("ERROR");//writeBuffer("ERROR167");
                        System.exit(-1);
                    }

                }

                if(!curMethod.returnType.equals("int") && !curMethod.returnType.equals("int[]") 
                    && !curMethod.returnType.equals("boolean") && !parentMethod.returnType.equals("int") && !parentMethod.returnType.equals("int[]") 
                    && !parentMethod.returnType.equals("boolean")&& !parentMethod.returnType.equals(curMethod.returnType)){

                    System.out.println("cur mtd ret = " + curMethod.returnType + " , parent mthd retype = " + parentMethod.returnType);

                    if(!check_child_type(parentMethod.returnType, curMethod.returnType)){
                        writeBuffer("ERROR");//writeBuffer("ERROR167");
                        System.exit(-1);
                    }
                }

                // Compare number of parameters
                if (curMethod.formals.size() != parentMethod.formals.size()){
                	writeBuffer("ERROR");//writeBuffer("ERROR174");
            		System.exit(-1);
                }
                // Compare the type of the parameters
                Iterator iterator1 = curMethod.formals.keySet().iterator();
                Iterator iterator2 = parentMethod.formals.keySet().iterator();
                while (iterator1.hasNext() && iterator2.hasNext()) {
                    String key = iterator1.next().toString();
                    String value1 = curMethod.formals.get(key);
                    key = iterator2.next().toString();
                    String value2 = parentMethod.formals.get(key);
                    if (!value1.equals(value2)) {
                    	writeBuffer("ERROR");//writeBuffer("ERROR186");
            			System.exit(-1);
                    }
                }
                curMethod.override = true;
            }
            curClass = parentClass;
        }*/
        // Set up visitor's fields to be aware where to check in the symbol table
        this.classVar = false;
        this.currentFunctionName = methodName;
        this.functionVar = true;
        // Visit VarDeclaration
        for (var varDecl : methodDecl.vardecls()) {
            varDecl.accept(this);
        }

	}

	@Override
	public void visit(FormalArg formalArg) {
		// TODO Auto-generated method stub
		formalArg.type().accept(this);
		String type = this.currentVarType;
        String identifier = formalArg.name();
        if(identifier.equals("this")){
            writeBuffer("ERROR");//writeBuffer("ERROR211");
            System.exit(-1);
        }
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
        SymbolTable.MethodSymTable curMethod = curClass.methods.get(this.currentFunctionName);
        if (curMethod.formals.containsKey(identifier)) {
            writeBuffer("ERROR");//writeBuffer("ERROR217");
            System.exit(-1);
        }
        curMethod.formals.put(identifier, type);
	}

	@Override
	public void visit(VarDecl varDecl) {
		// TODO Auto-generated method stub
		varDecl.type().accept(this);
		String type = this.currentVarType;
        String identifier = varDecl.name();
        if(identifier.equals("this")){
            writeBuffer("ERROR");//writeBuffer("ERROR230");
            System.exit(-1);
        }


        // Class variable
        if (this.classVar) {
            SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
            if (curClass.fields.containsKey(identifier)) {
                writeBuffer("ERROR");//writeBuffer("ERROR239");
            	System.exit(-1);
            }

            while (curClass.parentClass != null) {
                SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(curClass.parentClass);
                if(parentClass.fields.containsKey(identifier)){
                    writeBuffer("ERROR");//writeBuffer("ERROR246");
                    System.exit(-1);
                }
                curClass = parentClass;
            }

            curClass.fields.put(identifier, type);
        }
        // Method variable
        if (this.functionVar) {
            SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
            SymbolTable.MethodSymTable curMethod = curClass.methods.get(this.currentFunctionName);
            // Check if it is parameter
            if (curMethod.formals.containsKey(identifier)) {
                writeBuffer("ERROR");//writeBuffer("ERROR260");
            	System.exit(-1);
            }
            // Check if it is in body variable
            if (curMethod.locals.containsKey(identifier)) {
                writeBuffer("ERROR");//writeBuffer("ERROR265");
            	System.exit(-1);
            }
            curMethod.locals.put(identifier, type);
        }
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
		this.currentVarType = "int";

	}

	@Override
	public void visit(BoolAstType t) {
		// TODO Auto-generated method stub
		this.currentVarType = "boolean";
	}

	@Override
	public void visit(IntArrayAstType t) {
		// TODO Auto-generated method stub
		this.currentVarType = "int[]";

	}

	@Override
	public void visit(RefType t) {
		// TODO Auto-generated method stub
		this.currentVarType = t.id();

		
	}

}

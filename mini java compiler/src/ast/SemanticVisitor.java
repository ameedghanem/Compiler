package ast;
import java.io.*;
import java.util.*;
public class SemanticVisitor implements Visitor {
	private String currentClassName;
    private String currentFunctionName;
    private String exprType;
    private Boolean returnPrimaryExpr;
    private String ret_Type;
    boolean identifier_init;
    private String mth_call;
    private String fileName;
    private File fileptr;
    private ArrayList<String> methodArgs;
    public SymbolTable symbolTable;
    public HashSet<String> method_init;
    public HashSet<String> class_init;
    HashSet method_copy;
    int cond_count;
    boolean debug;


    public SemanticVisitor(String filename,SymbolTable symbolTable){
    	this.symbolTable = symbolTable;
    	this.fileName = filename;
        this.method_init = new HashSet<String>();
        this.class_init = new HashSet<String>();
        this.method_copy = new HashSet<String>();
        this.debug = false;
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

    private boolean check_child_type(String type1, String type2) {
        SymbolTable.ClassSymTable classSymTable1 = this.symbolTable.classes.get(type1);
        SymbolTable.ClassSymTable classSymTable2 = this.symbolTable.classes.get(type2);
        if (classSymTable2.parentClass != null) {
            while (classSymTable2.parentClass != null) {
                SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(classSymTable2.parentClass);
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


    void writeBuffer(String buffer) {
        try{
            FileWriter myWriter = new FileWriter(fileName);
            myWriter.write(buffer);
            myWriter.close();
        }catch(Exception hh){
            System.out.println("hh33");
        }
    }
    private String get_identifier_data(String identifier, SymbolTable symbolTable) throws Exception {
        // Lookup if this identifier is declared before
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
        SymbolTable.MethodSymTable curMethod = curClass.methods.get(this.currentFunctionName);
        // If you find it one of the below cases, return its type
        // Check if this identifier is a parameter or a variable
        if (curMethod.formals.containsKey(identifier)) {
            return curMethod.formals.get(identifier);
        }
        if (curMethod.locals.containsKey(identifier)) {
            //System.out.println("id = "+ identifier + " type =  " + curMethod.locals.get(identifier));
            return curMethod.locals.get(identifier);
        }
        // Check if it is field in the class
        if (curClass.fields.containsKey(identifier)) {
            return curClass.fields.get(identifier);
        }
        // Check if it has parent class with this field
        while (curClass.parentClass != null) {
            SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(curClass.parentClass);
            if (parentClass.fields.containsKey(identifier)) {
                return parentClass.fields.get(identifier);
            }
            curClass = parentClass;
        }
        return null;
    }

    // This method look up for methods given a 'methodName' and returns the type and the class that belongs
    private String[] look_up_methods(String methodName, String className, SymbolTable symbolTable) {
        SymbolTable.ClassSymTable classSym = symbolTable.classes.get(className);
        if (classSym.methods.containsKey(methodName)) {
            // Return your type and class name
            return new String[]{classSym.methods.get(methodName).returnType, classSym.className};

        }
        // Check if this method is in parent class
        while (classSym.parentClass != null) {
            SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(classSym.parentClass);
            if (parentClass.methods.containsKey(methodName)) {
                // Return your type and class name
                return new String[]{parentClass.methods.get(methodName).returnType, parentClass.className};
            }
            classSym = parentClass;
        }
        return null;
    }

        // This method compares the parameters of a method with a list of types that the visitors extracted
    private void check_args_method(String methodName, String className, ArrayList args, SymbolTable symbolTable) throws Exception {
        SymbolTable.ClassSymTable classSym = symbolTable.classes.get(className);
        SymbolTable.MethodSymTable methodSymTable = classSym.methods.get(methodName);
        // If the args array is empty
        //System.out.println("method name = " + methodName + " class name = " + className + " size = " + methodSymTable.formals.size());
        if (args.isEmpty()) {
            if (!methodSymTable.formals.isEmpty()) {
            	if(debug) writeBuffer("ERROR 133");//writeBuffer("ERROR 90");
                else writeBuffer("ERROR");
            	System.exit(-1);
            }
        } else {
            if (methodSymTable.formals.size() != args.size()) {
            	if(debug) writeBuffer("ERROR 138");//writeBuffer("ERROR 95");
                else writeBuffer("ERROR");
            	System.exit(-1);
            }
            // Type check arguments
            for (int i = 0; i < args.size(); i++) {
                String value = (new ArrayList<>(methodSymTable.formals.values())).get(i);
                // Check if the types are the same
                String argType = args.get(i).toString();
                if (argType.equals(value)) {
                    continue;
                }
                // The only case that this is allowed is when the parameter is type of subclass of the declared parameter
                // Do not execute the below for primitive types
                if (!argType.equals("int") && !argType.equals("int[]") && !argType.equals("boolean")) {
                    boolean foundType = false;
                    SymbolTable.ClassSymTable tempSym = symbolTable.classes.get(argType);
                    // Iterate your parents
                    while (tempSym.parentClass != null) {
                        SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(tempSym.parentClass);
                        // If you found a parent with this type name
                        if (parentClass.className.equals(value)) {
                            // Return your type and class name
                            foundType = true;
                            break;
                        }
                        tempSym = parentClass;
                    }
                    // Continue to the next argument
                    if (foundType) {
                        continue;
                    }
                }
                // If you are here then a parameter is wrong...
                if(debug) writeBuffer("ERROR 171");//writeBuffer("ERROR 128");
                else writeBuffer("ERROR");
            	System.exit(-1);
            }
        }
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
		this.currentClassName = classDecl.name();
        // Visit MethodDeclaration
        if(classDecl.superName() == null){
            this.class_init.clear();
        }else{
            SymbolTable.ClassSymTable tempSym = symbolTable.classes.get(classDecl.superName());
            for(var x: tempSym.fields.entrySet()){
                String y = x.getKey();
                this.class_init.add(y);
            }
        }

        for(var field: classDecl.fields()){
            //this.method_init.add(field.name());
            this.class_init.add(field.name());
        }
        for (var methodDecl : classDecl.methoddecls()) {
            methodDecl.accept(this);
        }
        
	}

	@Override
	public void visit(MainClass mainClass) {
		// TODO Auto-generated method stub
		this.currentClassName = mainClass.name();
        this.currentFunctionName = "main";
        // Visit Statement
        mainClass.mainStatement().accept(this);
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		// TODO Auto-generated method stub
		this.currentFunctionName = methodDecl.name();
        String methodName = methodDecl.name();
        // Visit Statement
        this.method_init.clear();
        for(var formal: methodDecl.formals()){
            this.method_init.add(formal.name());
        }
        for(var varDecl: methodDecl.vardecls()){
            if(this.class_init.contains(varDecl.name())){
                class_init.remove(varDecl.name());
            }
        }
       	for (var stmt : methodDecl.body()) {
            stmt.accept(this);
        }
        SymbolTable.ClassSymTable classSym = symbolTable.classes.get(this.currentClassName);
        String methodType = classSym.methods.get(this.currentFunctionName).returnType;
        this.exprType = methodType;
        identifier_init = false;
        methodDecl.ret().accept(this);
        if(!identifier_init){
            if(debug) writeBuffer("ERROR 236");//writeBuffer("ERROR 128");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        String retType = this.ret_Type;
        //System.out.println("hehe");
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
        SymbolTable.MethodSymTable curMethod = curClass.methods.get(methodName);
        curMethod.override = false;
        while (curClass.parentClass != null) {
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
                        if(debug) writeBuffer("ERROR 256");//writeBuffer("ERROR167");
                        else writeBuffer("ERROR");
                        System.exit(-1);
                    }

                }

                if(!curMethod.returnType.equals("int") && !curMethod.returnType.equals("int[]") 
                    && !curMethod.returnType.equals("boolean") && !parentMethod.returnType.equals("int") && !parentMethod.returnType.equals("int[]") 
                    && !parentMethod.returnType.equals("boolean")&& !parentMethod.returnType.equals(curMethod.returnType)){

                    

                    if(!check_child_type(parentMethod.returnType, curMethod.returnType)){
                        if(debug) writeBuffer("ERROR 269");//writeBuffer("ERROR167");
                        else writeBuffer("ERROR");
                        System.exit(-1);
                    }
                }

                //System.out.println("formal size = " + curMethod.formals.size());
                //System.out.println("parnt formal size = " + parentMethod.formals.size());

                // Compare number of parameters
                if (curMethod.formals.size() != parentMethod.formals.size()){
                    if(debug) writeBuffer("ERROR 279");//writeBuffer("ERROR174");
                    else writeBuffer("ERROR");
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
                        if(debug) writeBuffer("ERROR 291");//writeBuffer("ERROR186");
                        else writeBuffer("ERROR");
                        System.exit(-1);
                    }
                }
                curMethod.override = true;
            }
            curClass = parentClass;
        }
        // If the return type equals with the method type
        if (retType.equals(methodType)) {
            return;
        }
        if(!retType.equals("int") && !retType.equals("int[]") 
                    && !retType.equals("boolean")) {

            if(check_child_type(methodType, retType))
                return;
        }
        // Check if it has parent with that type
        /*boolean typeFound = false;
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClassName);
        // Check if it has parent with that type
        while (curClass.parentClass != null) {
            if (methodType.equals(curClass.parentClass)) {
                typeFound = true;
                break;
            }
            curClass = symbolTable.classes.get(curClass.parentClass);
        }
        if(typeFound) {
            return;
        }*/
        if(debug) writeBuffer("ERROR 323");//writeBuffer("ERROR 194");
        else writeBuffer("ERROR");
        System.exit(-1);
	}

	@Override
	public void visit(FormalArg formalArg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDecl varDecl) {
		// TODO Auto-generated method stub
        varDecl.type().accept(this);
        String type = ret_Type;
        if(!symbolTable.classes.containsKey(type)){
            if(debug) writeBuffer("ERROR 339");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
		
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
        
        identifier_init = false;
		ifStatement.cond().accept(this);
        if(cond_count > 0){
            method_copy = new HashSet<String>(this.method_copy);
        }else{
            method_copy = new HashSet<String>(this.method_init);
        }
        cond_count++;
		String type = ret_Type;
        if (!type.equals("boolean")) {
        	if(debug )writeBuffer("ERROR 362");//writeBuffer("ERROR 225");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!identifier_init){
            if(debug )writeBuffer("ERROR 366");//writeBuffer("ERROR 225");
            else writeBuffer("ERROR");
            System.exit(-1); 
        }

        // Visit statements
        ifStatement.thencase().accept(this);
        HashSet<String> intersection1 = new HashSet<String>(method_copy);
        if(cond_count > 1){
            method_copy = new HashSet<String>(this.method_copy);
        }else{
            method_copy = new HashSet<String>(this.method_init);
        }
        ifStatement.elsecase().accept(this);
        HashSet<String> intersection2 = new HashSet<String>(method_copy);
        HashSet<String> intersection = new HashSet<String>(intersection1);
        intersection.retainAll(intersection2);
        this.method_init = intersection;
        method_copy = new HashSet<String>(this.method_init);
        cond_count--;
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		// TODO Auto-generated method stub
        // TODO Auto-generated method stub
        identifier_init = false;
		whileStatement.cond().accept(this);

        if(cond_count > 0){
            method_copy = new HashSet<String>(this.method_copy);
        }else{
            method_copy = new HashSet<String>(this.method_init);
        }

        cond_count++;
		String type = ret_Type;
        if (!type.equals("boolean")) {
        	if(debug) writeBuffer("ERROR 396");//writeBuffer("ERROR 240");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!identifier_init){
            if(debug) writeBuffer("ERROR 400");//writeBuffer("ERROR 225");
            else writeBuffer("ERROR");
            System.exit(-1); 
        }
        // Visit statements
        whileStatement.body().accept(this);
        if(cond_count > 1){
            method_copy = new HashSet<String>(this.method_copy);
        }else{
            method_copy = new HashSet<String>(this.method_init);
        }
        cond_count--;

	}

	@Override
	public void visit(SysoutStatement sysoutStatement) {
		// TODO Auto-generated method stub
        this.exprType = "int";
        identifier_init = false;
        sysoutStatement.arg().accept(this);
        String type = ret_Type;
        if (!type.equals("int")) {
            if(debug) writeBuffer("ERROR 421");//writeBuffer("ERROR 256");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!identifier_init){
            if(debug) writeBuffer("ERROR 425");//writeBuffer("ERROR 256");
            else writeBuffer("ERROR");
            System.exit(-1); 
        }
        this.exprType = null;
	}





	@Override
	public void visit(AssignStatement assignStatement) {
		// TODO Auto-generated method stub
		String identifier = assignStatement.lv();
        String type;
		try{
	        type = get_identifier_data(identifier, symbolTable);
	        if (type == null) {
	        	if(debug) writeBuffer("ERROR 443");//writeBuffer("ERROR 273");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }
	        this.exprType = type;
	        this.returnPrimaryExpr = false;
            identifier_init = false;
	        assignStatement.rv().accept(this);
	        String expr2Type = ret_Type;
            if(!expr2Type.equals(this.exprType)){

                if(this.exprType.equals("int") || this.exprType.equals("int[]") || this.exprType.equals("boolean")
                        || expr2Type.equals("int") || expr2Type.equals("int[]") || expr2Type.equals("boolean")){

                    if(debug) writeBuffer("ERROR 456");//writeBuffer("ERROR 324");
                    else writeBuffer("ERROR");
                    System.exit(-1);
                }

                if (!this.exprType.equals("int") && !this.exprType.equals("int[]") && !this.exprType.equals("boolean")
                        && !expr2Type.equals("int") && !expr2Type.equals("int[]") && !expr2Type.equals("boolean")) {
                    if(!check_child_type(exprType, expr2Type)){
                        if(debug) writeBuffer("ERROR 463");//writeBuffer("ERROR 316");
                        else writeBuffer("ERROR");
                        System.exit(-1);
                    }
                }
            }

                if(!identifier_init){
                    if(debug) writeBuffer("ERROR 470");//writeBuffer("ERROR 316");
                    else writeBuffer("ERROR");
                    System.exit(-1);
                }


                if(this.cond_count > 0){
                    //System.out.println("????  " + cond_count);
                    this.method_copy.add(identifier);
                }else{
                    this.method_init.add(identifier);
                }

                /*for(var x: method_init){
                    System.out.print(x + " ");
                }
                System.out.println();*/
            

	        /*if (!this.exprType.equals(expr2Type)) {
	            // The only case that this is allowed is when the left var is type of subclass of the right var
	            // Do not execute the below for primitive types
	            boolean foundType = false;
	            

	                SymbolTable.ClassSymTable tempSym = symbolTable.classes.get(expr2Type);
	                // Iterate your parents
	                while (tempSym.parentClass != null) {
	                    SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(tempSym.parentClass);
	                    // If you found a parent with this type name
                        //System.out.println("");
	                    if (parentClass.className.equals(this.exprType)) {
	                        // Return your type and class name
	                        foundType = true;
	                        break;
	                    }
	                    tempSym = parentClass;
	                }
	                // Continue to the next argument
	            if (!foundType) {
	            	writeBuffer("ERROR 303");
	           		System.exit(-1);
	            }
	        }*/
	        //this.exprType = null;
    	}catch(Exception e_Eeeeeeeee){

    	}
	}
	@Override
	public void visit(AssignArrayStatement assignArrayStatement) {
		// TODO Auto-generated method stub
		String identifier = assignArrayStatement.lv();
        boolean vflag = false;
		try{
	        String type = get_identifier_data(identifier, symbolTable);
	        if (type == null) {
	            if(debug) writeBuffer("ERROR 518");//writeBuffer("ERROR 319");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }
	        if (!type.equals("int[]")) {
	            if(debug) writeBuffer("ERROR 522");//writeBuffer("ERROR 323");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }
            identifier_init = false;
	        assignArrayStatement.index().accept(this);
            if(!identifier_init){
                if(debug) writeBuffer("ERROR 528");//writeBuffer("ERROR 339");
                else writeBuffer("ERROR");
                System.exit(-1);
            }
            //vflag = identifier_init;

	        String expr1Type = ret_Type;
	        if (!expr1Type.equals("int")) {
	        	if(debug) writeBuffer("ERROR 535");//writeBuffer("ERROR 331");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }
            identifier_init = false;
	        assignArrayStatement.rv().accept(this);
            if(!identifier_init){
                if(debug) writeBuffer("ERROR 541");//writeBuffer("ERROR 339");
                else writeBuffer("ERROR");
                System.exit(-1);
            }

	        String expr2Type = ret_Type;
	        if (!expr2Type.equals("int")) {
	        	if(debug) writeBuffer("ERROR 547");//writeBuffer("ERROR 339");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }         

                if(cond_count==0 && !(this.method_init.contains(identifier) || this.class_init.contains(identifier))){
                    if(debug) writeBuffer("ERROR 552");//writeBuffer("ERROR 316");
                    else writeBuffer("ERROR");
                    System.exit(-1);
                }
                if(cond_count > 0 && !(this.method_copy.contains(identifier) || this.class_init.contains(identifier))){
                    if(debug) writeBuffer("ERROR 556");//writeBuffer("ERROR 316");
                    else writeBuffer("ERROR");
                    System.exit(-1);
                }
            /*if(assignArrayStatement.rv() instanceof NewIntArrayExpr){
                method_init.add(identifier, true);
            }*/
            //identifier_init = true;

    	}catch(Exception e_Eeeeeeeee){

    	}

        
	}

	@Override
	public void visit(AndExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e1().accept(this);
        boolean vflag = identifier_init;
		String type1 = ret_Type;
        identifier_init = false;
		e.e2().accept(this);
        String type2 = ret_Type;
        if (!type1.equals("boolean") || !type2.equals("boolean")) {
            if(debug) writeBuffer("ERROR 582");//writeBuffer("ERROR 355");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 586");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "boolean"; 
	}

	@Override
	public void visit(LtExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e1().accept(this);
        boolean vflag = identifier_init;
        String type1 = ret_Type;
        identifier_init = false;
        e.e2().accept(this);
        String type2 = ret_Type;
        if (!type1.equals("int") || !type2.equals("int")) {
        	if(debug) writeBuffer("ERROR 604");//writeBuffer("ERROR 371");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 608");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "boolean";
	}

	@Override
	public void visit(AddExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e1().accept(this);
        boolean vflag = identifier_init;
        String type1 = ret_Type;
        identifier_init = false;
        e.e2().accept(this);
        String type2 = ret_Type;
        if (!type1.equals("int") || !type2.equals("int")) {
        	if(debug) writeBuffer("ERROR 626");//writeBuffer("ERROR 388");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 630");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "int";
	}

	@Override
	public void visit(SubtractExpr e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e1().accept(this);
        boolean vflag = identifier_init;
        String type1 = ret_Type;
        identifier_init = false;
        e.e2().accept(this);

        /*if(e.e1() instanceof IdentifierExpr){
            System.out.println("e1 = " + (((IdentifierExpr)(e.e1())).id()));
        }
        if(e.e2() instanceof IdentifierExpr){
            System.out.println("e2 = " + (((IdentifierExpr)(e.e2())).id()));
        }*/

        String type2 = ret_Type;
        if (!type1.equals("int") || !type2.equals("int")) {
        	if(debug) writeBuffer("ERROR 649");//writeBuffer("ERROR 421");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 653");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "int";
	}

	@Override
	public void visit(MultExpr e) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e1().accept(this);
        boolean vflag = identifier_init;
        String type1 = ret_Type;
        identifier_init = false;
        e.e2().accept(this);
        String type2 = ret_Type;
        if (!type1.equals("int") || !type2.equals("int")) {
        	if(debug) writeBuffer("ERROR 672");//writeBuffer("ERROR 439");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 676");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "int";
	}

	@Override
	public void visit(ArrayAccessExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.arrayExpr().accept(this);
        boolean vflag = identifier_init;
		String type1 = ret_Type;
        if (!type1.equals("int[]")) {
        	if(debug) writeBuffer("ERROR 691");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        identifier_init = false;
        e.indexExpr().accept(this);
        String type2 = ret_Type;
        if (!type2.equals("int")) {
        	if(debug) writeBuffer("ERROR 698");//writeBuffer("ERROR 458");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        //System.out.println(" id  = " + vflag + " index =  " + identifier_init);
        if(!(identifier_init && vflag)){
            if(debug) writeBuffer("ERROR 703");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "int";
	}

	@Override
	public void visit(ArrayLengthExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.arrayExpr().accept(this);
		String type = ret_Type;
        if (!type.equals("int[]")) {
            if(debug) writeBuffer("ERROR 717");//writeBuffer("ERROR 470");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        if(!identifier_init){
            if(debug) writeBuffer("ERROR 721");//writeBuffer("ERROR 452");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
        ret_Type = "int";
	}


    public HashMap<String, Boolean> union_latice(HashMap<String, Boolean> h1, HashMap<String, Boolean> h2){
        HashMap hu = new HashMap<String, Boolean>();
        for(Map.Entry<String, Boolean> m1: h1.entrySet()){
            boolean flag = false;
            String m1_key = m1.getKey();
            boolean m1_value = m1.getValue();
            if(m1_value == h2.get(m1_key)){
                flag = m1_value;
            }else{
                flag = true;
            }
            hu.put(m1_key, flag);
        }
        return hu;
    }


	@Override
	public void visit(MethodCallExpr e) {
		// TODO Auto-generated method stub
		this.returnPrimaryExpr = true;
		try{
	        // Visit PrimaryExpression
            if(!( (e.ownerExpr() instanceof ThisExpr) || (e.ownerExpr() instanceof NewObjectExpr) || (e.ownerExpr() instanceof IdentifierExpr)  )){
                if(debug) writeBuffer("ERROR 753");//writeBuffer("ERROR 501");
                else writeBuffer("ERROR");
                System.exit(-1);
            }
            identifier_init = false;
	        e.ownerExpr().accept(this);
            if(identifier_init == false){
                if(debug) writeBuffer("ERROR 759");//writeBuffer("ERROR 501");
                else writeBuffer("ERROR");
                System.exit(-1);
            }
	        String variable = ret_Type;
	        String varType;
	        // Use current Class type if 'this' is used
	        if (variable.equals("this")) {
	            varType = this.currentClassName;
	        } else {
	            varType = get_identifier_data(variable, symbolTable);
	        }
	        // The only case that is allowed without object name
	        // Is when the object is created in the same line
	        if (varType == null) {
	            if (!symbolTable.classes.containsKey(variable)) {

	               	if(debug) writeBuffer("ERROR 775");//writeBuffer("ERROR 518");
                    else writeBuffer("ERROR");
	           		System.exit(-1);
	            } else {
	                varType = variable;
	            }

	        }

	        String methodName = e.methodId();
	        // Lookup if method exists
	        String[] retValues = look_up_methods(methodName, varType, symbolTable);
	        if (retValues == null) {
	            if(debug) writeBuffer("ERROR 787");//writeBuffer("ERROR 530");
                else writeBuffer("ERROR");
	           	System.exit(-1);
	        }
	        String methodType = retValues[0];
	        String className = retValues[1];

	        // Create an array to hold up the types of parameters
	        // If it is already created then it is assumed where are in nested call
	        // Hold the data of the current array and create a new one to type check the nested call
	        ArrayList<String> backupMethodArgs = null;
	        boolean methodArgsTempFlag = false;
	        if (this.methodArgs != null) {
	            methodArgsTempFlag = true;
	            backupMethodArgs = new ArrayList<>(this.methodArgs);
	        }
	        this.methodArgs = new ArrayList<>();
	        for (Expr arg : e.actuals()) {
                identifier_init = false;
	            arg.accept(this);
                if(!identifier_init){
                    if(debug) writeBuffer("ERROR 807");//writeBuffer("ERROR 530");
                    else writeBuffer("ERROR");
                    System.exit(-1);
                }
                this.methodArgs.add(ret_Type);
	        }
	        // Lookup arg types with the parameter types that are defined in the method that is called
	        check_args_method(methodName, className, this.methodArgs, symbolTable);

	        // Erase the array
	        this.methodArgs = null;
	        // Restore previous array if was existed before the methodCall visit
	        if (methodArgsTempFlag) {
	            this.methodArgs = new ArrayList<>(backupMethodArgs);
	        }
	        this.returnPrimaryExpr = false;
	        ret_Type = methodType;
            identifier_init = true;
    	}catch(Exception e_Eeeeeeeee)
    	{}
	}

	@Override
	public void visit(IntegerLiteralExpr e) {
		// TODO Auto-generated method stub
		ret_Type = "int";
        identifier_init = true;
	}

	@Override
	public void visit(TrueExpr e) {
		// TODO Auto-generated method stub
		ret_Type = "boolean";
        identifier_init = true;
	}

	@Override
	public void visit(FalseExpr e) {
		// TODO Auto-generated method stub
		ret_Type = "boolean";
        identifier_init = true;
	}

	@Override
	public void visit(IdentifierExpr e) {
		// TODO Auto-generated method stub
        if(cond_count==0 && (this.method_init.contains(e.id()) || this.class_init.contains(e.id()))){
            identifier_init = true;
        }
        if(cond_count>0 && (this.method_copy.contains(e.id()) || this.class_init.contains(e.id()))){
            identifier_init = true;
        }
        try{
		  ret_Type = get_identifier_data(e.id(), symbolTable);
          if(ret_Type == null){
            if(debug) writeBuffer("ERROR 861");//writeBuffer("ERROR 589");
            else writeBuffer("ERROR");
            System.exit(-1);
          }
        }catch(Exception ert){}	
	}

	@Override
	public void visit(ThisExpr e) {
		// TODO Auto-generated method stub
		ret_Type = this.currentClassName;
        identifier_init = true;
	}

	@Override
	public void visit(NewIntArrayExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.lengthExpr().accept(this);
        if(!ret_Type.equals("int")){
            if(debug) writeBuffer("ERROR 880");//writeBuffer("ERROR 589");
            else writeBuffer("ERROR");
            System.exit(-1); 
        }
        identifier_init = true;
        ret_Type = "int[]";
	}

	@Override
	public void visit(NewObjectExpr e) {
		// TODO Auto-generated method stub
		ret_Type = e.classId();
        if(!symbolTable.classes.containsKey(ret_Type)){
            if(debug) writeBuffer("ERROR 892");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
        identifier_init = true;
	}

	@Override
	public void visit(NotExpr e) {
		// TODO Auto-generated method stub
        identifier_init = false;
		e.e().accept(this);
        if(!identifier_init){
            if(debug) writeBuffer("ERROR 904");//writeBuffer("ERROR 575");
            else writeBuffer("ERROR");
            System.exit(-1);
        }
		String type = ret_Type;
        if (!type.equals("boolean")) {
            if(debug) writeBuffer("ERROR 909");//writeBuffer("ERROR 575");
            else writeBuffer("ERROR");
           	System.exit(-1);
        }
        ret_Type = "boolean";
	}

	@Override
	public void visit(IntAstType t) {
		// TODO Auto-generated method stub
		ret_Type = "int";
	}

	@Override
	public void visit(BoolAstType t) {
		// TODO Auto-generated method stub
		ret_Type = "boolean";
	}

	@Override
	public void visit(IntArrayAstType t) {
		// TODO Auto-generated method stub
		ret_Type = "int[]";
	}

	@Override
	public void visit(RefType t) {
		// TODO Auto-generated method stub
		ret_Type = t.id();
	}

}

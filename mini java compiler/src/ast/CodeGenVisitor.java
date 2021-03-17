package ast;
import java.io.*;
import java.util.*;

public class CodeGenVisitor implements Visitor {
	private VTables vTables;
    private SymbolTable symbolTable;
    private String fileName;
    private File fileptr;
    private int register;
    private int loopLabel;
    private int ifLabel;
    private int andLabel;
    private int boundsLabel;
    private int arrAllocLabel;
    private String currentClass;
    private String currentMethod;
    private String currentVarType;
    private String latestRegister;
    private boolean returnFieldValue;
    private LinkedHashMap<String, String> registerTypes;
    private ArrayList<String> methodArgs;
    
    public CodeGenVisitor(String fileName,SymbolTable symbolTable, VTables vTables){
    	this.register = 0;
        this.loopLabel = 0;
        this.ifLabel = 0;
        this.andLabel = 0;
        this.boundsLabel = 0;
        this.arrAllocLabel = 0;
        this.vTables = vTables;
        this.symbolTable = symbolTable;
        this.fileName = fileName;
        this.returnFieldValue = false;
        this.registerTypes = new LinkedHashMap<>();
        
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
        try {
            FileWriter fw = new FileWriter(this.fileptr, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.print(buffer);
            pw.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    String get_register() {
        int retVal = this.register;
        this.register++;
        return "%_" + retVal;
    }

    String get_loop_label() {
        int retVal = this.loopLabel;
        this.loopLabel++;
        return "loop" + retVal;
    }

    String get_if_label() {
        int retVal = this.ifLabel;
        this.ifLabel++;
        return "if" + retVal;
    }

    String get_and_label() {
        int retVal = this.andLabel;
        this.andLabel++;
        return "andclause" + retVal;
    }

    String get_bound_label() {
        int retVal = this.boundsLabel;
        this.boundsLabel++;
        return "oob" + retVal;
    }

    String get_arr_alloc_label() {
        int retVal = this.arrAllocLabel;
        this.arrAllocLabel++;
        return "arr_alloc" + retVal;
    }

    private String[] get_identifier_data(String identifier, SymbolTable symbolTable) throws Exception {
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(this.currentClass);
        SymbolTable.MethodSymTable curMethod = curClass.methods.get(this.currentMethod);
        if (curMethod.formals.containsKey(identifier)) {
            return new String[]{curMethod.formals.get(identifier), "parameter", curMethod.methodName};
        }
        if (curMethod.locals.containsKey(identifier)) {
            return new String[]{curMethod.locals.get(identifier), "variable", curMethod.methodName};
        }
        if (curClass.fields.containsKey(identifier)) {
        	
            return new String[]{curClass.fields.get(identifier), "field", curClass.className};
        }
        while (curClass.parentClass != null) {
            SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(curClass.parentClass);
            if (parentClass.fields.containsKey(identifier)) {
                return new String[]{parentClass.fields.get(identifier), "field", parentClass.className};
            }
            curClass = parentClass;
        }
        throw new Exception("Identifier not found!");
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

    
    private int get_method_vtable_position(String identifier, String type) throws Exception {
        int parentsMethods = 0;
        int position = 0;
        boolean foundAtParent = false;
        int parentPosition = 0;
        SymbolTable.ClassSymTable classSymTable = this.symbolTable.classes.get(type);
        SymbolTable.ClassSymTable backupSymTable = classSymTable;
        if (classSymTable.parentClass != null) {
            while (classSymTable.parentClass != null) {
                SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(classSymTable.parentClass);
                if (parentClass.mainClass) {
                    break;
                }
                for (Map.Entry classEntryMethods : parentClass.methods.entrySet()) {
                    String name = classEntryMethods.getKey().toString();
                    if (name.equals(identifier)) {
                        parentPosition = parentsMethods;
                        foundAtParent = true;
                    }
                    parentsMethods++;
                    classSymTable = parentClass;
                }
            }
        }
        position = parentsMethods;
        classSymTable = backupSymTable;
        for (Map.Entry classEntryMethods : classSymTable.methods.entrySet()) {
            String name = classEntryMethods.getKey().toString();
            if (name.equals(identifier)) {
                foundAtParent = false;
                break;
            }
            position++;
        }
        if (foundAtParent) {
            position = parentPosition;
        }
        return position;
    }

    private int get_number_of_methods(String className, SymbolTable symbolTable) throws Exception {
        SymbolTable.ClassSymTable classSymTable = symbolTable.classes.get(className);
        int numberOfMethods = classSymTable.methods.size();
        return numberOfMethods;
    }

    private int get_offset(String identifier, String type, VTables vTables) throws Exception {
        VTables.ClassVTable classVTable = vTables.classesTables.get(type);
        // Check if it is field
        if (classVTable.fieldsTable.containsKey(identifier)) {
            int offset = Integer.parseInt(classVTable.fieldsTable.get(identifier).toString());
            offset += 8;
            return offset;
        }
        
        else if (classVTable.methodsTable.containsKey(identifier)) {
            int offset = Integer.parseInt(classVTable.methodsTable.get(identifier).toString());
            offset /= 8;
            return offset;
        }
        throw new Exception("Identifier not found!");
    }

    private int get_class_size(String className, SymbolTable symbolTable) {
        SymbolTable.ClassSymTable curClass = symbolTable.classes.get(className);
        int size = 0;
        
        if (curClass.parentClass != null) {
            size = get_class_size(curClass.parentClass, this.symbolTable);
        }

        for (Map.Entry classEntryFields : curClass.fields.entrySet()) {
            String type = classEntryFields.getValue().toString();
            if (type.equals("int")) {
                size += 4;
            } else if (type.equals("boolean")) {
                size += 1;
            } else {
                size += 8;
            }
        }
        size += 8;
        return size;
    }

    void llvm_create_v_tables() {
        for (Map.Entry entry : this.vTables.classesTables.entrySet()) {
            Object key = entry.getKey();
            String className = entry.getKey().toString();
            VTables.ClassVTable classVTable = this.vTables.classesTables.get(key);
            if (classVTable.isMainClass) {
                writeBuffer("@." + className + "_vtable = global [0 x i8*] []\n");
                continue;
            }
            int numberOfFuncs = classVTable.methodsTable.size();
            String buffer = "@." + className + "_vtable = global [";
            writeBuffer(buffer);
            buffer = "";
            SymbolTable.ClassSymTable classSymTable = this.symbolTable.classes.get(className);
            SymbolTable.ClassSymTable backupSymTable = classSymTable;

            boolean printComa = false;
            String extendBuffer = "";
            writeBuffer(numberOfFuncs + " x i8*] [" + extendBuffer);
            classSymTable = backupSymTable;
            className = classSymTable.className;
            for (Map.Entry classEntryMethods : classSymTable.methods.entrySet()) {
                String methodName = classEntryMethods.getKey().toString();
                SymbolTable.MethodSymTable methodSymTable = classSymTable.methods.get(methodName);
                String methodRetType = methodSymTable.returnType;
                if (printComa) {
                    buffer += ", ";
                }
                // Return type
                if (methodRetType.equals("int")) {
                    buffer += "i8* bitcast (i32 (i8*";
                } else if (methodRetType.equals("boolean")) {
                    buffer += "i8* bitcast (i1 (i8*";
                } else if (methodRetType.equals("int[]")) {
                    buffer += "i8* bitcast (i32* (i8*";
                } else {
                    buffer += "i8* bitcast (i8* (i8*";
                }
                // Set up parameters
                for (Map.Entry methodParams : methodSymTable.formals.entrySet() ){
                    String paramType = methodParams.getValue().toString();
                    if (paramType.equals("int")) {
                        buffer += ",i32";
                    } else if (paramType.equals("boolean")) {
                        buffer += ",i1";
                    } else if (paramType.equals("int[]")) {
                        buffer += ",i32*";
                    } else {
                        buffer += ",i8*";
                    }
                }
                buffer += ")* @" + className + "." + methodName + " to i8*)";

                printComa = true;
                writeBuffer(buffer);
                buffer = "";
            }
            writeBuffer("]\n");
        }
    }

    
    void llvm_helper_methods() {
        String buffer = "\n" +
                "declare i8* @calloc(i32, i32)\n" +
                "declare i32 @printf(i8*, ...)\n" +
                "declare void @exit(i32)\n" +
                "\n" +
                "@_cint = constant [4 x i8] c\"%d\\0a\\00\"\n" +
                "@_cOOB = constant [15 x i8] c\"Out of bounds\\0a\\00\"\n" +
                "define void @print_int(i32 %i) {\n" +
                "    %_str = bitcast [4 x i8]* @_cint to i8*\n" +
                "    call i32 (i8*, ...) @printf(i8* %_str, i32 %i)\n" +
                "    ret void\n" +
                "}\n" +
                "\n" +
                "define void @throw_oob() {\n" +
                "    %_str = bitcast [15 x i8]* @_cOOB to i8*\n" +
                "    call i32 (i8*, ...) @printf(i8* %_str)\n" +
                "    call void @exit(i32 1)\n" +
                "    ret void\n" +
                "}\n";
        writeBuffer(buffer);
    }
   
    @Override
    public void visit(Program program) {
    	llvm_create_v_tables();
        llvm_helper_methods();
        
        program.mainClass().accept(this);
        
        for (ClassDecl classdecl : program.classDecls()) {
            classdecl.accept(this);
           
        }
    }

    @Override
    public void visit(ClassDecl classDecl) {
        this.currentClass=classDecl.name();
        
        for (var methodDecl : classDecl.methoddecls()) {
            methodDecl.accept(this);
            
        }
        
    }

    @Override
    public void visit(MainClass mainClass) {
    	writeBuffer("\ndefine i32 @main() {\n");
    	this.currentClass = mainClass.name();
        this.currentMethod = "main";
        
        
        mainClass.mainStatement().accept(this);
        writeBuffer("\n\tret i32 0\n}\n");
        this.register = 0;
        this.loopLabel = 0;
        this.ifLabel = 0;
        this.andLabel = 0;
        this.boundsLabel = 0;
        this.arrAllocLabel = 0;
        this.registerTypes.clear();
    }

    @Override
    public void visit(MethodDecl methodDecl) {
    	String buffer = "\ndefine ";
        methodDecl.returnType().accept(this);
        String llvmMethType;
        if (this.currentVarType.equals("int")) {
            buffer += "i32";
            llvmMethType = "i32";
        } else if (this.currentVarType.equals("boolean")) {
            buffer += "i1";
            llvmMethType = "i1";
        } else if (this.currentVarType.equals("int[]")) {
            buffer += "i32*";
            llvmMethType = "i32*";
        } else {
            buffer += "i8*";
            llvmMethType = "i8*";
        }
        this.currentMethod=methodDecl.name();
        buffer += " @" + this.currentClass + "." + this.currentMethod + "(i8* %this";
        
        //setup
        for (var formal : methodDecl.formals()) {
            formal.type().accept(this);
            
            if (this.currentVarType.equals("int")) {
                buffer += ", i32";
            } else if (this.currentVarType.equals("boolean")) {
                buffer += ", i1";
            } else if (this.currentVarType.equals("int[]")) {
                buffer += ", i32*";
            } else {
                buffer += ", i8*";
            }
            buffer += " %." + formal.name();
        }
        buffer += ") {\n";
        writeBuffer(buffer);
        //allocation
        for (var formal : methodDecl.formals()) {
            formal.type().accept(this);
            buffer = "\t%" + formal.name() + " = alloca ";
            if (this.currentVarType.equals("int")) {
                buffer += "i32\n";
                buffer += "\tstore i32 %." + formal.name() + ", i32* %" + formal.name();
            } else if (this.currentVarType.equals("boolean")) {
                buffer += "i1\n";
                buffer += "\tstore i1 %." + formal.name() + ", i1* %" + formal.name();
            } else if (this.currentVarType.equals("int[]")) {
                buffer += "i32*\n";
                buffer += "\tstore i32* %." + formal.name() + ", i32** %" + formal.name();
            } else {
                buffer += "i8*\n";
                buffer += "\tstore i8* %." + formal.name() + ", i8** %" + formal.name();
            }
            buffer += "\n";
            writeBuffer(buffer);
        }
        

        for (var varDecl : methodDecl.vardecls()) {
            varDecl.accept(this);
        }
        for (var stmt : methodDecl.body()) {
            stmt.accept(this);
        }
        this.returnFieldValue = true;
        methodDecl.ret().accept(this);
        buffer = "\n\tret " + llvmMethType + " " + this.latestRegister + "\n}\n";
        writeBuffer(buffer);
        this.register = 0;
        this.loopLabel = 0;
        this.ifLabel = 0;
        this.andLabel = 0;
        this.boundsLabel = 0;
        this.arrAllocLabel = 0;
        this.registerTypes.clear();
        
    }

    @Override
    public void visit(FormalArg formalArg) {
        
    }
    

    @Override
    public void visit(VarDecl varDecl) {
        
        varDecl.type().accept(this);
        
        String buffer = "\t%" + varDecl.name() + " = alloca ";
        if (this.currentVarType.equals("int")) {
            buffer += "i32\n";
        } else if (this.currentVarType.equals("boolean")) {
            buffer += "i1\n";
        } else if (this.currentVarType.equals("int[]")) {
            buffer += "i32*\n";
        } else {
            buffer += "i8*\n";
        }
        writeBuffer(buffer);
    }

    @Override
    public void visit(BlockStatement blockStatement) {       
        for (var s : blockStatement.statements()) {    
            s.accept(this);
        }    
    }

    @Override
    public void visit(IfStatement ifStatement) {
    	String label1 = get_if_label();
        String label2 = get_if_label();
        String label3 = get_if_label();
        this.returnFieldValue = true;
        ifStatement.cond().accept(this);
        writeBuffer("\tbr i1 " + this.latestRegister + ", label %" + label1 + ", label %" + label2 + "\n");
        
        
        writeBuffer("\n" + label1 + ":\n");
        ifStatement.thencase().accept(this);
        writeBuffer("\n\tbr label %" + label3 + "\n");
        writeBuffer("\n" + label2 + ":\n");
        
        ifStatement.elsecase().accept(this);
        writeBuffer("\n\tbr label %" + label3 + "\n");
        writeBuffer("\n" + label3 + ":\n");
        
    }

    @Override
    public void visit(WhileStatement whileStatement) {
    	String loop1 = get_loop_label();
        String loop2 = get_loop_label();
        String loop3 = get_loop_label();

        writeBuffer("\n\tbr label %" + loop1 + "\n");
        writeBuffer("\n" + loop1 + ":\n");
        whileStatement.cond().accept(this);
        writeBuffer("\tbr i1 " + this.latestRegister + ", label %" + loop2 + ", label %" + loop3 + "\n");

        writeBuffer("\n" + loop2 + ":\n");
        whileStatement.body().accept(this);
        writeBuffer("\n\tbr label %" + loop1 + "\n");

        writeBuffer("\n" + loop3 + ":\n");
        
    }

    @Override
    public void visit(SysoutStatement sysoutStatement) {
    	this.returnFieldValue = true;
    	sysoutStatement.arg().accept(this);
        writeBuffer("\tcall void (i32) @print_int(i32 " + this.latestRegister + ")\n");
        
        
    }

    @Override
    public void visit(AssignStatement assignStatement) {
    	String buffer;
        String identifier = assignStatement.lv();
        try {
	        String results[] = get_identifier_data(identifier, this.symbolTable);
	        String llvmType;
	        String targetRegister;
	        if (results[1].equals("field")) {
	            String reg1 = get_register();
	            String reg2 = get_register();
	            if (results[0].equals("int")) {
	                llvmType = "i32*";
	            } else if (results[0].equals("boolean")) {
	                llvmType = "i1*";
	            } else if (results[0].equals("int[]")) {
	                llvmType = "i32**";
	            } else {
	                llvmType = "i8**";
	            }
	            buffer = "\t" + reg1 + " = getelementptr i8, i8* %this, i32 " + get_offset(identifier, results[2], this.vTables) + "\n";
	            buffer += "\t" + reg2 + " = bitcast i8* " + reg1 + " to " + llvmType + "\n";
	            targetRegister = reg2;
	            writeBuffer(buffer);
	        }
	        // Variable or parameter
	        else {
	            if (results[0].equals("int")) {
	                llvmType = "i32*";
	            } else if (results[0].equals("boolean")) {
	                llvmType = "i1*";
	            } else if (results[0].equals("int[]")) {
	                llvmType = "i32**";
	            } else {
	                llvmType = "i8**";
	            }
	            targetRegister = "%" + identifier;
	        }
	        
	        this.returnFieldValue = true;
	        assignStatement.rv().accept(this);
	        String regType = registerTypes.get(this.latestRegister);
	        //lv refType is different from rv, and it isn't primitive, so check
	        //if rv is subtype of lv
	        buffer = "\tstore " + llvmType.substring(0, llvmType.length() - 1) + " " + this.latestRegister + ", " + llvmType + " " + targetRegister + "\n";
	        writeBuffer(buffer);
        }catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void visit(AssignArrayStatement assignArrayStatement) {
    	String lbl1 = get_bound_label();
        String lbl2 = get_bound_label();
        String lbl3 = get_bound_label();
        String tempreg1;
        String tempreg2 = get_register();
        String cmpReg = get_register();
        String tempreg3 = get_register();
        String tempreg4 = get_register();

        String reg1 = assignArrayStatement.lv();
        String results[];
		try {//added
			results = get_identifier_data(reg1, this.symbolTable);
		
	        String buffer;
	        if (results[1].equals("field")) {
	            String fieldReg1 = get_register();
	            String fieldReg2 = get_register();
	            String fieldReg3 = get_register();
	            buffer = "\t" + fieldReg1 + " = getelementptr i8, i8* %this, i32 " + get_offset(reg1, results[2], this.vTables) + "\n";
	            buffer += "\t" + fieldReg2 + " = bitcast i8* " + fieldReg1 + " to i32**\n";
	            buffer += "\t" + fieldReg3 + " = load i32*, i32** " + fieldReg2 + "\n";
	            tempreg1 = fieldReg3;
	            writeBuffer(buffer);
	        }
	        // Parameter or variable
	        else {
	            String varReg = get_register();
	            writeBuffer("\t" + varReg + " = load i32*, i32** %" + reg1 + "\n");
	            tempreg1 = varReg;
	        }
	        
	        assignArrayStatement.index().accept(this);
	        String reg2 = this.latestRegister;
	        writeBuffer("\t" + tempreg2 + " = load i32, i32* " + tempreg1 + "\n");
	
	        writeBuffer("\t" + cmpReg + " = icmp ult i32 " + reg2 + ", " + tempreg2 + "\n");
	        writeBuffer("\tbr i1 " + cmpReg + ", label %" + lbl1 + ", label %" + lbl2 + "\n");
	        writeBuffer("\n" + lbl1 + ":\n");
	
	        assignArrayStatement.rv().accept(this);
	        String reg3 = this.latestRegister;
	        writeBuffer("\t" + tempreg3 + " = add i32 " + reg2 + ", 1\n");
	        writeBuffer("\t" + tempreg4 + " = getelementptr i32, i32* " + tempreg1 + ", i32 " + tempreg3 + "\n");
	        writeBuffer("\tstore i32 " + reg3 + ", i32* " + tempreg4 + "\n");
	
	        writeBuffer("\tbr label %" + lbl3 + "\n");
	
	        writeBuffer("\n" + lbl2 + ":\n");
	        writeBuffer("\tcall void @throw_oob()\n");
	        writeBuffer("\tbr label %" + lbl3 + "\n");
	
	        writeBuffer("\n" + lbl3 + ":\n");
	        
	        this.latestRegister=tempreg4;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void visit(AndExpr e) {
    	String andLbl1 = get_and_label();
        String andLbl2 = get_and_label();
        String andLbl3 = get_and_label();
        String andLbl4 = get_and_label();
        String phiReg = get_register();

        this.returnFieldValue = true;
        e.e1().accept(this);
        String reg1 = this.latestRegister;
        writeBuffer("\tbr label %" + andLbl1 + "\n");
        writeBuffer("\n" + andLbl1 + ":\n");
        writeBuffer("\tbr i1 " + reg1 + ", label %" + andLbl2 + ", label %" + andLbl4 + "\n");

        this.returnFieldValue = true;
        writeBuffer("\n" + andLbl2 + ":\n");
        e.e2().accept(this);
        String reg2 = this.latestRegister;
        writeBuffer("\tbr label %" + andLbl3 + "\n");

        writeBuffer("\n" + andLbl3 + ":\n");
        writeBuffer("\tbr label %" + andLbl4 + "\n");

        writeBuffer("\n" + andLbl4 + ":\n");
        writeBuffer("\t" + phiReg + " = phi i1 [ 0, %" + andLbl1 + " ], [ " + reg2 + ", %" + andLbl3 + " ]\n");
        
        this.latestRegister=phiReg;
    }

    @Override
    public void visit(LtExpr e) {
    	this.returnFieldValue = true;
    	e.e1().accept(this);
        String reg1 = this.latestRegister;
        this.returnFieldValue = true;
        e.e2().accept(this);
        String reg2 = this.latestRegister;
        String resultReg = get_register();
        writeBuffer("\t" + resultReg + " = icmp slt i32 " + reg1 + ", " + reg2 + "\n");
        this.latestRegister=resultReg;
        
    }

    @Override
    public void visit(AddExpr e) {
    	this.returnFieldValue = true;
    	e.e1().accept(this);
        String reg1 = this.latestRegister;
        this.returnFieldValue = true;
        e.e2().accept(this);
        String reg2 = this.latestRegister;
        String resultReg = get_register();
        writeBuffer("\t" + resultReg + " = add i32 " + reg1 + ", " + reg2 + "\n");
        this.latestRegister= resultReg;
    }

    @Override
    public void visit(SubtractExpr e) {
    	this.returnFieldValue = true;
    	e.e1().accept(this);
        String reg1 = this.latestRegister;
        this.returnFieldValue = true;
        e.e2().accept(this);
        String reg2 = this.latestRegister;
        String resultReg = get_register();
        writeBuffer("\t" + resultReg + " = sub i32 " + reg1 + ", " + reg2 + "\n");
        this.latestRegister=resultReg;
    }

    @Override
    public void visit(MultExpr e) {
    	this.returnFieldValue = true;
    	e.e1().accept(this);
        String reg1 = this.latestRegister;
        this.returnFieldValue = true;
        e.e2().accept(this);
        String reg2 = this.latestRegister;
        String resultReg = get_register();
        writeBuffer("\t" + resultReg + " = mul i32 " + reg1 + ", " + reg2 + "\n");
        this.latestRegister=resultReg;
    }

    @Override
    public void visit(ArrayAccessExpr e) {
    	String lbl1 = get_bound_label();
        String lbl2 = get_bound_label();
        String lbl3 = get_bound_label();
        String tempreg1 = get_register();
        String tempreg2 = get_register();
        String cmpReg = get_register();
        String tempreg3 = get_register();
        String tempreg4 = get_register();

        this.returnFieldValue = true;
        e.arrayExpr().accept(this);
        String reg1 = this.latestRegister;
        writeBuffer("\t" + tempreg1 + " = load i32, i32* " + reg1 + "\n");

        this.returnFieldValue = true;
        e.indexExpr().accept(this);
        String reg2 = this.latestRegister;


        writeBuffer("\t" + cmpReg + " = icmp ult i32 " + reg2 + ", " + tempreg1 + "\n");
        writeBuffer("\tbr i1 " + cmpReg + ", label %" + lbl1 + ", label %" + lbl2 + "\n");

        writeBuffer("\n" + lbl1 + ":\n");
        writeBuffer("\t" + tempreg2 + " = add i32 " + reg2 + ", 1\n");
        writeBuffer("\t" + tempreg3 + " = getelementptr i32, i32* " + reg1 + ", i32 " + tempreg2 + "\n");
        writeBuffer("\t" + tempreg4 + " = load i32, i32* " + tempreg3 + "\n");
        writeBuffer("\tbr label %" + lbl3 + "\n");

        writeBuffer("\n" + lbl2 + ":\n");
        writeBuffer("\tcall void (i32) @print_int(i32 " + "12" + ")\n");
        writeBuffer("\tcall void @throw_oob()\n");
        writeBuffer("\tbr label %" + lbl3 + "\n");

        writeBuffer("\n" + lbl3 + ":\n");

        this.latestRegister= tempreg4;
        
    }

    @Override
    public void visit(ArrayLengthExpr e) {
    	 String tempreg = get_register();
         this.returnFieldValue = true;
         e.arrayExpr().accept(this);
         String reg = this.latestRegister;
         writeBuffer("\t" + tempreg + " = load i32, i32* " + reg + "\n");
         this.latestRegister= tempreg;
    }

    @Override
    public void visit(MethodCallExpr e) {
    	// Visit ownerExpression
    	try {
    	e.ownerExpr().accept(this);
        String register = this.latestRegister;
        String registerType = this.registerTypes.get(register);
        // Use current Class type if 'this' is used
        if (register.equals("%this")) {
            registerType = this.currentClass;
        }
        if (registerType == null) {
            String results[] = get_identifier_data(register, this.symbolTable);
            registerType = results[0];
        }
        
        String methName = e.methodId();
        SymbolTable.ClassSymTable classSymTable = this.symbolTable.classes.get(registerType);
        SymbolTable.MethodSymTable methodSymTable = classSymTable.methods.get(methName);
        // If you didn't find method in current class,
        // then this method belongs to a superclass and this class is child class
        if (methodSymTable == null && classSymTable.parentClass != null) {
            // Check if this method is in parent class
            while (classSymTable.parentClass != null) {
                SymbolTable.ClassSymTable parentClass = symbolTable.classes.get(classSymTable.parentClass);
                if (parentClass.methods.containsKey(methName)) {
                    // Return your type and class name
                    classSymTable = parentClass;
                    methodSymTable = classSymTable.methods.get(methName);
                    break;
                }
                classSymTable = parentClass;
            }
        }
        String methodType = methodSymTable.returnType;
        
        // Create an array to hold up the types of parameters
        // If it is already created then it is assumed where are in nested call
        ArrayList<String> backupMethodArgs = null;
        boolean methodArgsTempFlag = false;
        if (this.methodArgs != null) {
            methodArgsTempFlag = true;
            backupMethodArgs = new ArrayList<>(this.methodArgs);
        }
        this.methodArgs = new ArrayList<>();

        int offset = get_method_vtable_position(methName, registerType);
        String reg1 = get_register();
        String reg2 = get_register();
        String reg3 = get_register();
        String reg4 = get_register();
        String reg5 = get_register();
        String reg6 = get_register();
        String buffer = "\t" + reg1 + " = bitcast i8* " + register + " to i8***\n";
        buffer += "\t" + reg2 + " = load i8**, i8*** " + reg1 + "\n";
        buffer += "\t" + reg3 + " = getelementptr i8*, i8** " + reg2 + ", i32 " + offset + "\n";
        buffer += "\t" + reg4 + " = load i8*, i8** " + reg3 + "\n";
        buffer += "\t" + reg5 + " = bitcast i8* " + reg4 + " to ";

        String llvmMethType;
        if (methodType.equals("int")) {
            buffer += "i32 (i8*";
            llvmMethType = "i32";
        } else if (methodType.equals("boolean")) {
            buffer += "i1 (i8*";
            llvmMethType = "i1";
        } else if (methodType.equals("int[]")) {
            buffer += "i32* (i8*";
            llvmMethType = "i32*";
        } else {
            buffer += "i8* (i8*";
            llvmMethType = "i8*";
        }
        for (Map.Entry methodEntryFunctions : methodSymTable.formals.entrySet()) {
            String paramType = methodEntryFunctions.getValue().toString();
            if (paramType.equals("int")) {
                buffer += ", i32";
            } else if (paramType.equals("boolean")) {
                buffer += ", i1";
            } else if (methodType.equals("int[]")) {
                buffer += ", i32*";
            } else {
                buffer += ", i8*";
            }
        }
        buffer += ")*\n";
        writeBuffer(buffer);
        
        for (Expr arg : e.actuals()) {
            this.returnFieldValue = true;
            arg.accept(this);
            String register1 = this.latestRegister;
            this.methodArgs.add(register1);            
        }
        buffer = "\t" + reg6 + " = call " + llvmMethType + " " + reg5 + "(i8* " + register;
        
        // Insert parameters
        for (int i = 0; i < this.methodArgs.size(); i++) {
            String paramType = (new ArrayList<>(methodSymTable.formals.values())).get(i);
            String reg = this.methodArgs.get(i);
            if (paramType.equals("int")) {
                buffer += ", i32 " + reg;
            } else if (paramType.equals("boolean")) {
                buffer += ", i1 " + reg;
            } else if (methodType.equals("int[]")) {
                buffer += ", i32* " + reg;
            } else {
                buffer += ", i8* " + reg;
            }
        }
        buffer += ")\n";
        writeBuffer(buffer);

        // Erase the array
        this.methodArgs = null;
        // Restore previous array if was existed before the methodArgs visit
        if (methodArgsTempFlag) {
            this.methodArgs = new ArrayList<>(backupMethodArgs);
        }
        this.registerTypes.put(reg6, methodType);
        this.latestRegister = reg6;
    	}catch (Exception err) {
			// TODO Auto-generated catch block
			err.printStackTrace();
		}
    }

    @Override
    public void visit(IntegerLiteralExpr e) {
        this.latestRegister=Integer.toString(e.num());
    }

    @Override
    public void visit(TrueExpr e) {
        this.latestRegister="1";
    }

    @Override
    public void visit(FalseExpr e) {
        this.latestRegister="0";
    }

    @Override
    public void visit(IdentifierExpr e) {
    	String results[];
		try {//added
			results = get_identifier_data(e.id(), this.symbolTable);
		
	        String buffer;
	        if (results[1].equals("field")) {
	            String llvmType;
	            String reg1 = get_register();
	            String reg2 = get_register();
	            writeBuffer("\n");
	            buffer = "\t" + reg1 + " = getelementptr i8, i8* %this, i32 " + get_offset(e.id(), results[2], this.vTables) + "\n";
	            if (results[0].equals("int")) {
	                llvmType = "i32*";
	            } else if (results[0].equals("boolean")) {
	                llvmType = "i1*";
	            } else if (results[0].equals("int[]")) {
	                llvmType = "i32**";
	            } else {
	                llvmType = "i8**";
	            }
	            buffer += "\t" + reg2 + " = bitcast i8* " + reg1 + " to " + llvmType + "\n";
	            writeBuffer(buffer);
	            if (this.returnFieldValue) {
	                String tmpReg = get_register();
	                this.returnFieldValue = false;
	                if (results[0].equals("int")) {
	                    writeBuffer("\t" + tmpReg + " = load i32, i32* " + reg2 + "\n");
	                } else if (results[0].equals("boolean")) {
	                    writeBuffer("\t" + tmpReg + " = load i1, i1* " + reg2 + "\n");
	                } else if (results[0].equals("int[]")) {
	                    writeBuffer("\t" + tmpReg + " = load i32*, i32** " + reg2 + "\n");
	                } else {
	                    writeBuffer("\t" + tmpReg + " = load i8*, i8** " + reg2 + "\n");
	                }
	                reg2 = tmpReg;
	            }
	            this.registerTypes.put(reg2, results[0]);
	            this.latestRegister = reg2;
	        }
	        // Parameter or variable
	        else {
	            String reg1 = get_register();
	            buffer = "\n\t" + reg1 + " = load ";
	            if (results[0].equals("int")) {
	                buffer += "i32, i32* ";
	            } else if (results[0].equals("boolean")) {
	                buffer += "i1, i1* ";
	            } else if (results[0].equals("int[]")) {
	                buffer += "i32*, i32** ";
	            } else {
	                buffer += "i8*, i8** ";
	            }
	            buffer += "%" +  e.id() + "\n";
	            writeBuffer(buffer);
	            this.registerTypes.put(reg1, results[0]);
	            this.latestRegister = reg1;
	        }
        } catch (Exception e1) {
			
			e1.printStackTrace();
		}
        
    }

    public void visit(ThisExpr e) {
        this.latestRegister="%this";
    }

    @Override
    public void visit(NewIntArrayExpr e) {
    	String lbl1 = get_arr_alloc_label();
        String lbl2 = get_arr_alloc_label();
        String cmpReg = get_register();
        String reg1 = get_register();
        String reg2 = get_register();
        String reg3 = get_register();
        e.lengthExpr().accept(this);
        String reg = this.latestRegister;
        writeBuffer("\t" + cmpReg + " = icmp slt i32 " + reg + ", 0\n");
        writeBuffer("\tbr i1 " + cmpReg + ", label %" + lbl1 + ", label %" + lbl2 + "\n");

        writeBuffer("\n" + lbl1 + ":\n");
        writeBuffer("\tcall void @throw_oob()\n");
        writeBuffer("\tbr label %" + lbl2 + "\n");

        writeBuffer("\n" + lbl2 + ":\n");
        writeBuffer("\t" + reg1 + " = add i32 " + reg + ", 1\n");
        writeBuffer("\t" + reg2 + " = call i8* @calloc(i32 4, i32 " + reg1 + ")\n");
        writeBuffer("\t" + reg3 + " = bitcast i8* " + reg2 + " to i32*\n");
        writeBuffer("\tstore i32 " + reg + ", i32* " + reg3 + "\n");
        this.registerTypes.put(reg3, "int[]");
        this.latestRegister= reg3;
        
    }

    @Override
    public void visit(NewObjectExpr e) {
    	String className = e.classId();
        // Get class size and the number of methods that are contained
        int classSize = get_class_size(className, this.symbolTable);
        int numberOfMethods;
		try {//added
			numberOfMethods = get_number_of_methods(className,this.symbolTable);
		

	        String buffer;
	        String reg1 = get_register();
	        String reg2 = get_register();
	        String reg3 = get_register();
	        writeBuffer("\n");
	        buffer = "\t" + reg1 + " = call i8* @calloc(i32 1, i32 " + classSize + ")\n";
	        buffer += "\t" + reg2 + " = bitcast i8* " + reg1 + " to i8***\n";
	        buffer += "\t" + reg3 + " = getelementptr [" + numberOfMethods + " x i8*], [" + numberOfMethods + " x i8*]* @." + className + "_vtable, i32 0, i32 0\n";
	        buffer += "\tstore i8** " + reg3 + ", i8*** " + reg2 + "\n";
	        writeBuffer(buffer);
	        this.registerTypes.put(reg1, className);
	        this.latestRegister = reg1;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
    }

    @Override
    public void visit(NotExpr e) {
    	e.e().accept(this);
    	String reg = this.latestRegister;
        String flippedReg = get_register();
        writeBuffer("\t" + flippedReg + " = sub i1 1, " + reg + "\n");
        this.latestRegister=flippedReg;
    }

    @Override
    public void visit(IntAstType t) {
        this.currentVarType="int";
    }

    @Override
    public void visit(BoolAstType t) {
    	this.currentVarType="boolean";
    }

    @Override
    public void visit(IntArrayAstType t) {
    	this.currentVarType="int[]";
    }

    @Override
    public void visit(RefType t) {
        this.currentVarType=t.id();
    }
}



package ast;
import java.io.*;
import java.util.*;

public class SymbolTable {
    File fileptr;
    String fileName;
    public  LinkedHashMap<String,ClassSymTable> classes;
    LinkedHashMap<String,MethodSymTable> methods_with_decls_formals;
    public SymbolTable() {
        classes = new LinkedHashMap<String,ClassSymTable>();
    }

    public SymbolTable(String fileName){
        this.fileName = fileName;
        classes = new LinkedHashMap<String,ClassSymTable>();
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

    public SymbolTable getSymTable() {
    	return this;
    }
    private void make_override(ClassSymTable c_sym,String meth_name,MethodSymTable m_s){
    	boolean flag = false;
         while (c_sym.parentClass != null) {
             SymbolTable.ClassSymTable parent_Class = classes.get(c_sym.parentClass); //c_sym.classes.get(c_sym.parentClass);
             if (parent_Class.mainClass) {
                 break;
             }
             if(parent_Class.methods.containsKey(meth_name) && parent_Class.methods.get(meth_name).returnType.equals(m_s.returnType)) {
            	 m_s.override = true;
            	 flag = true;
             }
             c_sym = parent_Class;
         }
         if(!flag) {
        	 m_s.override=false;
         }
    }
    // for specific class add methods and fields for it.
    public void AddMethods_plus_field(String ClassName,String ParentClass,boolean IsMain,LinkedHashMap<String,MethodSymTable> allmethods,LinkedHashMap<String,String> fields) {
		methods_with_decls_formals = new LinkedHashMap<String,MethodSymTable>(allmethods);
		ClassSymTable c_Sym = new ClassSymTable();
		c_Sym.className = ClassName;
		c_Sym.parentClass = ParentClass;
		c_Sym.fields = fields;
		if(IsMain)
			c_Sym.mainClass = true;
		else
			c_Sym.mainClass = false;
		c_Sym.setMethods(methods_with_decls_formals);
		classes.put(ClassName,c_Sym);
		LinkedHashMap<String,MethodSymTable> meths = classes.get(ClassName).methods;
		for(var meth:meths.entrySet()){
				meths.get(meth.getKey()).parent = c_Sym;
		}
		for(var meth : c_Sym.methods.entrySet()) {
			make_override(c_Sym,meth.getKey(),meth.getValue());
		}
    }
    // This method checks the types of the fields, methods and variables
    // that stored in the symbol table after the first visit
    public void type_check_symbol_table() {
        for (Map.Entry entry : classes.entrySet()) {
            Object key = entry.getKey();
            ClassSymTable classSym = classes.get(key);
            // Check the fields
            for (Map.Entry classEntryFields : classSym.fields.entrySet()) {
                String fieldType = classEntryFields.getValue().toString();
                if (!classes.containsKey(fieldType) && !fieldType.equals("int") && !fieldType.equals("boolean") && !fieldType.equals("int[]")) {
                    writeBuffer("ERROR");//writeBuffer("ERROR74");
                    System.exit(-1);
                }
            }
            // Check the methods
            for (Map.Entry classEntryFunctions : classSym.methods.entrySet()) {
                Object keyMethod = classEntryFunctions.getKey();
                MethodSymTable methSym = classSym.methods.get(keyMethod);
                String methodType = methSym.returnType;
                // Ignore main's type
                if (!methSym.methodName.equals("main")) {
                    if (!classes.containsKey(methodType) && !methodType.equals("int") && !methodType.equals("boolean") && !methodType.equals("int[]")) {
                        writeBuffer("ERROR");//writeBuffer("ERROR74");
                        System.exit(-1);
                    }
                    // Check parameters inside methods
                    for (Map.Entry methodEntryFunctions : methSym.formals.entrySet()) {
                        String paramType = methodEntryFunctions.getValue().toString();
                        if (!classes.containsKey(paramType) && !paramType.equals("int") && !paramType.equals("boolean") && !paramType.equals("int[]")) {
                            writeBuffer("ERROR");//writeBuffer("ERROR74");
                            System.exit(-1);
                        }
                    }
                }
                for (Map.Entry methodEntryFunctions : methSym.locals.entrySet()) {
                    String varType = methodEntryFunctions.getValue().toString();
                    if (!classes.containsKey(varType) && !varType.equals("int") && !varType.equals("boolean") && !varType.equals("int[]")) {
                        writeBuffer("ERROR");//writeBuffer("ERROR74");
                        System.exit(-1);
                    }
                }
            }
        }
    }

    // Stam function just printing hhh :))
    public void print_symbol_table() {
        for (Map.Entry entry : classes.entrySet()) {
            Object key = entry.getKey();
            System.out.println("CLASS: " + key);
            ClassSymTable classSym = classes.get(key);
            if(classSym.mainClass)
                continue;
            if (classSym.parentClass != null) {
                System.out.println("Extends class '" + classSym.parentClass + "'");
            }
            System.out.println("\nFIELDS:");
            for (Map.Entry classEntryFields : classSym.fields.entrySet()) {
                System.out.println("   " + classEntryFields.getValue() + " " + classEntryFields.getKey());
            }
            System.out.println("\nFUNCTIONS:");
            for (Map.Entry classEntryFunctions : classSym.methods.entrySet()) {
                Object keyMethod = classEntryFunctions.getKey();
                MethodSymTable methSym = classSym.methods.get(keyMethod);
                System.out.print("is_override ? : " + methSym.override);
                System.out.print("    " + methSym.returnType + " " + methSym.methodName + "(");

                boolean flag = false;
                for (Map.Entry methodEntryFunctions : methSym.formals.entrySet()) {
                    if (flag) {
                        System.out.print(", ");
                    }
                    flag = true;
                    System.out.print(methodEntryFunctions.getValue() + " " + methodEntryFunctions.getKey());
                }
                System.out.print(")\n");
                System.out.println("    VAR DECLS:");
                for (Map.Entry methodEntryFunctions : methSym.locals.entrySet()) {
                    System.out.println("        " + methodEntryFunctions.getValue() + " " + methodEntryFunctions.getKey());
                }
                System.out.println();
            }
            System.out.println("----------END----------");

        }
	//System.out.println(classes.get("Tree").methods.get("Delete").parent.className);
    }
    public static class ClassSymTable {
        public String className;
        public String parentClass;
        public Boolean mainClass;
        public LinkedHashMap<String, String> fields;
        public LinkedHashMap<String, MethodSymTable> methods;

        ClassSymTable() {
            className = null;
            parentClass = null;
            fields = new LinkedHashMap<>();
            methods = new LinkedHashMap<>();
        }
	void setMethods(LinkedHashMap<String, MethodSymTable> methods){
		this.methods = new LinkedHashMap<String,SymbolTable.MethodSymTable>(methods);
	}
    }
    public static class MethodSymTable {
        public String methodName;
        public String returnType;
        public Boolean override;
        public ClassSymTable parent;
        public LinkedHashMap<String, String> formals;
        public LinkedHashMap<String, String> locals;
        MethodSymTable() {
            methodName = null;
            returnType = null;
            parent = null;
            formals = new LinkedHashMap<>();
            locals = new LinkedHashMap<>();
        }
	void SetFormals(LinkedHashMap<String, String> formals){
		this.formals = formals;
	}
	void SetLocals(LinkedHashMap<String, String> locals){
		this.locals = locals;
	}
        
    }
}

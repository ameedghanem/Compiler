package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class VTables {

    public LinkedHashMap<String, ClassVTable> classesTables;

    public VTables() {
        classesTables = new LinkedHashMap<>();
    }

    public static class ClassVTable {
        boolean isMainClass;
        public LinkedHashMap<String, Integer> fieldsTable;
        public LinkedHashMap<String, Integer> methodsTable;

        ClassVTable() {
            fieldsTable = new LinkedHashMap<>();
            methodsTable = new LinkedHashMap<>();
        }
    }

    public VTables create_v_tables(SymbolTable symbolTable) {
        VTables vTables = new VTables();
        int fieldOffset, methodOffset;
        String mainClassName = null;
        
        LinkedHashMap<String, ArrayList<Integer>> offsetTable = new LinkedHashMap<>();
        for (Map.Entry entry : symbolTable.classes.entrySet()) {
            Object key = entry.getKey();
            SymbolTable.ClassSymTable classSym = symbolTable.classes.get(key);
            vTables.classesTables.put(classSym.className, new VTables.ClassVTable());
            VTables.ClassVTable classVTable = vTables.classesTables.get(classSym.className);
           	
            if (classSym.mainClass) {
                classVTable.isMainClass = true;
                mainClassName = classSym.className;
                continue;
            } else {
                classVTable.isMainClass = false;
            }
            if (classSym.parentClass != null && !classSym.parentClass.equals(mainClassName) && offsetTable.get(classSym.parentClass) != null) { // what about the 2nd condition ? o.O
                ArrayList<Integer> curOffset = offsetTable.get(classSym.parentClass);
                fieldOffset = curOffset.get(0);
                methodOffset = curOffset.get(1);
            } else {
                fieldOffset = 0;
                methodOffset = 0;
            }
            for (Map.Entry classEntryFields : classSym.fields.entrySet()) {
                String type = classEntryFields.getValue().toString();
                String var = classEntryFields.getKey().toString();
                if (type.equals("int")) {
                    classVTable.fieldsTable.put(var, fieldOffset);
                    fieldOffset += 4;
                } else if (type.equals("boolean")) {
                    classVTable.fieldsTable.put(var, fieldOffset);
                    fieldOffset += 1;
                } else {
                    classVTable.fieldsTable.put(var, fieldOffset);
                    fieldOffset += 8;
                }
            }
            for (Map.Entry classEntryFunctions : classSym.methods.entrySet()) {
                Object keyMethod = classEntryFunctions.getKey();
                SymbolTable.MethodSymTable methSym = classSym.methods.get(keyMethod);

                // my code 
                // here i add the function signature to the vtable
                /*ArrayList<String> lst = new ArrayList();
                lst.add(methSym.returnType);
                for (Map.Entry methodEntryFunctions : methSym.parameters.entrySet()) {
                    lst.add(methodEntryFunctions.getValue());
                }
                classVTable.signatureTable.put(methSym.methodName, lst);*/  // there is no need for it.

                // end of my code

                
                if (methSym.override) {
                    classVTable.methodsTable.put(methSym.methodName, methodOffset);
                }else{
                	classVTable.methodsTable.put(methSym.methodName, methodOffset);
                	methodOffset += 8;
                }
                

            }
            // Store offsets
            ArrayList<Integer> offsets = new ArrayList<>();
            offsets.add(fieldOffset);
            offsets.add(methodOffset);
            offsetTable.put(classSym.className, offsets);
        }
        return vTables;
    }


    
}

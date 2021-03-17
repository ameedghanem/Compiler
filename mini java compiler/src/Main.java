import ast.*;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            var inputMethod = args[0];
            var action = args[1];
            var filename = args[args.length - 2];
            var outfilename = args[args.length - 1];

            Program prog;
            prog = null;
            if (inputMethod.equals("parse")) {
                try {
                    FileReader fileReader = new FileReader(new File(filename));
                    Lexer l = new Lexer(fileReader);
                    Parser p = new Parser(l);
                    prog = (Program) p.parse().value;
                    
                } catch (Exception e) {
                    System.out.println("General error: " + e);
                    e.printStackTrace();
                }
            } else if (inputMethod.equals("unmarshal")) {
                AstXMLSerializer xmlSerializer = new AstXMLSerializer();
                prog = xmlSerializer.deserialize(new File(filename));
            } else {
                throw new UnsupportedOperationException("unknown input method " + inputMethod);
            }

            var outFile = new PrintWriter(outfilename);
            try {

                if (action.equals("marshal")) {
                    AstXMLSerializer xmlSerializer = new AstXMLSerializer();
                    xmlSerializer.serialize(prog, outfilename);
                } else if (action.equals("print")) {
                    AstPrintVisitor astPrinter = new AstPrintVisitor();
                    astPrinter.visit(prog);
                    outFile.write(astPrinter.getString());

                } else if (action.equals("semantic")) {


                    //throw new UnsupportedOperationException("TODO - Ex. 3");
                    SymbolTable symbolTable = new SymbolTable(outfilename);
                    TypeCheckVisitor type_check = new TypeCheckVisitor(outfilename,symbolTable);
                    type_check.visit(prog);
                    //System.out.println("40");
                    symbolTable.type_check_symbol_table();
                    //symbolTable.print_symbol_table();
                    SemanticVisitor semantic = new SemanticVisitor(outfilename,symbolTable);
                    semantic.visit(prog);
                    //System.out.println("45");

                    try {
            
                        File fileptr = new File(outfilename);
                        if (!fileptr.exists()) {
                            fileptr.createNewFile();
                        }
                        
                        else {
                            PrintWriter writer = new PrintWriter(fileptr);
                            writer.println("OK");
                            writer.close();
                        }
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }


                } else if (action.equals("compile")) {// Ex.  2
                    VisitorMakeSymbolTable symbolTable = new VisitorMakeSymbolTable();
                    symbolTable.visit(prog);
                    VTables vTables = new VTables();
                	vTables = vTables.create_v_tables(symbolTable.getSymTable());
                    CodeGenVisitor llvmvisitor = new CodeGenVisitor(outfilename, symbolTable.getSymTable(), vTables);
                    llvmvisitor.visit(prog);

                    

                	
                	
                	
                } else if (action.equals("rename")) {
                    var type = args[2];
                    var originalName = args[3];
                    var originalLine = args[4];
                    var newName = args[5];

                    boolean isMethod;
                    if (type.equals("var")) {
                        isMethod = false;
                    } else if (type.equals("method")) {
                        isMethod = true;
                    } else {
                        throw new IllegalArgumentException("unknown rename type " + type);
                    }
                    AstXMLSerializer xmlSerializer = new AstXMLSerializer();                    
                    if (!isMethod) {
            
                        VarKindVisitor visitorKind = new VarKindVisitor(originalLine);
                        visitorKind.visit(prog);
                        String kind=visitorKind.getType(); 
                        String methodName=visitorKind.methName();
                        String className=visitorKind.clsName();
                        
                        if (kind=="field") {     
                            AstFieldVisitor astFielder = new AstFieldVisitor(className,originalName ,newName);
                            astFielder.visit(prog);
                        }else if (kind=="formal") {
                            AstFormalVisitor astFormalizer = new AstFormalVisitor(className, originalName ,newName, methodName, Integer.parseInt(originalLine));
                            astFormalizer.visit(prog);
                        }else { // if kind equals "vardecl"
                            AstVarDeclVisitor astVarVis = new AstVarDeclVisitor(className, originalName ,newName, methodName, Integer.parseInt(originalLine));
                            astVarVis.visit(prog);   
                        }
                                
                    }else { //Method_Omar 
                        MethodKindVisitor visitorkind = new MethodKindVisitor(originalLine);
                        visitorkind.visit(prog);
                        String className = visitorkind.clsName();
                        AstMethodVisitor astMethod = new AstMethodVisitor(className,originalName,newName,Integer.parseInt(originalLine));
                        astMethod.visit(prog);
                    }

                    xmlSerializer.serialize(prog, outfilename);

                } else {
                    throw new IllegalArgumentException("unknown command line action " + action);
                }
            } finally {
                outFile.flush();
                outFile.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General error: " + e);
            e.printStackTrace();
        }
    }
}

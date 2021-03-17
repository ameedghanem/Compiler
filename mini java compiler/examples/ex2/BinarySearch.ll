@.BS_vtable = global [6 x i8*] [i8* bitcast (i32 (i8*, i32)* @BS.Start to i8*), i8* bitcast (i1 (i8*, i32)* @BS.Search to i8*), i8* bitcast (i32 (i8*, i32)* @BS.Div to i8*), i8* bitcast (i1 (i8*, i32, i32)* @BS.Compare to i8*), i8* bitcast (i32 (i8*)* @BS.Print to i8*), i8* bitcast (i32 (i8*, i32)* @BS.Init to i8*)]

declare i8* @calloc(i32, i32)
declare i32 @printf(i8*, ...)
declare void @exit(i32)

@_cint = constant [4 x i8] c"%d\0a\00"
@_cOOB = constant [15 x i8] c"Out of bounds\0a\00"
define void @print_int(i32 %i) {
	%_str = bitcast [4 x i8]* @_cint to i8*
	call i32 (i8*, ...) @printf(i8* %_str, i32 %i)
	ret void
}

define void @throw_oob() {
	%_str = bitcast [15 x i8]* @_cOOB to i8*
	call i32 (i8*, ...) @printf(i8* %_str)
	call void @exit(i32 1)
	ret void
}


define i32 @main() {
	%_0 = call i8* @calloc(i32 1, i32 20)
	%_1 = bitcast i8* %_0 to i8***
	%_2 = getelementptr [6 x i8*], [6 x i8*]* @.BS_vtable, i32 0, i32 0
	store i8** %_2, i8*** %_1
	%_3 = bitcast i8* %_0 to i8***
	%_4 = load i8**, i8*** %_3
	%_5 = getelementptr i8*, i8** %_4, i32 0
	%_6 = load i8*, i8** %_5
	%_7 = bitcast i8* %_6 to i32 (i8*, i32)*
	%_8 = call i32 %_7(i8* %_0, i32 20)
	call void (i32) @print_int(i32 %_8)
	ret i32 0
}

define i32 @BS.Start(i8* %this, i32 %.sz) {
	%sz = alloca i32	
	store i32 %.sz, i32* %sz	
	%aux01 = alloca i32	
	%aux02 = alloca i32	
	%_0 = bitcast i8* %this to i8***
	%_1 = load i8**, i8*** %_0
	%_2 = getelementptr i8*, i8** %_1, i32 5
	%_3 = load i8*, i8** %_2
	%_4 = bitcast i8* %_3 to i32 (i8*, i32)*
	%_5 = load i32, i32* %sz
	%_6 = call i32 %_4(i8* %this, i32 %_5)
	store i32 %_6, i32* %aux01	
	%_7 = bitcast i8* %this to i8***
	%_8 = load i8**, i8*** %_7
	%_9 = getelementptr i8*, i8** %_8, i32 4
	%_10 = load i8*, i8** %_9
	%_11 = bitcast i8* %_10 to i32 (i8*)*
	%_12 = call i32 %_11(i8* %this)
	store i32 %_12, i32* %aux02	
	%_13 = bitcast i8* %this to i8***
	%_14 = load i8**, i8*** %_13
	%_15 = getelementptr i8*, i8** %_14, i32 1
	%_16 = load i8*, i8** %_15
	%_17 = bitcast i8* %_16 to i1 (i8*, i32)*
	%_18 = call i1 %_17(i8* %this, i32 8)
	br i1 %_18, label %if0, label %if1
if0:
	call void (i32) @print_int(i32 1)
	br label %if2
if1:
	call void (i32) @print_int(i32 0)
	br label %if2
if2:
	%_19 = bitcast i8* %this to i8***
	%_20 = load i8**, i8*** %_19
	%_21 = getelementptr i8*, i8** %_20, i32 1
	%_22 = load i8*, i8** %_21
	%_23 = bitcast i8* %_22 to i1 (i8*, i32)*
	%_24 = call i1 %_23(i8* %this, i32 19)
	br i1 %_24, label %if3, label %if4
if3:
	call void (i32) @print_int(i32 1)
	br label %if5
if4:
	call void (i32) @print_int(i32 0)
	br label %if5
if5:
	%_25 = bitcast i8* %this to i8***
	%_26 = load i8**, i8*** %_25
	%_27 = getelementptr i8*, i8** %_26, i32 1
	%_28 = load i8*, i8** %_27
	%_29 = bitcast i8* %_28 to i1 (i8*, i32)*
	%_30 = call i1 %_29(i8* %this, i32 20)
	br i1 %_30, label %if6, label %if7
if6:
	call void (i32) @print_int(i32 1)
	br label %if8
if7:
	call void (i32) @print_int(i32 0)
	br label %if8
if8:
	%_31 = bitcast i8* %this to i8***
	%_32 = load i8**, i8*** %_31
	%_33 = getelementptr i8*, i8** %_32, i32 1
	%_34 = load i8*, i8** %_33
	%_35 = bitcast i8* %_34 to i1 (i8*, i32)*
	%_36 = call i1 %_35(i8* %this, i32 21)
	br i1 %_36, label %if9, label %if10
if9:
	call void (i32) @print_int(i32 1)
	br label %if11
if10:
	call void (i32) @print_int(i32 0)
	br label %if11
if11:
	%_37 = bitcast i8* %this to i8***
	%_38 = load i8**, i8*** %_37
	%_39 = getelementptr i8*, i8** %_38, i32 1
	%_40 = load i8*, i8** %_39
	%_41 = bitcast i8* %_40 to i1 (i8*, i32)*
	%_42 = call i1 %_41(i8* %this, i32 37)
	br i1 %_42, label %if12, label %if13
if12:
	call void (i32) @print_int(i32 1)
	br label %if14
if13:
	call void (i32) @print_int(i32 0)
	br label %if14
if14:
	%_43 = bitcast i8* %this to i8***
	%_44 = load i8**, i8*** %_43
	%_45 = getelementptr i8*, i8** %_44, i32 1
	%_46 = load i8*, i8** %_45
	%_47 = bitcast i8* %_46 to i1 (i8*, i32)*
	%_48 = call i1 %_47(i8* %this, i32 38)
	br i1 %_48, label %if15, label %if16
if15:
	call void (i32) @print_int(i32 1)
	br label %if17
if16:
	call void (i32) @print_int(i32 0)
	br label %if17
if17:
	%_49 = bitcast i8* %this to i8***
	%_50 = load i8**, i8*** %_49
	%_51 = getelementptr i8*, i8** %_50, i32 1
	%_52 = load i8*, i8** %_51
	%_53 = bitcast i8* %_52 to i1 (i8*, i32)*
	%_54 = call i1 %_53(i8* %this, i32 39)
	br i1 %_54, label %if18, label %if19
if18:
	call void (i32) @print_int(i32 1)
	br label %if20
if19:
	call void (i32) @print_int(i32 0)
	br label %if20
if20:
	%_55 = bitcast i8* %this to i8***
	%_56 = load i8**, i8*** %_55
	%_57 = getelementptr i8*, i8** %_56, i32 1
	%_58 = load i8*, i8** %_57
	%_59 = bitcast i8* %_58 to i1 (i8*, i32)*
	%_60 = call i1 %_59(i8* %this, i32 50)
	br i1 %_60, label %if21, label %if22
if21:
	call void (i32) @print_int(i32 1)
	br label %if23
if22:
	call void (i32) @print_int(i32 0)
	br label %if23
if23:
	ret i32 999	
}

define i1 @BS.Search(i8* %this, i32 %.num) {
	%num = alloca i32	
	store i32 %.num, i32* %num	
	%bs01 = alloca i1	
	%right = alloca i32	
	%left = alloca i32	
	%var_cont = alloca i1	
	%medium = alloca i32	
	%aux01 = alloca i32	
	%nt = alloca i32	
	store i32 0, i32* %aux01	
	store i1 0, i1* %bs01	
	%_0 = getelementptr i8, i8* %this, i32 8
	%_1 = bitcast i8* %_0 to i32**
	%_2 = load i32*, i32** %_1
	%_3 = load i32, i32* %_2

	store i32 %_3, i32* %right	
	%_4 = load i32, i32* %right
	%_5 = sub i32 %_4, 1
	store i32 %_5, i32* %right	
	store i32 0, i32* %left	
	store i1 1, i1* %var_cont	
	br label %loop0
	loop0:
	%_6 = load i1, i1* %var_cont
	br i1 %_6, label %loop1, label %loop2
	loop1:
	%_7 = load i32, i32* %left
	%_8 = load i32, i32* %right
	%_9 = add i32 %_7, %_8
	store i32 %_9, i32* %medium	
	
	%_10 = bitcast i8* %this to i8***
	%_11 = load i8**, i8*** %_10
	%_12 = getelementptr i8*, i8** %_11, i32 2
	%_13 = load i8*, i8** %_12
	%_14 = bitcast i8* %_13 to i32 (i8*, i32)*
	%_15 = load i32, i32* %medium
	%_16 = call i32 %_14(i8* %this, i32 %_15)
	store i32 %_16, i32* %medium	
	
	%_17 = getelementptr i8, i8* %this, i32 8
	%_18 = bitcast i8* %_17 to i32**
	%_19 = load i32*, i32** %_18
	%_20 = load i32, i32* %medium
	%_21 = icmp slt i32 %_20, 0
	br i1 %_21, label %arr_alloc3, label %arr_alloc4
arr_alloc3:
	call void @throw_oob()
	br label %arr_alloc4
arr_alloc4:
	%_22 = getelementptr i32, i32* %_19, i32 0
	%_23 = load i32, i32* %_22
	%_24 = icmp sle i32 %_23, %_20
	br i1 %_24, label %arr_alloc5, label %arr_alloc6
arr_alloc5:
	call void @throw_oob()
	br label %arr_alloc6
arr_alloc6:
	%_25 = add i32 %_20, 1
	%_26 = getelementptr i32, i32* %_19, i32 %_25
	%_27 = load i32, i32* %_26

	store i32 %_27, i32* %aux01	
	
	%_28 = load i32, i32* %num
	%_29 = load i32, i32* %aux01
	%_30 = icmp slt i32 %_28, %_29
	br i1 %_30, label %if7, label %if8
if7:
	%_31 = load i32, i32* %medium
	%_32 = sub i32 %_31, 1
	store i32 %_32, i32* %right	
	br label %if9
if8:
	%_33 = load i32, i32* %medium
	%_34 = add i32 %_33, 1
	store i32 %_34, i32* %left	
	br label %if9
if9:
	
	%_35 = bitcast i8* %this to i8***
	%_36 = load i8**, i8*** %_35
	%_37 = getelementptr i8*, i8** %_36, i32 3
	%_38 = load i8*, i8** %_37
	%_39 = bitcast i8* %_38 to i1 (i8*, i32, i32)*
	%_40 = load i32, i32* %aux01
	%_41 = load i32, i32* %num
	%_42 = call i1 %_39(i8* %this, i32 %_40, i32 %_41)
	br i1 %_42, label %if10, label %if11
if10:
	store i1 0, i1* %var_cont	
	br label %if12
if11:
	store i1 1, i1* %var_cont	
	br label %if12
if12:
	
	%_43 = load i32, i32* %right
	%_44 = load i32, i32* %left
	%_45 = icmp slt i32 %_43, %_44
	br i1 %_45, label %if13, label %if14
if13:
	store i1 0, i1* %var_cont	
	br label %if15
if14:
	store i32 0, i32* %nt	
	br label %if15
if15:
	
	br label %loop0
	loop2:
	%_46 = bitcast i8* %this to i8***
	%_47 = load i8**, i8*** %_46
	%_48 = getelementptr i8*, i8** %_47, i32 3
	%_49 = load i8*, i8** %_48
	%_50 = bitcast i8* %_49 to i1 (i8*, i32, i32)*
	%_51 = load i32, i32* %aux01
	%_52 = load i32, i32* %num
	%_53 = call i1 %_50(i8* %this, i32 %_51, i32 %_52)
	br i1 %_53, label %if16, label %if17
if16:
	store i1 1, i1* %bs01	
	br label %if18
if17:
	store i1 0, i1* %bs01	
	br label %if18
if18:
	%_54 = load i1, i1* %bs01
	ret i1 %_54	
}

define i32 @BS.Div(i8* %this, i32 %.num) {
	%num = alloca i32	
	store i32 %.num, i32* %num	
	%count01 = alloca i32	
	%count02 = alloca i32	
	%aux03 = alloca i32	
	store i32 0, i32* %count01	
	store i32 0, i32* %count02	
	%_0 = load i32, i32* %num
	%_1 = sub i32 %_0, 1
	store i32 %_1, i32* %aux03	
	br label %loop0
	loop0:
	%_2 = load i32, i32* %count02
	%_3 = load i32, i32* %aux03
	%_4 = icmp slt i32 %_2, %_3
	br i1 %_4, label %loop1, label %loop2
	loop1:
	%_5 = load i32, i32* %count01
	%_6 = add i32 %_5, 1
	store i32 %_6, i32* %count01	
	
	%_7 = load i32, i32* %count02
	%_8 = add i32 %_7, 2
	store i32 %_8, i32* %count02	
	
	br label %loop0
	loop2:
	%_9 = load i32, i32* %count01
	ret i32 %_9	
}

define i1 @BS.Compare(i8* %this, i32 %.num1, i32 %.num2) {
	%num1 = alloca i32	
	store i32 %.num1, i32* %num1	
	%num2 = alloca i32	
	store i32 %.num2, i32* %num2	
	%retval = alloca i1	
	%aux02 = alloca i32	
	store i1 0, i1* %retval	
	%_0 = load i32, i32* %num2
	%_1 = add i32 %_0, 1
	store i32 %_1, i32* %aux02	
	%_2 = load i32, i32* %num1
	%_3 = load i32, i32* %num2
	%_4 = icmp slt i32 %_2, %_3
	br i1 %_4, label %if0, label %if1
if0:
	store i1 0, i1* %retval	
	br label %if2
if1:
	%_5 = load i32, i32* %num1
	%_6 = load i32, i32* %aux02
	%_7 = icmp slt i32 %_5, %_6
	%_8 = sub i1 1, %_7
	br i1 %_8, label %if3, label %if4
if3:
	store i1 0, i1* %retval	
	br label %if5
if4:
	store i1 1, i1* %retval	
	br label %if5
if5:
	br label %if2
if2:
	%_9 = load i1, i1* %retval
	ret i1 %_9	
}

define i32 @BS.Print(i8* %this) {
	%j = alloca i32	
	store i32 1, i32* %j	
	br label %loop0
	loop0:
	%_0 = load i32, i32* %j
	%_1 = getelementptr i8, i8* %this, i32 16
	%_2 = bitcast i8* %_1 to i32*
	%_3 = load i32, i32* %_2
	%_4 = icmp slt i32 %_0, %_3
	br i1 %_4, label %loop1, label %loop2
	loop1:
	%_5 = getelementptr i8, i8* %this, i32 8
	%_6 = bitcast i8* %_5 to i32**
	%_7 = load i32*, i32** %_6
	%_8 = load i32, i32* %j
	%_9 = icmp slt i32 %_8, 0
	br i1 %_9, label %arr_alloc3, label %arr_alloc4
arr_alloc3:
	call void @throw_oob()
	br label %arr_alloc4
arr_alloc4:
	%_10 = getelementptr i32, i32* %_7, i32 0
	%_11 = load i32, i32* %_10
	%_12 = icmp sle i32 %_11, %_8
	br i1 %_12, label %arr_alloc5, label %arr_alloc6
arr_alloc5:
	call void @throw_oob()
	br label %arr_alloc6
arr_alloc6:
	%_13 = add i32 %_8, 1
	%_14 = getelementptr i32, i32* %_7, i32 %_13
	%_15 = load i32, i32* %_14

	call void (i32) @print_int(i32 %_15)
	
	%_16 = load i32, i32* %j
	%_17 = add i32 %_16, 1
	store i32 %_17, i32* %j	
	
	br label %loop0
	loop2:
	call void (i32) @print_int(i32 99999)
	ret i32 0	
}

define i32 @BS.Init(i8* %this, i32 %.sz) {
	%sz = alloca i32	
	store i32 %.sz, i32* %sz	
	%j = alloca i32	
	%k = alloca i32	
	%aux02 = alloca i32	
	%aux01 = alloca i32	
	%_0 = load i32, i32* %sz
	%_1 = getelementptr i8, i8* %this, i32 16
	%_2 = bitcast i8* %_1 to i32*
	store i32 %_0, i32* %_2	
	%_3 = load i32, i32* %sz
	%_4 = icmp slt i32 %_3, 0
	br i1 %_4, label %arr_alloc0, label %arr_alloc1
arr_alloc0:
	call void @throw_oob()
	br label %arr_alloc1
arr_alloc1:
	%_5 = add i32 %_3, 1
	%_6 = call i8* @calloc(i32 4, i32 %_5)
	%_7 = bitcast i8* %_6 to i32*
	store i32 %_3, i32* %_7
	%_8 = getelementptr i8, i8* %this, i32 8
	%_9 = bitcast i8* %_8 to i32**
	store i32* %_7, i32** %_9	
	store i32 1, i32* %j	
	%_10 = getelementptr i8, i8* %this, i32 16
	%_11 = bitcast i8* %_10 to i32*
	%_12 = load i32, i32* %_11
	%_13 = add i32 %_12, 1
	store i32 %_13, i32* %k	
	br label %loop2
	loop2:
	%_14 = load i32, i32* %j
	%_15 = getelementptr i8, i8* %this, i32 16
	%_16 = bitcast i8* %_15 to i32*
	%_17 = load i32, i32* %_16
	%_18 = icmp slt i32 %_14, %_17
	br i1 %_18, label %loop3, label %loop4
	loop3:
	%_19 = load i32, i32* %j
	%_20 = mul i32 2, %_19
	store i32 %_20, i32* %aux01	
	
	%_21 = load i32, i32* %k
	%_22 = sub i32 %_21, 3
	store i32 %_22, i32* %aux02	
	
	%_23 = getelementptr i8, i8* %this, i32 8
	%_24 = bitcast i8* %_23 to i32**
	%_25 = load i32*, i32** %_24
	%_26 = load i32, i32* %j
	%_27 = load i32, i32* %aux01
	%_28 = load i32, i32* %aux02
	%_29 = add i32 %_27, %_28
	%_30 = icmp slt i32 %_26, 0
	br i1 %_30, label %arr_alloc5, label %arr_alloc6
arr_alloc5:
	call void @throw_oob()
	br label %arr_alloc6
arr_alloc6:
	%_31 = getelementptr i32, i32* %_25, i32 0
	%_32 = load i32, i32* %_31
	%_33 = icmp sle i32 %_32, %_26
	br i1 %_33, label %arr_alloc7, label %arr_alloc8
arr_alloc7:
	call void @throw_oob()
	br label %arr_alloc8
arr_alloc8:
	%_34 = add i32 %_26, 1
	%_35 = getelementptr i32, i32* %_25, i32 %_34
	store i32 %_29, i32* %_35	
	
	%_36 = load i32, i32* %j
	%_37 = add i32 %_36, 1
	store i32 %_37, i32* %j	
	
	%_38 = load i32, i32* %k
	%_39 = sub i32 %_38, 1
	store i32 %_39, i32* %k	
	
	br label %loop2
	loop4:
	ret i32 0	
}


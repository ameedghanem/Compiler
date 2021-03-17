@.BBS_vtable = global [4 x i8*] [i8* bitcast (i32 (i8*, i32)* @BBS.Start to i8*), i8* bitcast (i32 (i8*)* @BBS.Sort to i8*), i8* bitcast (i32 (i8*)* @BBS.Print to i8*), i8* bitcast (i32 (i8*, i32)* @BBS.Init to i8*)]

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
	%_2 = getelementptr [4 x i8*], [4 x i8*]* @.BBS_vtable, i32 0, i32 0
	store i8** %_2, i8*** %_1
	%_3 = bitcast i8* %_0 to i8***
	%_4 = load i8**, i8*** %_3
	%_5 = getelementptr i8*, i8** %_4, i32 0
	%_6 = load i8*, i8** %_5
	%_7 = bitcast i8* %_6 to i32 (i8*, i32)*
	%_8 = call i32 %_7(i8* %_0, i32 10)
	call void (i32) @print_int(i32 %_8)
	ret i32 0
}

define i32 @BBS.Start(i8* %this, i32 %.sz) {
	%sz = alloca i32	
	store i32 %.sz, i32* %sz	
	%aux01 = alloca i32	
	%_0 = bitcast i8* %this to i8***
	%_1 = load i8**, i8*** %_0
	%_2 = getelementptr i8*, i8** %_1, i32 3
	%_3 = load i8*, i8** %_2
	%_4 = bitcast i8* %_3 to i32 (i8*, i32)*
	%_5 = load i32, i32* %sz
	%_6 = call i32 %_4(i8* %this, i32 %_5)
	store i32 %_6, i32* %aux01	
	%_7 = bitcast i8* %this to i8***
	%_8 = load i8**, i8*** %_7
	%_9 = getelementptr i8*, i8** %_8, i32 2
	%_10 = load i8*, i8** %_9
	%_11 = bitcast i8* %_10 to i32 (i8*)*
	%_12 = call i32 %_11(i8* %this)
	store i32 %_12, i32* %aux01	
	call void (i32) @print_int(i32 99999)
	%_13 = bitcast i8* %this to i8***
	%_14 = load i8**, i8*** %_13
	%_15 = getelementptr i8*, i8** %_14, i32 1
	%_16 = load i8*, i8** %_15
	%_17 = bitcast i8* %_16 to i32 (i8*)*
	%_18 = call i32 %_17(i8* %this)
	store i32 %_18, i32* %aux01	
	%_19 = bitcast i8* %this to i8***
	%_20 = load i8**, i8*** %_19
	%_21 = getelementptr i8*, i8** %_20, i32 2
	%_22 = load i8*, i8** %_21
	%_23 = bitcast i8* %_22 to i32 (i8*)*
	%_24 = call i32 %_23(i8* %this)
	store i32 %_24, i32* %aux01	
	ret i32 0	
}

define i32 @BBS.Sort(i8* %this) {
	%nt = alloca i32	
	%i = alloca i32	
	%aux02 = alloca i32	
	%aux04 = alloca i32	
	%aux05 = alloca i32	
	%aux06 = alloca i32	
	%aux07 = alloca i32	
	%j = alloca i32	
	%t = alloca i32	
	%_0 = getelementptr i8, i8* %this, i32 16
	%_1 = bitcast i8* %_0 to i32*
	%_2 = load i32, i32* %_1
	%_3 = sub i32 %_2, 1
	store i32 %_3, i32* %i	
	%_4 = sub i32 0, 1
	store i32 %_4, i32* %aux02	
	br label %loop0
	loop0:
	%_5 = load i32, i32* %aux02
	%_6 = load i32, i32* %i
	%_7 = icmp slt i32 %_5, %_6
	br i1 %_7, label %loop1, label %loop2
	loop1:
	store i32 1, i32* %j	
	
	br label %loop3
	loop3:
	%_8 = load i32, i32* %j
	%_9 = load i32, i32* %i
	%_10 = add i32 %_9, 1
	%_11 = icmp slt i32 %_8, %_10
	br i1 %_11, label %loop4, label %loop5
	loop4:
	%_12 = load i32, i32* %j
	%_13 = sub i32 %_12, 1
	store i32 %_13, i32* %aux07	
	
	%_14 = getelementptr i8, i8* %this, i32 8
	%_15 = bitcast i8* %_14 to i32**
	%_16 = load i32*, i32** %_15
	%_17 = load i32, i32* %aux07
	%_18 = icmp slt i32 %_17, 0
	br i1 %_18, label %arr_alloc6, label %arr_alloc7
arr_alloc6:
	call void @throw_oob()
	br label %arr_alloc7
arr_alloc7:
	%_19 = getelementptr i32, i32* %_16, i32 0
	%_20 = load i32, i32* %_19
	%_21 = icmp sle i32 %_20, %_17
	br i1 %_21, label %arr_alloc8, label %arr_alloc9
arr_alloc8:
	call void @throw_oob()
	br label %arr_alloc9
arr_alloc9:
	%_22 = add i32 %_17, 1
	%_23 = getelementptr i32, i32* %_16, i32 %_22
	%_24 = load i32, i32* %_23

	store i32 %_24, i32* %aux04	
	
	%_25 = getelementptr i8, i8* %this, i32 8
	%_26 = bitcast i8* %_25 to i32**
	%_27 = load i32*, i32** %_26
	%_28 = load i32, i32* %j
	%_29 = icmp slt i32 %_28, 0
	br i1 %_29, label %arr_alloc10, label %arr_alloc11
arr_alloc10:
	call void @throw_oob()
	br label %arr_alloc11
arr_alloc11:
	%_30 = getelementptr i32, i32* %_27, i32 0
	%_31 = load i32, i32* %_30
	%_32 = icmp sle i32 %_31, %_28
	br i1 %_32, label %arr_alloc12, label %arr_alloc13
arr_alloc12:
	call void @throw_oob()
	br label %arr_alloc13
arr_alloc13:
	%_33 = add i32 %_28, 1
	%_34 = getelementptr i32, i32* %_27, i32 %_33
	%_35 = load i32, i32* %_34

	store i32 %_35, i32* %aux05	
	
	%_36 = load i32, i32* %aux05
	%_37 = load i32, i32* %aux04
	%_38 = icmp slt i32 %_36, %_37
	br i1 %_38, label %if14, label %if15
if14:
	%_39 = load i32, i32* %j
	%_40 = sub i32 %_39, 1
	store i32 %_40, i32* %aux06	
	
	%_41 = getelementptr i8, i8* %this, i32 8
	%_42 = bitcast i8* %_41 to i32**
	%_43 = load i32*, i32** %_42
	%_44 = load i32, i32* %aux06
	%_45 = icmp slt i32 %_44, 0
	br i1 %_45, label %arr_alloc17, label %arr_alloc18
arr_alloc17:
	call void @throw_oob()
	br label %arr_alloc18
arr_alloc18:
	%_46 = getelementptr i32, i32* %_43, i32 0
	%_47 = load i32, i32* %_46
	%_48 = icmp sle i32 %_47, %_44
	br i1 %_48, label %arr_alloc19, label %arr_alloc20
arr_alloc19:
	call void @throw_oob()
	br label %arr_alloc20
arr_alloc20:
	%_49 = add i32 %_44, 1
	%_50 = getelementptr i32, i32* %_43, i32 %_49
	%_51 = load i32, i32* %_50

	store i32 %_51, i32* %t	
	
	%_52 = getelementptr i8, i8* %this, i32 8
	%_53 = bitcast i8* %_52 to i32**
	%_54 = load i32*, i32** %_53
	%_55 = load i32, i32* %aux06
	%_56 = getelementptr i8, i8* %this, i32 8
	%_57 = bitcast i8* %_56 to i32**
	%_58 = load i32*, i32** %_57
	%_59 = load i32, i32* %j
	%_60 = icmp slt i32 %_59, 0
	br i1 %_60, label %arr_alloc21, label %arr_alloc22
arr_alloc21:
	call void @throw_oob()
	br label %arr_alloc22
arr_alloc22:
	%_61 = getelementptr i32, i32* %_58, i32 0
	%_62 = load i32, i32* %_61
	%_63 = icmp sle i32 %_62, %_59
	br i1 %_63, label %arr_alloc23, label %arr_alloc24
arr_alloc23:
	call void @throw_oob()
	br label %arr_alloc24
arr_alloc24:
	%_64 = add i32 %_59, 1
	%_65 = getelementptr i32, i32* %_58, i32 %_64
	%_66 = load i32, i32* %_65

	%_67 = icmp slt i32 %_55, 0
	br i1 %_67, label %arr_alloc25, label %arr_alloc26
arr_alloc25:
	call void @throw_oob()
	br label %arr_alloc26
arr_alloc26:
	%_68 = getelementptr i32, i32* %_54, i32 0
	%_69 = load i32, i32* %_68
	%_70 = icmp sle i32 %_69, %_55
	br i1 %_70, label %arr_alloc27, label %arr_alloc28
arr_alloc27:
	call void @throw_oob()
	br label %arr_alloc28
arr_alloc28:
	%_71 = add i32 %_55, 1
	%_72 = getelementptr i32, i32* %_54, i32 %_71
	store i32 %_66, i32* %_72	
	
	%_73 = getelementptr i8, i8* %this, i32 8
	%_74 = bitcast i8* %_73 to i32**
	%_75 = load i32*, i32** %_74
	%_76 = load i32, i32* %j
	%_77 = load i32, i32* %t
	%_78 = icmp slt i32 %_76, 0
	br i1 %_78, label %arr_alloc29, label %arr_alloc30
arr_alloc29:
	call void @throw_oob()
	br label %arr_alloc30
arr_alloc30:
	%_79 = getelementptr i32, i32* %_75, i32 0
	%_80 = load i32, i32* %_79
	%_81 = icmp sle i32 %_80, %_76
	br i1 %_81, label %arr_alloc31, label %arr_alloc32
arr_alloc31:
	call void @throw_oob()
	br label %arr_alloc32
arr_alloc32:
	%_82 = add i32 %_76, 1
	%_83 = getelementptr i32, i32* %_75, i32 %_82
	store i32 %_77, i32* %_83	
	
	br label %if16
if15:
	store i32 0, i32* %nt	
	br label %if16
if16:
	
	%_84 = load i32, i32* %j
	%_85 = add i32 %_84, 1
	store i32 %_85, i32* %j	
	
	br label %loop3
	loop5:
	
	%_86 = load i32, i32* %i
	%_87 = sub i32 %_86, 1
	store i32 %_87, i32* %i	
	
	br label %loop0
	loop2:
	ret i32 0	
}

define i32 @BBS.Print(i8* %this) {
	%j = alloca i32	
	store i32 0, i32* %j	
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
	ret i32 0	
}

define i32 @BBS.Init(i8* %this, i32 %.sz) {
	%sz = alloca i32	
	store i32 %.sz, i32* %sz	
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
	%_10 = getelementptr i8, i8* %this, i32 8
	%_11 = bitcast i8* %_10 to i32**
	%_12 = load i32*, i32** %_11
	%_13 = icmp slt i32 0, 0
	br i1 %_13, label %arr_alloc2, label %arr_alloc3
arr_alloc2:
	call void @throw_oob()
	br label %arr_alloc3
arr_alloc3:
	%_14 = getelementptr i32, i32* %_12, i32 0
	%_15 = load i32, i32* %_14
	%_16 = icmp sle i32 %_15, 0
	br i1 %_16, label %arr_alloc4, label %arr_alloc5
arr_alloc4:
	call void @throw_oob()
	br label %arr_alloc5
arr_alloc5:
	%_17 = add i32 0, 1
	%_18 = getelementptr i32, i32* %_12, i32 %_17
	store i32 20, i32* %_18	
	%_19 = getelementptr i8, i8* %this, i32 8
	%_20 = bitcast i8* %_19 to i32**
	%_21 = load i32*, i32** %_20
	%_22 = icmp slt i32 1, 0
	br i1 %_22, label %arr_alloc6, label %arr_alloc7
arr_alloc6:
	call void @throw_oob()
	br label %arr_alloc7
arr_alloc7:
	%_23 = getelementptr i32, i32* %_21, i32 0
	%_24 = load i32, i32* %_23
	%_25 = icmp sle i32 %_24, 1
	br i1 %_25, label %arr_alloc8, label %arr_alloc9
arr_alloc8:
	call void @throw_oob()
	br label %arr_alloc9
arr_alloc9:
	%_26 = add i32 1, 1
	%_27 = getelementptr i32, i32* %_21, i32 %_26
	store i32 7, i32* %_27	
	%_28 = getelementptr i8, i8* %this, i32 8
	%_29 = bitcast i8* %_28 to i32**
	%_30 = load i32*, i32** %_29
	%_31 = icmp slt i32 2, 0
	br i1 %_31, label %arr_alloc10, label %arr_alloc11
arr_alloc10:
	call void @throw_oob()
	br label %arr_alloc11
arr_alloc11:
	%_32 = getelementptr i32, i32* %_30, i32 0
	%_33 = load i32, i32* %_32
	%_34 = icmp sle i32 %_33, 2
	br i1 %_34, label %arr_alloc12, label %arr_alloc13
arr_alloc12:
	call void @throw_oob()
	br label %arr_alloc13
arr_alloc13:
	%_35 = add i32 2, 1
	%_36 = getelementptr i32, i32* %_30, i32 %_35
	store i32 12, i32* %_36	
	%_37 = getelementptr i8, i8* %this, i32 8
	%_38 = bitcast i8* %_37 to i32**
	%_39 = load i32*, i32** %_38
	%_40 = icmp slt i32 3, 0
	br i1 %_40, label %arr_alloc14, label %arr_alloc15
arr_alloc14:
	call void @throw_oob()
	br label %arr_alloc15
arr_alloc15:
	%_41 = getelementptr i32, i32* %_39, i32 0
	%_42 = load i32, i32* %_41
	%_43 = icmp sle i32 %_42, 3
	br i1 %_43, label %arr_alloc16, label %arr_alloc17
arr_alloc16:
	call void @throw_oob()
	br label %arr_alloc17
arr_alloc17:
	%_44 = add i32 3, 1
	%_45 = getelementptr i32, i32* %_39, i32 %_44
	store i32 18, i32* %_45	
	%_46 = getelementptr i8, i8* %this, i32 8
	%_47 = bitcast i8* %_46 to i32**
	%_48 = load i32*, i32** %_47
	%_49 = icmp slt i32 4, 0
	br i1 %_49, label %arr_alloc18, label %arr_alloc19
arr_alloc18:
	call void @throw_oob()
	br label %arr_alloc19
arr_alloc19:
	%_50 = getelementptr i32, i32* %_48, i32 0
	%_51 = load i32, i32* %_50
	%_52 = icmp sle i32 %_51, 4
	br i1 %_52, label %arr_alloc20, label %arr_alloc21
arr_alloc20:
	call void @throw_oob()
	br label %arr_alloc21
arr_alloc21:
	%_53 = add i32 4, 1
	%_54 = getelementptr i32, i32* %_48, i32 %_53
	store i32 2, i32* %_54	
	%_55 = getelementptr i8, i8* %this, i32 8
	%_56 = bitcast i8* %_55 to i32**
	%_57 = load i32*, i32** %_56
	%_58 = icmp slt i32 5, 0
	br i1 %_58, label %arr_alloc22, label %arr_alloc23
arr_alloc22:
	call void @throw_oob()
	br label %arr_alloc23
arr_alloc23:
	%_59 = getelementptr i32, i32* %_57, i32 0
	%_60 = load i32, i32* %_59
	%_61 = icmp sle i32 %_60, 5
	br i1 %_61, label %arr_alloc24, label %arr_alloc25
arr_alloc24:
	call void @throw_oob()
	br label %arr_alloc25
arr_alloc25:
	%_62 = add i32 5, 1
	%_63 = getelementptr i32, i32* %_57, i32 %_62
	store i32 11, i32* %_63	
	%_64 = getelementptr i8, i8* %this, i32 8
	%_65 = bitcast i8* %_64 to i32**
	%_66 = load i32*, i32** %_65
	%_67 = icmp slt i32 6, 0
	br i1 %_67, label %arr_alloc26, label %arr_alloc27
arr_alloc26:
	call void @throw_oob()
	br label %arr_alloc27
arr_alloc27:
	%_68 = getelementptr i32, i32* %_66, i32 0
	%_69 = load i32, i32* %_68
	%_70 = icmp sle i32 %_69, 6
	br i1 %_70, label %arr_alloc28, label %arr_alloc29
arr_alloc28:
	call void @throw_oob()
	br label %arr_alloc29
arr_alloc29:
	%_71 = add i32 6, 1
	%_72 = getelementptr i32, i32* %_66, i32 %_71
	store i32 6, i32* %_72	
	%_73 = getelementptr i8, i8* %this, i32 8
	%_74 = bitcast i8* %_73 to i32**
	%_75 = load i32*, i32** %_74
	%_76 = icmp slt i32 7, 0
	br i1 %_76, label %arr_alloc30, label %arr_alloc31
arr_alloc30:
	call void @throw_oob()
	br label %arr_alloc31
arr_alloc31:
	%_77 = getelementptr i32, i32* %_75, i32 0
	%_78 = load i32, i32* %_77
	%_79 = icmp sle i32 %_78, 7
	br i1 %_79, label %arr_alloc32, label %arr_alloc33
arr_alloc32:
	call void @throw_oob()
	br label %arr_alloc33
arr_alloc33:
	%_80 = add i32 7, 1
	%_81 = getelementptr i32, i32* %_75, i32 %_80
	store i32 9, i32* %_81	
	%_82 = getelementptr i8, i8* %this, i32 8
	%_83 = bitcast i8* %_82 to i32**
	%_84 = load i32*, i32** %_83
	%_85 = icmp slt i32 8, 0
	br i1 %_85, label %arr_alloc34, label %arr_alloc35
arr_alloc34:
	call void @throw_oob()
	br label %arr_alloc35
arr_alloc35:
	%_86 = getelementptr i32, i32* %_84, i32 0
	%_87 = load i32, i32* %_86
	%_88 = icmp sle i32 %_87, 8
	br i1 %_88, label %arr_alloc36, label %arr_alloc37
arr_alloc36:
	call void @throw_oob()
	br label %arr_alloc37
arr_alloc37:
	%_89 = add i32 8, 1
	%_90 = getelementptr i32, i32* %_84, i32 %_89
	store i32 19, i32* %_90	
	%_91 = getelementptr i8, i8* %this, i32 8
	%_92 = bitcast i8* %_91 to i32**
	%_93 = load i32*, i32** %_92
	%_94 = icmp slt i32 9, 0
	br i1 %_94, label %arr_alloc38, label %arr_alloc39
arr_alloc38:
	call void @throw_oob()
	br label %arr_alloc39
arr_alloc39:
	%_95 = getelementptr i32, i32* %_93, i32 0
	%_96 = load i32, i32* %_95
	%_97 = icmp sle i32 %_96, 9
	br i1 %_97, label %arr_alloc40, label %arr_alloc41
arr_alloc40:
	call void @throw_oob()
	br label %arr_alloc41
arr_alloc41:
	%_98 = add i32 9, 1
	%_99 = getelementptr i32, i32* %_93, i32 %_98
	store i32 5, i32* %_99	
	ret i32 0	
}


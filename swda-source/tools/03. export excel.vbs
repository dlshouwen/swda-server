Option Explicit

Dim mdl
Dim opt

Set mdl = ActiveModel
Set opt = mdl.GetModelOptions()
opt.EnableNameCodeTranslation = true
opt.save
opt.UpdateModelOptions
ProcessFolder 

Private sub ProcessFolder
	
	'sort
	Dim tbls(), tbl, col, inx, length, i, j
	length = mdl.tables.count
	redim tbls(length)
	
	i=0
	for each tbl in mdl.Tables
		set tbls(i)=tbl
		i=i+1
	next

	for i = 0 To length - 1
		For j = i + 1 To length-1
			If tbls(i).number > tbls(j).number Then 
				dim t
				set t = tbls(i)
				set tbls(i) = tbls(j)
				set tbls(j) = t
			end if
		Next
	Next
   
	'length = 10
   
	'export
	Dim x, lineNo, cell

	Set x = CreateObject("Excel.Application") 
	x.Visible = True 
	x.Workbooks.Add

	x.Sheets.add(x.Sheets(x.Sheets.count))
	x.ActiveSheet.Name="0. 表"
	lineNo = 1
	x.Range("A"+Cstr(lineNo)).Value = "序号"
	x.Range("B"+Cstr(lineNo)).Value = "表名"
	x.Range("C"+Cstr(lineNo)).Value = "释义"
	lineNo = lineNo+1
	for i = 0 To length - 1
		set tbl = tbls(i)
		x.Range("A"+Cstr(lineNo)).Value = tbl.Number
		x.Range("B"+Cstr(lineNo)).Value = tbl.Code
		x.Range("C"+Cstr(lineNo)).Value = tbl.Comment
		lineNo = lineNo+1
	next

	x.Sheets.add(x.Sheets(x.Sheets.count))
	x.ActiveSheet.Name="0. 字段"
	lineNo = 1
	x.Range("A"+Cstr(lineNo)).Value = "表名"
	x.Range("B"+Cstr(lineNo)).Value = "释义"
	x.Range("C"+Cstr(lineNo)).Value = "字段名"
	x.Range("D"+Cstr(lineNo)).Value = "释义"
	x.Range("E"+Cstr(lineNo)).Value = "字段类型"
	x.Range("F"+Cstr(lineNo)).Value = "长度"
	x.Range("G"+Cstr(lineNo)).Value = "精度"
	x.Range("H"+Cstr(lineNo)).Value = "选项"
	x.Range("I"+Cstr(lineNo)).Value = "默认值"
	x.Range("J"+Cstr(lineNo)).Value = "主键"
	x.Range("K"+Cstr(lineNo)).Value = "自增"
	x.Range("L"+Cstr(lineNo)).Value = "索引"
	x.Range("M"+Cstr(lineNo)).Value = "必填"
	for i = 0 To length - 1
		set tbl = tbls(i)
		for each col in tbl.Columns
			lineNo = lineNo+1
			x.Range("A"+Cstr(lineNo)).Value = tbl.Code
			x.Range("B"+Cstr(lineNo)).Value = tbl.Comment
			x.Range("C"+Cstr(lineNo)).Value = col.Code
			x.Range("D"+Cstr(lineNo)).Value = col.Comment
			x.Range("E"+Cstr(lineNo)).Value = col.DataType
			x.Range("F"+Cstr(lineNo)).Value = col.Length
			x.Range("G"+Cstr(lineNo)).Value = col.Precision
			x.Range("H"+Cstr(lineNo)).Value = col.PhysicalOptions
			x.Range("I"+Cstr(lineNo)).Value = col.DefaultValue
			if col.primary=true then x.Range("J"+Cstr(lineNo)).Value = "1" end if
			if col.primary=false then x.Range("J"+Cstr(lineNo)).Value = "0" end if
			if col.Identity=true then x.Range("K"+Cstr(lineNo)).Value = "1" end if
			if col.Identity=false then x.Range("K"+Cstr(lineNo)).Value = "0" end if
			x.Range("L"+Cstr(lineNo)).Value = "0"
			for each inx in tbl.indexes
				if inx.Code = ("i_"+col.Code) then
					x.Range("L"+Cstr(lineNo)).Value = "1"
				end if 
			next
			if col.Mandatory=true then x.Range("M"+Cstr(lineNo)).Value = "1" end if
			if col.Mandatory=false then x.Range("M"+Cstr(lineNo)).Value = "0" end if
		next
	next
   
	x.Sheets.add(x.Sheets(x.Sheets.count))
	x.ActiveSheet.Name="0. 表格"
	lineNo = 1
	for i = 0 To length - 1
		set tbl = tbls(i)
		x.Range("A"+Cstr(lineNo)).Value = "表名"
		x.Range("B"+Cstr(lineNo)).Value = tbl.Code
		x.Range("C"+Cstr(lineNo)).Value = "释义"
		x.Range("D"+Cstr(lineNo)+":"+"J"+Cstr(lineNo)).Merge
		x.Range("D"+Cstr(lineNo)).Value = tbl.Comment
		lineNo = lineNo+1
		x.Range("A"+Cstr(lineNo)).Value = "字段名"
		x.Range("B"+Cstr(lineNo)).Value = "释义"
		x.Range("C"+Cstr(lineNo)).Value = "字段类型"
		x.Range("D"+Cstr(lineNo)).Value = "长度"
		x.Range("E"+Cstr(lineNo)).Value = "精度"
		x.Range("F"+Cstr(lineNo)).Value = "选项"
		x.Range("G"+Cstr(lineNo)).Value = "默认值"
		x.Range("H"+Cstr(lineNo)).Value = "主键"
		x.Range("I"+Cstr(lineNo)).Value = "自增"
		x.Range("J"+Cstr(lineNo)).Value = "索引"
		x.Range("K"+Cstr(lineNo)).Value = "必填"
		lineNo = lineNo+1
		for each col in tbl.Columns
			x.Range("A"+Cstr(lineNo)).Value = col.Code
			x.Range("B"+Cstr(lineNo)).Value = col.Comment
			x.Range("C"+Cstr(lineNo)).Value = col.DataType
			x.Range("D"+Cstr(lineNo)).Value = col.Length
			x.Range("E"+Cstr(lineNo)).Value = col.Precision
			x.Range("F"+Cstr(lineNo)).Value = col.PhysicalOptions
			x.Range("G"+Cstr(lineNo)).Value = col.DefaultValue
			if col.primary=true then x.Range("H"+Cstr(lineNo)).Value = "1" end if
			if col.primary=false then x.Range("H"+Cstr(lineNo)).Value = "0" end if
			if col.Identity=true then x.Range("I"+Cstr(lineNo)).Value = "1" end if
			if col.Identity=false then x.Range("I"+Cstr(lineNo)).Value = "0" end if
			x.Range("J"+Cstr(lineNo)).Value = "0"
			for each inx in tbl.indexes
				if inx.Code = ("i_"+col.Code) then
					x.Range("J"+Cstr(lineNo)).Value = "1"
				end if 
			next
			if col.Mandatory=true then x.Range("K"+Cstr(lineNo)).Value = "1" end if
			if col.Mandatory=false then x.Range("K"+Cstr(lineNo)).Value = "0" end if
			lineNo = lineNo+1
		next
		'x.Range("A"+Cstr(lineNo)+":"+"K"+Cstr(lineNo)).Merge
		lineNo = lineNo+1
	next
   
	for i = 0 To length - 1
		set tbl = tbls(i)
		x.Sheets.add(x.Sheets(x.Sheets.count))
		x.ActiveSheet.Name=Cstr(i+1)+". "+tbl.Comment
		lineNo = 1
		set tbl = tbls(i)
		x.Range("A"+Cstr(lineNo)).Value = "表名"
		x.Range("B"+Cstr(lineNo)).Value = tbl.Code
		x.Range("C"+Cstr(lineNo)).Value = "释义"
		x.Range("D"+Cstr(lineNo)+":"+"K"+Cstr(lineNo)).Merge
		x.Range("D"+Cstr(lineNo)).Value = tbl.Comment
		lineNo = lineNo+1
		x.Range("A"+Cstr(lineNo)).Value = "字段名"
		x.Range("B"+Cstr(lineNo)).Value = "释义"
		x.Range("C"+Cstr(lineNo)).Value = "字段类型"
		x.Range("D"+Cstr(lineNo)).Value = "长度"
		x.Range("E"+Cstr(lineNo)).Value = "精度"
		x.Range("F"+Cstr(lineNo)).Value = "选项"
		x.Range("G"+Cstr(lineNo)).Value = "默认值"
		x.Range("H"+Cstr(lineNo)).Value = "主键"
		x.Range("I"+Cstr(lineNo)).Value = "自增"
		x.Range("J"+Cstr(lineNo)).Value = "索引"
		x.Range("K"+Cstr(lineNo)).Value = "必填"
		lineNo = lineNo+1
		for each col in tbl.Columns
			x.Range("A"+Cstr(lineNo)).Value = col.Code
			x.Range("B"+Cstr(lineNo)).Value = col.Comment
			x.Range("C"+Cstr(lineNo)).Value = col.DataType
			x.Range("D"+Cstr(lineNo)).Value = col.Length
			x.Range("E"+Cstr(lineNo)).Value = col.Precision
			x.Range("F"+Cstr(lineNo)).Value = col.PhysicalOptions
			x.Range("G"+Cstr(lineNo)).Value = col.DefaultValue
			if col.primary=true then x.Range("H"+Cstr(lineNo)).Value = "1" end if
			if col.primary=false then x.Range("H"+Cstr(lineNo)).Value = "0" end if
			if col.Identity=true then x.Range("I"+Cstr(lineNo)).Value = "1" end if
			if col.Identity=false then x.Range("I"+Cstr(lineNo)).Value = "0" end if
			x.Range("J"+Cstr(lineNo)).Value = "0"
			for each inx in tbl.indexes
				if inx.Code = ("i_"+col.Code) then
					x.Range("J"+Cstr(lineNo)).Value = "1"
				end if 
			next
			if col.Mandatory=true then x.Range("K"+Cstr(lineNo)).Value = "1" end if
			if col.Mandatory=false then x.Range("K"+Cstr(lineNo)).Value = "0" end if
			lineNo = lineNo+1
		next
		lineNo = lineNo+1
	next
   
	x.Sheets(x.Sheets.count).delete

end sub

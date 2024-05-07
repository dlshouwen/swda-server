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
   
	x.Sheets(x.Sheets.count).delete

end sub

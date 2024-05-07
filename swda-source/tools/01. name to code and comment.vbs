Option Explicit

Dim mdl ' 模型
Dim opt ' 模型选项

Set mdl = ActiveModel
Set opt = mdl.GetModelOptions()
opt.EnableNameCodeTranslation = true
opt.save
opt.UpdateModelOptions
ProcessFolder 

Private sub ProcessFolder
	Dim tbl, col, regEx
	for each tbl in mdl.Tables
		tbl.Name = replace(tbl.Name, "（", " [")
		tbl.Name = replace(tbl.Name, "）", "]")
		for each col in tbl.Columns
			col.Name = replace(col.Name, "（", " [")
			col.Name = replace(col.Name, "）", "]")
		next
	next
	Set regEx = New RegExp
	regEx.Pattern = ".*\s\["
	regEx.Global = true
	for each tbl in mdl.Tables
      tbl.Code = replace(regEx.replace(tbl.Name, ""), "]", "")
		for each col in tbl.Columns
         col.Code = replace(regEx.replace(col.Name, ""), "]", "")
		next
	next
	Set regEx = New RegExp
	regEx.Pattern = " \[.*\]"
	regEx.Global = true
	for each tbl in mdl.Tables
      tbl.Comment = regEx.replace(tbl.Name, "")
		for each col in tbl.Columns
         col.Comment =regEx.replace(col.Name, "")
		next
	next
end sub
